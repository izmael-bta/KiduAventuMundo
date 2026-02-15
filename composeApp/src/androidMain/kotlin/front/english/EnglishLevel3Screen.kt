package com.ismael.kiduaventumundo.kiduaventumundo.front.english

import androidx.compose.runtime.Composable

@Composable
fun EnglishLevel3Screen(
    onBack: () -> Unit,
    onFinished: () -> Unit
) {
    EnglishQuizLevelScreen(
        levelNumber = 3,
        levelTitle = "Animales",
        unlockMessage = "Se desbloqueo el Nivel 4.",
        questionsSource = EnglishLevel3Data.questions(),
        onBack = onBack,
        onFinished = onFinished
    )
}
