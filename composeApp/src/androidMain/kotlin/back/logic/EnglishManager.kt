package com.ismael.kiduaventumundo.kiduaventumundo.back.logic

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import com.ismael.kiduaventumundo.kiduaventumundo.back.data.english.EnglishLevel1Data
import com.ismael.kiduaventumundo.kiduaventumundo.back.data.english.EnglishLevel2Data
import com.ismael.kiduaventumundo.kiduaventumundo.back.data.english.EnglishLevel3Data
import com.ismael.kiduaventumundo.kiduaventumundo.back.data.english.EnglishLevel4Data
import com.ismael.kiduaventumundo.kiduaventumundo.back.data.english.EnglishLevel5Data
import com.ismael.kiduaventumundo.kiduaventumundo.back.data.english.EnglishLevel6Data
import com.ismael.kiduaventumundo.kiduaventumundo.back.data.english.EnglishLevel7Data
import com.ismael.kiduaventumundo.kiduaventumundo.back.data.english.EnglishLevel8Data
import com.ismael.kiduaventumundo.kiduaventumundo.data.remote.model.ProgressEvent
import com.ismael.kiduaventumundo.kiduaventumundo.data.remote.model.UserActivityProgress
import com.ismael.kiduaventumundo.kiduaventumundo.data.remote.model.UserLevelProgress
import com.ismael.kiduaventumundo.kiduaventumundo.domain.repository.EventsRepository
import com.ismael.kiduaventumundo.kiduaventumundo.domain.repository.ProgressRepository
import com.ismael.kiduaventumundo.kiduaventumundo.domain.repository.ReportsRepository
import com.ismael.kiduaventumundo.kiduaventumundo.com.ismael.kiduaventumundo.kiduaventumundo.ui.screens.EnglishLevel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch

object EnglishManager {

    val stars = mutableStateOf(0)
    private val bestStarsByLevel = mutableMapOf<Int, Int>()
    private val activityBestStarsByLevel = mutableMapOf<Int, MutableList<Int?>>()
    private val nextStartActivityByLevel = mutableMapOf<Int, Int>()

    private val levels = mutableStateListOf<EnglishLevel>()

    private var progressRepository: ProgressRepository? = null
    private var eventsRepository: EventsRepository? = null
    private var reportsRepository: ReportsRepository? = null
    private var activeUserId: Long? = null

    private val ioScope = CoroutineScope(SupervisorJob() + Dispatchers.IO)

    init {
        resetInMemoryProgress()
    }

    fun configure(
        progressRepository: ProgressRepository,
        eventsRepository: EventsRepository,
        reportsRepository: ReportsRepository
    ) {
        this.progressRepository = progressRepository
        this.eventsRepository = eventsRepository
        this.reportsRepository = reportsRepository
    }

    private fun defaultLevels(): List<EnglishLevel> = listOf(
        EnglishLevel(1, "Colores", "Identifica el color correcto", true, false),
        EnglishLevel(2, "Objetos", "Selecciona el objeto correcto", false, false),
        EnglishLevel(3, "Animales", "Elige el animal correcto", false, false),
        EnglishLevel(4, "Sonidos", "Escucha y selecciona", false, false),
        EnglishLevel(5, "Colores + Objetos", "Combina lo aprendido", false, false),
        EnglishLevel(6, "Animales + Sonidos", "Relaciona el sonido", false, false),
        EnglishLevel(7, "Mixto", "Repaso general", false, false),
        EnglishLevel(8, "Desafio final", "Racha de aciertos", false, false)
    )

    fun getLevels(): List<EnglishLevel> = levels

    data class ProgressSummary(
        val totalStars: Int,
        val activitiesCompleted: Int,
        val currentLevel: Int,
        val unlockedLevels: Int
    )

    suspend fun bindUserSession(userId: Long) {
        if (activeUserId == userId) return
        activeUserId = userId
        loadProgressFromApi()
    }

    suspend fun persistCurrentUserProgress() {
        persistProgressToApi()
    }

    fun clearInMemoryProgress() {
        activeUserId = null
        resetInMemoryProgress()
    }

    fun getActivityStars(level: Int, totalActivities: Int): List<Int?> {
        val current = activityBestStarsByLevel.getOrPut(level) {
            MutableList(totalActivities) { null }
        }
        if (current.size < totalActivities) {
            repeat(totalActivities - current.size) { current.add(null) }
        }
        return current.toList()
    }

    fun recordActivityResult(level: Int, activityIndex: Int, starsEarned: Int, totalActivities: Int) {
        val userId = activeUserId ?: return
        val earned = starsEarned.coerceIn(0, 3)
        val activityStars = activityBestStarsByLevel.getOrPut(level) {
            MutableList(totalActivities) { null }
        }
        if (activityIndex !in activityStars.indices) return

        val previous = activityStars[activityIndex] ?: -1
        if (earned <= previous) return

        val delta = earned - maxOf(previous, 0)
        stars.value += delta
        activityStars[activityIndex] = earned

        ioScope.launch {
            progressRepository?.upsertActivityProgress(
                userId = userId,
                level = level,
                activityIndex = activityIndex,
                progress = UserActivityProgress(
                    userId = userId,
                    level = level,
                    activityIndex = activityIndex,
                    stars = earned,
                    attempts = 1,
                    successes = 1,
                    lastResult = true
                )
            )
            eventsRepository?.createEvent(
                userId = userId,
                event = ProgressEvent(
                    userId = userId,
                    level = level,
                    activityIndex = activityIndex,
                    eventType = "activity_completed",
                    starsDelta = delta,
                    payloadJson = "{\"stars\":$earned}"
                )
            )
        }
    }

    fun getLevelStars(level: Int, totalActivities: Int): Int {
        return getActivityStars(level, totalActivities).sumOf { it ?: 0 }
    }

    fun setStartActivity(level: Int, activityIndex: Int) {
        nextStartActivityByLevel[level] = activityIndex.coerceAtLeast(0)
    }

    fun consumeStartActivity(level: Int): Int {
        return nextStartActivityByLevel.remove(level) ?: 0
    }

    fun completeLevel(level: Int, starsEarned: Int) {
        val userId = activeUserId ?: return
        val index = levels.indexOfFirst { it.level == level }
        if (index == -1) return

        val earned = starsEarned.coerceAtLeast(0)
        val previousBest = bestStarsByLevel[level] ?: 0
        if (earned > previousBest) bestStarsByLevel[level] = earned

        val current = levels[index]
        levels[index] = current.copy(isCompleted = true)

        if (index + 1 < levels.size) {
            val next = levels[index + 1]
            levels[index + 1] = next.copy(isUnlocked = true)
        }

        ioScope.launch {
            progressRepository?.upsertLevelProgress(
                userId = userId,
                level = level,
                progress = UserLevelProgress(
                    userId = userId,
                    level = level,
                    isUnlocked = true,
                    isCompleted = true,
                    bestStars = earned
                )
            )
        }
    }

    fun completeLevelAndGetNext(level: Int, starsEarned: Int): Int? {
        completeLevel(level = level, starsEarned = starsEarned)
        return getNextPlayableLevel(level)
    }

    fun getNextPlayableLevel(currentLevel: Int): Int? {
        val nextLevelNumber = currentLevel + 1
        return levels.firstOrNull { it.level == nextLevelNumber && it.isUnlocked }?.level
    }

    fun getProgressSummary(): ProgressSummary {
        val currentLevels = getLevels()
        val currentLevel = currentLevels.firstOrNull { it.isUnlocked && !it.isCompleted }?.level
            ?: currentLevels.lastOrNull { it.isCompleted }?.level
            ?: 1
        val unlockedLevels = currentLevels.count { it.isUnlocked }
        val activitiesCompleted = (1..8).sumOf { level ->
            val totalActivities = totalActivitiesForLevel(level)
            getActivityStars(level, totalActivities).count { it != null }
        }

        return ProgressSummary(
            totalStars = stars.value,
            activitiesCompleted = activitiesCompleted,
            currentLevel = currentLevel,
            unlockedLevels = unlockedLevels
        )
    }

    private fun totalActivitiesForLevel(level: Int): Int {
        return when (level) {
            1 -> EnglishLevel1Data.questions().size
            2 -> EnglishLevel2Data.questions().size
            3 -> EnglishLevel3Data.questions().size
            4 -> EnglishLevel4Data.questions().size
            5 -> EnglishLevel5Data.questions().size
            6 -> EnglishLevel6Data.questions().size
            7 -> EnglishLevel7Data.questions().size
            8 -> EnglishLevel8Data.questions().size
            else -> 0
        }
    }

    private fun resetInMemoryProgress() {
        stars.value = 0
        bestStarsByLevel.clear()
        activityBestStarsByLevel.clear()
        nextStartActivityByLevel.clear()
        levels.clear()
        levels.addAll(defaultLevels())
    }

    private suspend fun loadProgressFromApi() {
        val userId = activeUserId ?: return
        val progressRepo = progressRepository ?: return

        resetInMemoryProgress()

        val summary = reportsRepository?.getSummary(userId)
        stars.value = summary?.totalStars ?: 0

        val levelProgressByLevel = progressRepo.getLevelProgress(userId).associateBy { it.level }
        levels.forEachIndexed { index, level ->
            val saved = levelProgressByLevel[level.level] ?: return@forEachIndexed
            levels[index] = level.copy(
                isUnlocked = saved.isUnlocked,
                isCompleted = saved.isCompleted
            )
            bestStarsByLevel[level.level] = saved.bestStars
        }

        levels.forEach { level ->
            val totalActivities = totalActivitiesForLevel(level.level)
            val savedActivityStars = MutableList(totalActivities) { null as Int? }
            progressRepo.getActivityProgress(userId, level.level).forEach { item ->
                val position = item.activityIndex.coerceAtLeast(0)
                if (position in savedActivityStars.indices) {
                    savedActivityStars[position] = item.stars
                }
            }
            activityBestStarsByLevel[level.level] = savedActivityStars
        }
    }

    private suspend fun persistProgressToApi() {
        val userId = activeUserId ?: return
        val progressRepo = progressRepository ?: return

        levels.forEach { level ->
            val levelStars = getLevelStars(level.level, totalActivitiesForLevel(level.level))
            progressRepo.upsertLevelProgress(
                userId = userId,
                level = level.level,
                progress = UserLevelProgress(
                    userId = userId,
                    level = level.level,
                    isUnlocked = level.isUnlocked,
                    isCompleted = level.isCompleted,
                    bestStars = levelStars
                )
            )

            getActivityStars(level.level, totalActivitiesForLevel(level.level))
                .forEachIndexed { index, star ->
                    if (star != null) {
                        progressRepo.upsertActivityProgress(
                            userId = userId,
                            level = level.level,
                            activityIndex = index,
                            progress = UserActivityProgress(
                                userId = userId,
                                level = level.level,
                                activityIndex = index,
                                stars = star,
                                attempts = 1,
                                successes = if (star > 0) 1 else 0,
                                lastResult = star > 0
                            )
                        )
                    }
                }
        }
    }
}
