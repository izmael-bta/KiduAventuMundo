package com.ismael.kiduaventumundo.kiduaventumundo.back.logic

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import com.ismael.kiduaventumundo.kiduaventumundo.com.ismael.kiduaventumundo.kiduaventumundo.ui.screens.EnglishLevel

object EnglishManager {

    val stars = mutableStateOf(0)
    private val bestStarsByLevel = mutableMapOf<Int, Int>()
    private val activityBestStarsByLevel = mutableMapOf<Int, MutableList<Int?>>()
    private val nextStartActivityByLevel = mutableMapOf<Int, Int>()

    private val levels = mutableStateListOf(
        EnglishLevel(1,"Colores","Identifica el color correcto",true,false),
        EnglishLevel(2,"Objetos","Selecciona el objeto correcto",false,false),
        EnglishLevel(3,"Animales","Elige el animal correcto",false,false),
        EnglishLevel(4,"Sonidos","Escucha y selecciona",false,false),
        EnglishLevel(5,"Colores + Objetos","Combina lo aprendido",false,false),
        EnglishLevel(6,"Animales + Sonidos","Relaciona el sonido",false,false),
        EnglishLevel(7,"Mixto","Repaso general",false,false),
        EnglishLevel(8,"Desafío final","Racha de aciertos",false,false)
    )

    fun getLevels(): List<EnglishLevel> = levels

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
            val delta = earned - maxOf(previous, 0)
            stars.value += delta
            activityStars[activityIndex] = earned
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

        // marcar completado
        val current = levels[index]
        levels[index] = current.copy(isCompleted = true)

        // desbloquear siguiente
        if (index + 1 < levels.size) {
            val next = levels[index + 1]
            levels[index + 1] = next.copy(isUnlocked = true)
        }
    }
}
