package com.ismael.kiduaventumundo.kiduaventumundo.ui.components

import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

/*import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember*/

@Composable
fun RegisterCard(
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit
){

        // Animación de Aparición
        var visible by remember { mutableStateOf(false) }

        val scale by animateFloatAsState(
        targetValue = if (visible) 1f else 0.9f,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioMediumBouncy,
            stiffness = Spring.StiffnessLow
            ),
        label = "cardScale"
        )
        LaunchedEffect(Unit) {
        visible = true
        }

        Card(
            modifier = modifier
            .fillMaxWidth(0.9f)
            .scale(scale),
            shape = RoundedCornerShape(30.dp),
            elevation = CardDefaults.cardElevation(
                12.dp
            )
        ){

            Column (
                modifier = Modifier
                    .background(Color.White.copy(alpha = 0.94f))
                    .padding(horizontal = 26.dp, vertical = 30.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ){
                content()
            }
        }

    }