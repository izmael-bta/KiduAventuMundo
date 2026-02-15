package com.ismael.kiduaventumundo.kiduaventumundo.back.logic

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import com.ismael.kiduaventumundo.kiduaventumundo.front.screens.EnglishLevel

object EnglishManager {

    // Total general en memoria (sin persistencia SQL por ahora)
    val stars = mutableStateOf(0)

    private val levels = mutableStateListOf(
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

    fun resetProgress() {
        stars.value = 0
        for (i in levels.indices) {
            val current = levels[i]
            levels[i] = current.copy(
                isUnlocked = i == 0,
                isCompleted = false
            )
        }
    }

    fun completeLevel(level: Int, starsEarned: Int) {
        val index = levels.indexOfFirst { it.level == level }
        if (index == -1) return

        val current = levels[index]
        if (current.isCompleted) return

        val safeStars = starsEarned.coerceAtLeast(0)

        stars.value += safeStars
        levels[index] = current.copy(isCompleted = true)

        val required = when (level) {
            1 -> 13
            2 -> 13
            else -> 0
        }

        if (safeStars >= required && index + 1 < levels.size) {
            val next = levels[index + 1]
            levels[index + 1] = next.copy(isUnlocked = true)
        }
    }
}
