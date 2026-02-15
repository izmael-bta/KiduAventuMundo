package com.ismael.kiduaventumundo.kiduaventumundo.com.ismael.kiduaventumundo.kiduaventumundo.domain.actions

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import com.ismael.kiduaventumundo.kiduaventumundo.com.ismael.kiduaventumundo.kiduaventumundo.ui.screens.EnglishLevel

object EnglishManager {

    val stars = mutableStateOf(0)

    private val levels = mutableStateListOf(
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

    fun completeLevel(level: Int, starsEarned: Int) {
        val index = levels.indexOfFirst { it.level == level }
        if (index == -1) return

        // sumar estrellitas
        stars.value += starsEarned

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