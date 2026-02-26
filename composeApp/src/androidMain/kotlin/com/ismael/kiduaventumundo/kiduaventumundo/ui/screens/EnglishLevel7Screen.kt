package com.ismael.kiduaventumundo.kiduaventumundo.front.english

import androidx.compose.runtime.Composable
import com.ismael.kiduaventumundo.kiduaventumundo.back.data.english.EnglishLevel7Data
import com.ismael.kiduaventumundo.kiduaventumundo.back.logic.english.EnglishQuizLevelScreen

@Composable
fun EnglishLevel7Screen(
    onBack: () -> Unit,
    onFinished: () -> Unit
) {
    EnglishQuizLevelScreen(
        levelNumber = 7,
        levelTitle = "Mixto",
        unlockMessage = "Se desbloqueo el Nivel 8.",
        questionsSource = EnglishLevel7Data.questions(),
        onBack = onBack,
        onFinished = onFinished
    )
}
