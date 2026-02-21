package com.ismael.kiduaventumundo.kiduaventumundo.ui.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import kotlinx.coroutines.delay
import kotlin.math.sin
import kotlin.random.Random

@Composable
fun AnimatedCircle() {

    val configuration = LocalConfiguration.current
    val screenHeight = configuration.screenHeightDp.toFloat() * 3
    val screenWidth = configuration.screenWidthDp.toFloat() * 3

    val particles = remember {
        List(25) {
            Particle(screenWidth, screenHeight)
        }
    }

    Canvas(modifier = Modifier.fillMaxSize()) {
        particles.forEach {
            drawCircle(
                color = it.color,
                radius = it.radius,
                center = Offset(it.x, it.y)
            )
        }
    }

    particles.forEach { particle ->
        LaunchedEffect(Unit) {
            while (true) {
                particle.update(screenHeight, screenWidth)
                delay(16L)
            }
        }
    }
}

class Particle(
    private val screenWidth: Float,
    private val screenHeight: Float
) {
    var x by mutableStateOf(Random.nextFloat() * screenWidth)
    var y by mutableStateOf(Random.nextFloat() * screenHeight)

    private val direction = if (Random.nextBoolean()) 1 else -1
    private val speed = Random.nextFloat() * 2f + 0.5f
    private var waveOffset = Random.nextFloat() * 100f

    val radius = Random.nextInt(40, 120).toFloat()

    val color = listOf(
        Color(0xFFFF6B6B),
        Color(0xFFFFD93D),
        Color(0xFF6BCB77),
        Color(0xFF4D96FF),
        Color(0xFFB983FF)
    ).random().copy(alpha = Random.nextFloat() * 0.4f + 0.2f)

    suspend fun update(maxHeight: Float, maxWidth: Float) {
        y += speed * direction
        x += sin(y / 100 + waveOffset) * 0.8f

        if (y > maxHeight + radius) {
            y = -radius
            x = Random.nextFloat() * maxWidth
        }

        if (y < -radius) {
            y = maxHeight + radius
            x = Random.nextFloat() * maxWidth
        }
    }
}