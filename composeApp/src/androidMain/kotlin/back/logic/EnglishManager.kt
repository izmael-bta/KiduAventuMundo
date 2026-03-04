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
import com.ismael.kiduaventumundo.kiduaventumundo.back.db.AppDatabaseHelper
import com.ismael.kiduaventumundo.kiduaventumundo.com.ismael.kiduaventumundo.kiduaventumundo.ui.screens.EnglishLevel

/**
 * Manager central del progreso de Ingles.
 *
 * Responsabilidades:
 * - Mantener estado en memoria para la UI.
 * - Cargar/guardar progreso por usuario en SQLite.
 * - Exponer un resumen simple para pantallas de progreso.
 */
object EnglishManager {

    val stars = mutableStateOf(0)
    private val bestStarsByLevel = mutableMapOf<Int, Int>()
    private val activityBestStarsByLevel = mutableMapOf<Int, MutableList<Int?>>()
    private val nextStartActivityByLevel = mutableMapOf<Int, Int>()
    private var db: AppDatabaseHelper? = null
    private var activeUserId: Long? = null

    private val levels = mutableStateListOf<EnglishLevel>()

    init {
        resetInMemoryProgress()
    }

    private fun defaultLevels(): List<EnglishLevel> = listOf(
        EnglishLevel(1, "Colores", "Identifica el color correcto", true, false),
        EnglishLevel(2, "Objetos", "Selecciona el objeto correcto", false, false),
        EnglishLevel(3, "Animales", "Elige el animal correcto", false, false),
        EnglishLevel(4, "Sonidos", "Escucha y selecciona", false, false),
        EnglishLevel(5, "Colores + Objetos", "Combina lo aprendido", false, false),
        EnglishLevel(6, "Animales + Sonidos", "Relaciona el sonido", false, false),
        EnglishLevel(7, "Mixto", "Repaso general", false, false),
        EnglishLevel(8, "Desafío final", "Racha de aciertos", false, false)
    )

    fun getLevels(): List<EnglishLevel> = levels

    data class ProgressSummary(
        val totalStars: Int,
        val activitiesCompleted: Int,
        val currentLevel: Int,
        val unlockedLevels: Int
    )

    /**
     * Vincula una sesion de usuario y carga su progreso persistido.
     * Si ya estaba vinculado el mismo usuario, evita recarga innecesaria.
     */
    fun bindUserSession(database: AppDatabaseHelper, userId: Long) {
        db = database
        if (activeUserId == userId) return
        activeUserId = userId
        loadProgressFromDatabase()
    }

    /**
     * Fuerza persistencia del estado actual.
     * Util cuando se hace logout o transiciones sensibles.
     */
    fun persistCurrentUserProgress() {
        persistProgressToDatabase()
    }

    /**
     * Limpia estado en memoria del usuario actual.
     * No borra datos persistidos.
     */
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
        val earned = starsEarned.coerceIn(0, 3)
        val activityStars = activityBestStarsByLevel.getOrPut(level) {
            MutableList(totalActivities) { null }
        }
        if (activityIndex !in activityStars.indices) return

        val previous = activityStars[activityIndex] ?: -1
        if (earned > previous) {
            // Solo se suma la mejora neta para no duplicar estrellas al repetir actividad.
            val delta = earned - maxOf(previous, 0)
            stars.value += delta
            activityStars[activityIndex] = earned
            persistProgressToDatabase()
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

        // Guardado inmediato para no perder desbloqueos/completados al cerrar app.
        persistProgressToDatabase()
    }

    /**
     * Completa el nivel y devuelve el siguiente nivel jugable, si existe.
     */
    fun completeLevelAndGetNext(level: Int, starsEarned: Int): Int? {
        completeLevel(level = level, starsEarned = starsEarned)
        return getNextPlayableLevel(level)
    }

    /**
     * Devuelve el siguiente nivel desbloqueado despues del nivel actual.
     * Retorna null si no hay siguiente nivel (fin de contenido).
     */
    fun getNextPlayableLevel(currentLevel: Int): Int? {
        val nextLevelNumber = currentLevel + 1
        return levels.firstOrNull { it.level == nextLevelNumber && it.isUnlocked }?.level
    }

    /**
     * Entrega datos listos para UI de Progreso, evitando logica de negocio en composables.
     */
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

    /**
     * Restaura estado de progreso del usuario actual desde SQLite.
     */
    private fun loadProgressFromDatabase() {
        val database = db ?: return
        val userId = activeUserId ?: return

        resetInMemoryProgress()

        stars.value = database.getUserById(userId)?.stars ?: 0

        val levelProgressByLevel = database.getEnglishLevelProgress(userId).associateBy { it.level }
        levels.forEachIndexed { index, level ->
            val saved = levelProgressByLevel[level.level] ?: return@forEachIndexed
            levels[index] = level.copy(
                isUnlocked = saved.isUnlocked,
                isCompleted = saved.isCompleted
            )
        }

        levels.forEach { level ->
            val totalActivities = totalActivitiesForLevel(level.level)
            val savedActivityStars = database.getEnglishActivityStars(userId, level.level, totalActivities)
            activityBestStarsByLevel[level.level] = savedActivityStars.toMutableList()
        }
    }

    /**
     * Persiste estado completo de progreso para mantener coherencia:
     * estrellas globales, niveles y estrellas por actividad.
     */
    private fun persistProgressToDatabase() {
        val database = db ?: return
        val userId = activeUserId ?: return

        database.updateUserStars(userId = userId, stars = stars.value)

        levels.forEach { level ->
            database.upsertEnglishLevelProgress(
                userId = userId,
                level = level.level,
                isUnlocked = level.isUnlocked,
                isCompleted = level.isCompleted
            )
            val totalActivities = totalActivitiesForLevel(level.level)
            database.replaceEnglishActivityStars(
                userId = userId,
                level = level.level,
                starsByActivity = getActivityStars(level.level, totalActivities)
            )
        }
    }
}
