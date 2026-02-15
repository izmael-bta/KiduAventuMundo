package com.ismael.kiduaventumundo.kiduaventumundo.com.ismael.kiduaventumundo.kiduaventumundo.ui.components.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun SplashScreen(
    hasSession: Boolean,
    onGoLogin: () -> Unit,
    onGoMenu: () -> Unit
) {
    val goNext = { if (hasSession) onGoMenu() else onGoLogin() }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(
                        Color(0xFF4FC3F7),
                        Color(0xFF81D4FA),
                        Color(0xFFA5D6A7)
                    )
                )
            )
            .padding(24.dp)
    ) {
        Column(
            modifier = Modifier.align(Alignment.TopCenter),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(Modifier.height(24.dp))
            Text(
                text = "Kidu AventuMundo",
                style = MaterialTheme.typography.headlineMedium,
                color = Color.White
            )
            Spacer(Modifier.height(8.dp))
            Text(
                text = "Aprende jugando",
                style = MaterialTheme.typography.titleMedium,
                color = Color.White.copy(alpha = 0.95f)
            )
        }

        Box(
            modifier = Modifier.align(Alignment.Center),
            contentAlignment = Alignment.Center
        ) {
            Box(
                modifier = Modifier
                    .size(110.dp)
                    .shadow(10.dp, CircleShape)
                    .clip(CircleShape)
                    .background(Color.White.copy(alpha = 0.25f))
            )

            IconButton(
                onClick = goNext,
                modifier = Modifier
                    .size(84.dp)
                    .shadow(12.dp, CircleShape)
                    .clip(CircleShape)
                    .background(Color(0xFF00C853))
            ) {
                Text(
                    text = ">",
                    color = Color.White,
                    style = MaterialTheme.typography.headlineMedium
                )
            }
        }

        Text(
            text = "Toca para comenzar",
            modifier = Modifier.align(Alignment.BottomCenter),
            style = MaterialTheme.typography.titleMedium,
            color = Color.White
        )
    }
}
