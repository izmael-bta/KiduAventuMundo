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
import com.ismael.kiduaventumundo.kiduaventumundo.back.data.english.EnglishLevel3Data
import com.ismael.kiduaventumundo.kiduaventumundo.back.logic.english.EnglishQuizLevelScreen
import com.ismael.kiduaventumundo.kiduaventumundo.ui.components.AnimatedCircle
import com.ismael.kiduaventumundo.kiduaventumundo.ui.components.AnimatedFlower

@Composable
fun EnglishLevel3Screen(
    onBack: () -> Unit,
    onFinished: (Int?) -> Unit
) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Image(
            painter = painterResource(R.drawable.fondo_verdeh),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
        )
    }
    Box(modifier = Modifier.fillMaxSize()){
        AnimatedCircle()
    }

    EnglishQuizLevelScreen(
        levelNumber = 3,
        levelTitle = "Animales",
        unlockMessage = "Nivel 4 Disponible👏",
        questionsSource = EnglishLevel3Data.questions(),
        onBack = onBack,
        onFinished = onFinished
    )
}
