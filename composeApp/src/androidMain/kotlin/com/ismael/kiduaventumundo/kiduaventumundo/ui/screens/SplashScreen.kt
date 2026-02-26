package com.ismael.kiduaventumundo.kiduaventumundo.ui.screens

import androidx.compose.animation.core.*
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kiduaventumundo.composeapp.generated.resources.Res
import kiduaventumundo.composeapp.generated.resources.fondo_inicio
import kiduaventumundo.composeapp.generated.resources.logo1
import org.jetbrains.compose.resources.painterResource

@Composable
fun SplashScreen(
    hasSession: Boolean,
    onGoLogin: () -> Unit,
    onGoMenu: () -> Unit
) {

    val goNext = { if (hasSession) onGoMenu() else onGoLogin() }

    val infiniteTransition = rememberInfiniteTransition(label = "owl")

    val floatY by infiniteTransition.animateFloat(
        initialValue = -10f,
        targetValue = 10f,
        animationSpec = infiniteRepeatable(
            animation = tween(2600, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "float"
    )

    val scale by infiniteTransition.animateFloat(
        initialValue = 0.98f,
        targetValue = 1.04f,
        animationSpec = infiniteRepeatable(
            animation = tween(3000, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "scale"
    )

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {


        Image(
            painter = painterResource(Res.drawable.fondo_inicio),
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(vertical = 60.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceEvenly
        ) {


            Text(
                text = buildAnnotatedString {
                    withStyle(
                        style = SpanStyle(
                            color = Color(0xFF063697),
                            fontWeight = FontWeight.ExtraBold,
                            fontSize = 42.sp
                        )
                    ) { append("Kidu ") }

                    withStyle(
                        style = SpanStyle(
                            color = Color(0xFF820802),
                            fontWeight = FontWeight.ExtraBold,
                            fontSize = 42.sp
                        )
                    ) { append("World") }
                }
            )

            // Pajaro ese  ANIMADO
            Image(
                painter = painterResource(Res.drawable.logo1),
                contentDescription = "Logo",
                modifier = Modifier
                    .size(260.dp)
                    .offset(y = floatY.dp)
                    .scale(scale)
            )


            SwipeToStart(
                onComplete = { goNext() }
            )
        }
    }
}

@Composable
fun SwipeToStart(
    onComplete: () -> Unit
) {

    val trackWidth = 320.dp
    val thumbSize = 64.dp

    val maxWidthPx = with(LocalDensity.current) {
        (trackWidth - thumbSize).toPx()
    }

    var offsetX by remember { mutableStateOf(0f) }
    val animatedOffsetX by animateFloatAsState(offsetX)

    Box(
        modifier = Modifier
            .width(trackWidth)
            .height(70.dp)
            .clip(RoundedCornerShape(100)) // Bordes totalmente redondeados
            .background(Color.White.copy(alpha = 0.25f)),
        contentAlignment = Alignment.CenterStart
    ) {

        Text(
            text = "Desliza para comenzar",
            color = Color.White,
            fontSize = 15.sp,
            modifier = Modifier.align(Alignment.Center)
        )

        Box(
            modifier = Modifier
                .offset { IntOffset(animatedOffsetX.toInt(), 0) }
                .size(thumbSize)
                .clip(CircleShape)
                .background(Color(0xFF4CAF50))
                .pointerInput(Unit) {
                    detectDragGestures(
                        onDrag = { change, dragAmount ->
                            change.consume()
                            offsetX = (offsetX + dragAmount.x)
                                .coerceIn(0f, maxWidthPx)
                        },
                        onDragEnd = {
                            if (offsetX > maxWidthPx * 0.85f) {
                                onComplete()
                            } else {
                                offsetX = 0f
                            }
                        }
                    )
                },
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "➜",
                color = Color.White,
                fontSize = 20.sp
            )
        }
    }
}


/*
@Composable
fun SplashScreen(
    hasSession: Boolean,
    onGoLogin: () -> Unit,
    onGoMenu: () -> Unit
) {

    val goNext = { if (hasSession) onGoMenu() else onGoLogin() }

    // Animaciones del búho
    val infiniteTransition = rememberInfiniteTransition(label = "animations")

    val offsetY by infiniteTransition.animateFloat(
        initialValue = -15f,
        targetValue = 15f,
        animationSpec = infiniteRepeatable(
            animation = tween(2000, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "float"
    )

    val scale by infiniteTransition.animateFloat(
        initialValue = 0.95f,
        targetValue = 1.05f,
        animationSpec = infiniteRepeatable(
            animation = tween(2500, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "breathing"
    )

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {

        // Fondo
        Image(
            painter = painterResource(Res.drawable.fondo_inicio),
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )

        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceEvenly
        ) {

            // Título
            Text(
                text = buildAnnotatedString {
                    withStyle(
                        style = SpanStyle(
                            color = Color(0xFF063697),
                            fontWeight = FontWeight.ExtraBold,
                            fontSize = 55.sp
                        )
                    ) { append("Kidu") }

                    withStyle(
                        style = SpanStyle(
                            color = Color(0xFF820802),
                            fontWeight = FontWeight.ExtraBold,
                            fontSize = 55.sp
                        )
                    ) { append("World") }
                }
            )

            // Búho animado
            Image(
                painter = painterResource(Res.drawable.logo1),
                contentDescription = "Logo Kidu",
                modifier = Modifier
                    .size(300.dp)
                    .offset(y = offsetY.dp)
                    .scale(scale)
            )

            // Swipe
            SwipeToStart(
                onComplete = { goNext() }
            )
        }
    }
}

@Composable
fun SwipeToStart(
    onComplete: () -> Unit
) {

    val maxWidth = 280.dp
    val circleSize = 60.dp

    val maxWidthPx = with(LocalDensity.current) {
        (maxWidth - circleSize).toPx()
    }

    var offsetX by remember { mutableStateOf(0f) }
    val animatedOffsetX by animateFloatAsState(offsetX)

    Box(
        modifier = Modifier
            .width(maxWidth)
            .height(circleSize)
            .background(Color.White.copy(alpha = 0.2f), CircleShape)
    ) {

        Text(
            text = "Desliza para comenzar",
            color = Color.White,
            modifier = Modifier.align(Alignment.Center)
        )

        Box(
            modifier = Modifier
                .offset { IntOffset(animatedOffsetX.toInt(), 0) }
                .size(circleSize)
                .background(Color(0xFF4CAF50), CircleShape)
                .pointerInput(Unit) {
                    detectDragGestures(
                        onDrag = { change, dragAmount ->
                            change.consume()
                            offsetX = (offsetX + dragAmount.x)
                                .coerceIn(0f, maxWidthPx)
                        },
                        onDragEnd = {
                            if (offsetX > maxWidthPx * 0.8f) {
                                onComplete()
                            } else {
                                offsetX = 0f
                            }
                        }
                    )
                },
            contentAlignment = Alignment.Center
        ) {
            Text(">", color = Color.White)
        }
    }
}

 */