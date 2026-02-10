package com.ismael.kiduaventumundo.kiduaventumundo.front

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(
    onGoLogin: () -> Unit,
    onGoMenu: () -> Unit
) {
    // Por ahora: simula revisión de sesión
    // En el siguiente paso lo reemplazamos por FirebaseAuth.currentUser
    LaunchedEffect(Unit) {
        delay(900)
        val hasSession = false
        if (hasSession) onGoMenu() else onGoLogin()
    }

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text("Kidu AventuMundo", style = MaterialTheme.typography.headlineMedium)
            Spacer(Modifier.height(12.dp))
            CircularProgressIndicator()
        }
    }
}
