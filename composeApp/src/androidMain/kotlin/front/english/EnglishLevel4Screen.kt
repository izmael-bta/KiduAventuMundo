package com.ismael.kiduaventumundo.kiduaventumundo.front.english

import androidx.compose.runtime.Composable

@Composable
fun EnglishLevel4Screen(
    onBack: () -> Unit,
    onFinished: () -> Unit
) {
    EnglishQuizLevelScreen(
        levelNumber = 4,
        levelTitle = "Sonidos",
        unlockMessage = "Se desbloqueo el Nivel 5.",
        questionsSource = EnglishLevel4Data.questions(),
        onBack = onBack,
        onFinished = onFinished
    )
}
