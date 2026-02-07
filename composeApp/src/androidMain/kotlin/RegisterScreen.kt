package com.ismael.kiduavendumundo.kiduavendumundo

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp

@Composable
fun RegisterScreen(
    onRegisterSuccess: () -> Unit,
    onBackToLogin: () -> Unit
) {
    var email by remember { mutableStateOf("") }
    var pass by remember { mutableStateOf("") }
    var name by remember { mutableStateOf("") }
    var age by remember { mutableStateOf("") }
    var nickname by remember { mutableStateOf("") }
    var avatarId by remember { mutableStateOf("avatar_01") } // placeholder
    var error by remember { mutableStateOf<String?>(null) }

    Column(Modifier.fillMaxSize().padding(16.dp)) {
        Text("Crear cuenta", style = MaterialTheme.typography.headlineSmall)
        Spacer(Modifier.height(16.dp))

        OutlinedTextField(email, { email = it }, label = { Text("Email") }, modifier = Modifier.fillMaxWidth())
        Spacer(Modifier.height(10.dp))
        OutlinedTextField(
            pass, { pass = it },
            label = { Text("Contraseña") },
            visualTransformation = PasswordVisualTransformation(),
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(Modifier.height(10.dp))
        OutlinedTextField(name, { name = it }, label = { Text("Nombre") }, modifier = Modifier.fillMaxWidth())
        Spacer(Modifier.height(10.dp))
        OutlinedTextField(age, { age = it }, label = { Text("Edad") }, modifier = Modifier.fillMaxWidth())
        Spacer(Modifier.height(10.dp))
        OutlinedTextField(nickname, { nickname = it }, label = { Text("Nickname") }, modifier = Modifier.fillMaxWidth())

        Spacer(Modifier.height(10.dp))
        Text("Avatar actual: $avatarId")
        // Luego hacemos un AvatarPicker real

        if (error != null) {
            Spacer(Modifier.height(10.dp))
            Text(error!!, color = MaterialTheme.colorScheme.error)
        }

        Spacer(Modifier.height(16.dp))
        Button(
            onClick = {
                error = null
                val ageNum = age.toIntOrNull()
                if (email.isBlank() || pass.isBlank() || name.isBlank() || nickname.isBlank() || ageNum == null) {
                    error = "Revisa: email, contraseña, nombre, edad válida y nickname."
                } else {
                    // Luego aquí: Firebase Auth + Firestore create profile
                    onRegisterSuccess()
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Crear y entrar")
        }

        Spacer(Modifier.height(10.dp))
        TextButton(onClick = onBackToLogin, modifier = Modifier.fillMaxWidth()) {
            Text("Volver a login")
        }
    }
}