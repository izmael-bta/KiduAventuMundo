package com.ismael.kiduaventumundo.kiduaventumundo.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ismael.kiduaventumundo.kiduaventumundo.front.models.UserProfileUi

@Composable
fun RegisterCard(
    modifier: Modifier = Modifier,
    onRegister: (UserProfileUi) -> Unit
) {

    var name by remember { mutableStateOf("") }
    var nickname by remember { mutableStateOf("") }
    var age by remember { mutableStateOf("") }

    Column(
        modifier = modifier
            .clip(RoundedCornerShape(30.dp))
            .background(Color.White.copy(alpha = 0.95f))
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Text(
            text = "Registrarse",
            fontSize = 22.sp
        )

        Spacer(Modifier.height(16.dp))

        OutlinedTextField(
            value = name,
            onValueChange = { name = it },
            label = { Text("Nombre") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(Modifier.height(12.dp))

        OutlinedTextField(
            value = nickname,
            onValueChange = { nickname = it },
            label = { Text("Nickname") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(Modifier.height(12.dp))

        OutlinedTextField(
            value = age,
            onValueChange = { age = it },
            label = { Text("Edad") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(Modifier.height(20.dp))

        Button(
            onClick = {
                val profile = UserProfileUi(
                    name = name,
                    username = nickname,
                    age = age.toIntOrNull() ?: 0,
                    avatarId = 1
                )

                onRegister(profile)
            },
            shape = RoundedCornerShape(50),
            modifier = Modifier.fillMaxWidth(0.6f)
        ) {
            Text("Continuar")
        }
    }
}

