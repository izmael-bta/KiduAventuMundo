package com.ismael.kiduaventumundo.kiduaventumundo.ui.components

import androidx.compose.animation.core.*
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import kotlinx.coroutines.delay
import kotlin.random.Random

@Composable
fun Stars() {

    val stars = remember {
        List(12) { Star() }
    }

    Canvas(modifier = Modifier.fillMaxSize()) {

        stars.forEach { star ->

            //  Brillo exterior
            drawCircle(
                color = Color.White.copy(alpha = star.alpha * 0.3f),
                radius = star.radius * 2f,
                center = Offset(star.x, star.y)
            )

            //  Núcleo brillante
            drawCircle(
                color = Color.White.copy(alpha = star.alpha),
                radius = star.radius,
                center = Offset(star.x, star.y)
            )
        }
    }

    stars.forEach { star ->
        LaunchedEffect(Unit) {
            while (true) {
                star.blink()
            }
        }
    }
}

class Star {

    var x by mutableStateOf(Random.nextFloat() * 1000f)
    var y by mutableStateOf(Random.nextFloat() * 2000f)

    var alpha by mutableStateOf(Random.nextFloat() * 0.5f + 0.5f)

    val radius = Random.nextInt(10, 18).toFloat()

    suspend fun blink() {
        alpha = Random.nextFloat() * 0.5f + 0.5f
        delay(Random.nextLong(500L, 1200L))
    }
}