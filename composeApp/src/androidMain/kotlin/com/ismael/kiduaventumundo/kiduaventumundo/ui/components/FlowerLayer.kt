package com.ismael.kiduaventumundo.kiduaventumundo.ui.components

import androidx.compose.animation.core.*
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.ismael.kiduaventumundo.kiduaventumundo.R

@Composable
fun FlowerLayer() {

    Box(
        modifier = Modifier.fillMaxSize()
    ) {

        Row(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 50.dp), // ajusta si quieres más arriba
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {

            AnimatedFlower(size = 180f, duration = 3200)
            AnimatedFlower(size = 140f, duration = 2600)
            AnimatedFlower(size = 160f, duration = 3000)
            AnimatedFlower(size = 140f, duration = 2500)
            AnimatedFlower(size = 180f, duration = 3400)
        }
    }
}
