package com.ismael.kiduaventumundo.kiduaventumundo.com.ismael.kiduaventumundo.kiduaventumundo.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.ismael.kiduaventumundo.kiduaventumundo.com.ismael.kiduaventumundo.kiduaventumundo.ui.components.AvatarPicker
import com.ismael.kiduaventumundo.kiduaventumundo.front.models.*

@Composable
fun RegisterScreen(
    avatars: List<AvatarOption>,
    onCreate: (UserProfileUi) -> Unit,
    onGoLogin: () -> Unit
) {
    var name by remember { mutableStateOf("") }
    var ageText by remember { mutableStateOf("") }
    var username by remember { mutableStateOf("") }
    var error by remember { mutableStateOf<String?>(null) }

    val defaultAvatarId = avatars.firstOrNull()?.id ?: 1
    var selectedAvatarId by remember { mutableIntStateOf(defaultAvatarId) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {

        Text("Crear cuenta", style = MaterialTheme.typography.headlineSmall)

        OutlinedTextField(
            value = name,
            onValueChange = { name = it },
            label = { Text("Nombre") },
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = ageText,
            onValueChange = { ageText = it },
            label = { Text("Edad") },
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = username,
            onValueChange = { username = it },
            label = { Text("Usuario / Nick") },
            modifier = Modifier.fillMaxWidth()
        )

        if (avatars.isNotEmpty()) {
            AvatarPicker(
                avatars = avatars,
                selectedAvatarId = selectedAvatarId,
                onSelect = { selectedAvatarId = it }
            )
        }

        if (error != null) {
            Text(
                text = error!!,
                color = MaterialTheme.colorScheme.error
            )
        }

        Button(
            onClick = {
                val cleanName = name.trim()
                val cleanUsername = username.trim()
                val age = ageText.toIntOrNull()

                if (cleanName.isBlank() || cleanUsername.isBlank()) {
                    error = "Completa nombre y usuario."
                    return@Button
                }
                if (age == null || age <= 0) {
                    error = "Ingresa una edad valida."
                    return@Button
                }

                error = null

                onCreate(
                    UserProfileUi(
                        name = cleanName,
                        age = age,
                        username = cleanUsername,
                        avatarId = selectedAvatarId
                    )
                )
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Crear cuenta")
        }

        TextButton(onClick = onGoLogin) {
            Text("Ya tengo cuenta")
        }
    }
}
