package com.ismael.kiduaventumundo.kiduaventumundo.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.ismael.kiduaventumundo.kiduaventumundo.back.logic.EnglishManager
import com.ismael.kiduaventumundo.kiduaventumundo.ui.components.AnimatedCircle
import com.ismael.kiduaventumundo.kiduaventumundo.ui.components.BackGroundMenu

import com.ismael.kiduaventumundo.kiduaventumundo.ui.components.Stars


@Composable
fun MenuScreen(
    nickname: String,
    onGoEnglish: () -> Unit,
    onGoProfile: () -> Unit
) {

    Box(
        modifier = Modifier.fillMaxSize()
    ) {

        //  Fondo
        BackGroundMenu()

        // Circulos Animados
        AnimatedCircle()

        //  Estrellas
        Stars()

        //  Contenido centrado
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Text(
                text = "Hola, $nickname ",
                style = MaterialTheme.typography.headlineMedium,
                color = Color.White
            )

            Spacer(modifier = Modifier.height(12.dp))

            Text(
                text = "⭐ ${EnglishManager.stars.value}",
                style = MaterialTheme.typography.titleMedium,
                color = Color.White
            )

            Spacer(modifier = Modifier.height(50.dp))

            Button(
                onClick = onGoEnglish,
                modifier = Modifier
                    .width(240.dp)
                    .height(55.dp),
                shape = MaterialTheme.shapes.large
            ) {
                Text("Inglés")
            }

            Spacer(modifier = Modifier.height(20.dp))

            Button(
                onClick = onGoProfile,
                modifier = Modifier
                    .width(240.dp)
                    .height(55.dp),
                shape = MaterialTheme.shapes.large
            ) {
                Text("Ajustes")
            }
        }
    }
}