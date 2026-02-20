package com.ismael.kiduaventumundo.kiduaventumundo.ui.components

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import kotlin.math.roundToInt

import com.ismael.kiduaventumundo.kiduaventumundo.R


@Composable
fun CloudLayer() {

    val cloudCount = 6

    repeat(cloudCount) { index ->

        val randomStart = (-800..0).random().toFloat()
        val randomY = (50..300).random().toFloat()
        val randomSize = (100..200).random().toFloat()
        val randomDuration = (20000..40000).random()

        AnimatedCloud(
            drawableRes = R.drawable.cloud,
            startX = randomStart,
            yOffset = randomY,
            size = randomSize,
            duration = randomDuration
        )
    }
}
