package com.ismael.kiduaventumundo.kiduaventumundo.com.ismael.kiduaventumundo.kiduaventumundo.ui.components.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun EnglishScreen(onBack: () -> Unit) {
    Column(Modifier.fillMaxSize().padding(16.dp)) {
        Text("Módulo: Inglés", style = MaterialTheme.typography.headlineSmall)
        Spacer(Modifier.height(12.dp))
        Text("Aquí irán: colores, animales, objetos y sonidos.")
        Spacer(Modifier.height(20.dp))
        OutlinedButton(onClick = onBack) { Text("Volver") }
    }
}