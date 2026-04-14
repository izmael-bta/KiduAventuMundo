package com.ismael.kiduaventumundo.kiduaventumundo.front.english

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import com.ismael.kiduaventumundo.kiduaventumundo.R
import com.ismael.kiduaventumundo.kiduaventumundo.back.data.english.EnglishLevel8Data
import com.ismael.kiduaventumundo.kiduaventumundo.back.logic.english.EnglishQuizLevelScreen
import com.ismael.kiduaventumundo.kiduaventumundo.ui.components.AnimatedCircle

@Composable
fun EnglishLevel8Screen(
    onBack: () -> Unit,
    onFinished: (Int?) -> Unit
) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Image(
            painter = painterResource(R.drawable.fondo_desierto),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
        )
    }
    Box(modifier = Modifier.fillMaxSize()){
        AnimatedCircle()
    }

    EnglishQuizLevelScreen(
        levelNumber = 8,
        levelTitle = "Desafio final",
        unlockMessage = "Completaste todos los niveles",
        questionsSource = EnglishLevel8Data.questions(),
        onBack = onBack,
        onFinished = onFinished
    )
}
