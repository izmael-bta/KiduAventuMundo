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
fun AnimatedCloud(
    drawableRes: Int,
    startX: Float,
    yOffset: Float,
    size: Float,
    duration: Int
) {
    val infiniteTransition = rememberInfiniteTransition(label = "")

    val offsetX by infiniteTransition.animateFloat(
        initialValue = startX,
        targetValue = startX + 1400f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = duration, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = ""
    )

    Image(
        painter = painterResource(id = drawableRes),
        contentDescription = null,
        modifier = Modifier
            .offset {
                IntOffset(offsetX.toInt(), yOffset.toInt())
            }
            .size(size.dp),
        contentScale = ContentScale.Fit
    )
}
