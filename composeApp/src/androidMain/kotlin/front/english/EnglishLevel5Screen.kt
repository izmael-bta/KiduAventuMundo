package com.ismael.kiduaventumundo.kiduaventumundo.front.english

import androidx.compose.runtime.Composable

@Composable
fun EnglishLevel5Screen(
    onBack: () -> Unit,
    onFinished: () -> Unit
) {
    EnglishQuizLevelScreen(
        levelNumber = 5,
        levelTitle = "Colores + Objetos",
        unlockMessage = "Se desbloqueo el Nivel 6.",
        questionsSource = EnglishLevel5Data.questions(),
        onBack = onBack,
        onFinished = onFinished
    )
}
