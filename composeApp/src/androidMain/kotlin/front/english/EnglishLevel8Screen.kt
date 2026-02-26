package com.ismael.kiduaventumundo.kiduaventumundo.front.english

import androidx.compose.runtime.Composable
import com.ismael.kiduaventumundo.kiduaventumundo.back.data.english.EnglishLevel8Data
import com.ismael.kiduaventumundo.kiduaventumundo.back.logic.english.EnglishQuizLevelScreen

@Composable
fun EnglishLevel8Screen(
    onBack: () -> Unit,
    onFinished: () -> Unit
) {
    EnglishQuizLevelScreen(
        levelNumber = 8,
        levelTitle = "Desafio final",
        unlockMessage = "Has completado todos los niveles.",
        questionsSource = EnglishLevel8Data.questions(),
        onBack = onBack,
        onFinished = onFinished
    )
}
