package com.ismael.kiduaventumundo.kiduaventumundo.ui.components

import androidx.compose.animation.core.*
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource

import androidx.compose.ui.unit.dp

import com.ismael.kiduaventumundo.kiduaventumundo.R

@Composable
fun AnimatedFlower(
    size: Float,
    duration: Int
) {

    val infiniteTransition = rememberInfiniteTransition(label = "")

    val floatY by infiniteTransition.animateFloat(
        initialValue = -8f,
        targetValue = 8f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = duration, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = ""
    )

    Image(
        painter = painterResource(id = R.drawable.flowers),
        contentDescription = null,
        modifier = Modifier
            .offset(y = floatY.dp)
            .size(size.dp),
        contentScale = ContentScale.Fit
    )
}
