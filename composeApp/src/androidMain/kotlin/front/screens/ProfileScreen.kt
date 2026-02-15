package com.ismael.kiduaventumundo.kiduaventumundo.front.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.ismael.kiduaventumundo.kiduaventumundo.front.components.AvatarPicker
import com.ismael.kiduaventumundo.kiduaventumundo.front.models.*

@Composable
fun ProfileScreen(
    avatars: List<AvatarOption>,
    profile: UserProfileUi,
    onSave: (UserProfileUi) -> Unit,
    onBack: () -> Unit
) {
    var selectedAvatarId by remember {
        mutableIntStateOf(profile.avatarId)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {

        Row(
            Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text("Perfil", style = MaterialTheme.typography.headlineSmall)
            TextButton(onClick = onBack) {
                Text("Volver")
            }
        }

        Text("Nombre: ${profile.name}")
        Text("Edad: ${profile.age}")
        Text("Usuario: ${profile.username}")

        HorizontalDivider()

        AvatarPicker(
            avatars = avatars,
            selectedAvatarId = selectedAvatarId,
            onSelect = { selectedAvatarId = it }
        )

        Button(
            onClick = {
                onSave(profile.copy(avatarId = selectedAvatarId))
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Guardar avatar")
        }
    }
}
