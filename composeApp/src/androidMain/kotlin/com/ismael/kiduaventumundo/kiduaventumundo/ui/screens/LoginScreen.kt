package com.ismael.kiduaventumundo.kiduaventumundo.com.ismael.kiduaventumundo.kiduaventumundo.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.ismael.kiduaventumundo.kiduaventumundo.back.db.AppDatabaseHelper

@Composable
fun LoginScreen(
    onGoRegister: () -> Unit,
    onLoginSuccess: () -> Unit
) {
    val context = LocalContext.current
    val db = remember { AppDatabaseHelper(context) }

    var nickname by remember { mutableStateOf("") }
    var error by remember { mutableStateOf<String?>(null) }
    var isLoading by remember { mutableStateOf(false) }

    Column(Modifier.fillMaxSize().padding(16.dp)) {
        Text("Iniciar sesión", style = MaterialTheme.typography.headlineSmall)
        Spacer(Modifier.height(16.dp))

        OutlinedTextField(
            value = nickname,
            onValueChange = { nickname = it },
            label = { Text("Nickname") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true
        )

        if (error != null) {
            Spacer(Modifier.height(10.dp))
            Text(error!!, color = MaterialTheme.colorScheme.error)
        }

        Spacer(Modifier.height(16.dp))
        Button(
            onClick = {
                error = null
                val nick = nickname.trim()

                if (nick.isBlank()) {
                    error = "Ingresa tu nickname."
                    return@Button
                }

                isLoading = true
                val userId = db.getUserIdByNickname(nick)

                if (userId == null) {
                    error = "Usuario no encontrado. Crea una cuenta."
                    isLoading = false
                    return@Button
                }

                db.setSession(userId)
                isLoading = false
                onLoginSuccess()
            },
            modifier = Modifier.fillMaxWidth(),
            enabled = !isLoading
        ) {
            Text(if (isLoading) "Entrando..." else "Entrar")
        }

        Spacer(Modifier.height(10.dp))
        TextButton(
            onClick = onGoRegister,
            modifier = Modifier.fillMaxWidth(),
            enabled = !isLoading
        ) {
            Text("Crear cuenta")
        }
    }
}
