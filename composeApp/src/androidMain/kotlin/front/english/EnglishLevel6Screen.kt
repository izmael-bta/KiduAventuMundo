package com.ismael.kiduaventumundo.kiduaventumundo.front.english

import androidx.compose.runtime.Composable
import com.ismael.kiduaventumundo.kiduaventumundo.back.data.english.EnglishLevel6Data
import com.ismael.kiduaventumundo.kiduaventumundo.back.logic.english.EnglishQuizLevelScreen

@Composable
fun EnglishLevel6Screen(
    onBack: () -> Unit,
    onFinished: () -> Unit
) {
    EnglishQuizLevelScreen(
        levelNumber = 6,
        levelTitle = "Animales + Sonidos",
        unlockMessage = "Se desbloqueo el Nivel 7.",
        questionsSource = EnglishLevel6Data.questions(),
        onBack = onBack,
        onFinished = onFinished
    )
}
