package com.ismael.kiduaventumundo.kiduaventumundo.front.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun MenuScreen(
    onLogout: () -> Unit
) {
    // Luego: traer de Firestore nickname + stars
    val nickname = "Isma"
    val stars = 0

    Column(Modifier.fillMaxSize().padding(16.dp)) {
        Text("Menú", style = MaterialTheme.typography.headlineSmall)
        Spacer(Modifier.height(8.dp))
        Text("Hola, $nickname")
        Text("⭐ $stars")

        Spacer(Modifier.height(16.dp))
        Button(onClick = { /* Inglés */ }, modifier = Modifier.fillMaxWidth()) { Text("Inglés") }
        Spacer(Modifier.height(10.dp))
        Button(onClick = { /* Español */ }, modifier = Modifier.fillMaxWidth()) { Text("Español") }
        Spacer(Modifier.height(10.dp))
        Button(onClick = { /* Matemáticas */ }, modifier = Modifier.fillMaxWidth()) { Text("Matemáticas") }

        Spacer(Modifier.height(20.dp))
        OutlinedButton(onClick = onLogout, modifier = Modifier.fillMaxWidth()) {
            Text("Cerrar sesión")
        }
    }
}
