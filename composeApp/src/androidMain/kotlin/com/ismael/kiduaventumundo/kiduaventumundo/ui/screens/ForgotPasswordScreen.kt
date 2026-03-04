package com.ismael.kiduaventumundo.kiduaventumundo.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.platform.LocalContext
import com.ismael.kiduaventumundo.kiduaventumundo.back.db.AppDatabaseHelper
import com.ismael.kiduaventumundo.kiduaventumundo.back.logic.auth.PasswordRecoveryService
import com.ismael.kiduaventumundo.kiduaventumundo.back.logic.auth.PasswordResetResult
import com.ismael.kiduaventumundo.kiduaventumundo.back.logic.auth.SecurityQuestionResult

@Composable
fun ForgotPasswordScreen(
    onBackToLogin: () -> Unit
) {
    val context = LocalContext.current
    val recoveryService = remember { PasswordRecoveryService(AppDatabaseHelper(context)) }

    var nickname by remember { mutableStateOf("") }
    var securityQuestion by remember { mutableStateOf<String?>(null) }
    var securityAnswer by remember { mutableStateOf("") }
    var newPassword by remember { mutableStateOf("") }
    var error by remember { mutableStateOf<String?>(null) }
    var success by remember { mutableStateOf<String?>(null) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    listOf(Color(0xFFEDF4FF), Color(0xFFD9E8FF))
                )
            )
            .padding(20.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(24.dp),
            colors = CardDefaults.cardColors(
                containerColor = Color.White.copy(alpha = 0.95f)
            )
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp)
            ) {
                Text(
                    text = "Recuperar contraseña",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.ExtraBold,
                    color = Color(0xFF1E3A5F)
                )

                Spacer(modifier = Modifier.height(14.dp))

                OutlinedTextField(
                    value = nickname,
                    onValueChange = {
                        nickname = it
                        error = null
                        success = null
                    },
                    label = { Text("Nickname") },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true,
                    shape = RoundedCornerShape(14.dp)
                )

                Spacer(modifier = Modifier.height(10.dp))

                Button(
                    onClick = {
                        when (val result = recoveryService.getSecurityQuestion(nickname)) {
                            is SecurityQuestionResult.Success -> {
                                securityQuestion = result.question
                                error = null
                                success = null
                            }
                            is SecurityQuestionResult.Error -> {
                                securityQuestion = null
                                error = result.message
                                success = null
                            }
                        }
                    },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(14.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFF4A7CDB),
                        contentColor = Color.White
                    )
                ) {
                    Text("Ver pregunta de seguridad")
                }

                if (securityQuestion != null) {
                    Spacer(modifier = Modifier.height(14.dp))
                    Text(
                        text = "Pregunta:",
                        fontWeight = FontWeight.SemiBold,
                        color = Color(0xFF1E3A5F)
                    )
                    Text(
                        text = securityQuestion ?: "",
                        color = Color(0xFF2F4D6F)
                    )

                    Spacer(modifier = Modifier.height(10.dp))

                    OutlinedTextField(
                        value = securityAnswer,
                        onValueChange = {
                            securityAnswer = it
                            error = null
                        },
                        label = { Text("Respuesta de seguridad") },
                        modifier = Modifier.fillMaxWidth(),
                        singleLine = true,
                        shape = RoundedCornerShape(14.dp)
                    )

                    Spacer(modifier = Modifier.height(10.dp))

                    OutlinedTextField(
                        value = newPassword,
                        onValueChange = {
                            newPassword = it
                            error = null
                        },
                        label = { Text("Nueva contraseña") },
                        visualTransformation = PasswordVisualTransformation(),
                        modifier = Modifier.fillMaxWidth(),
                        singleLine = true,
                        shape = RoundedCornerShape(14.dp)
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    Button(
                        onClick = {
                            when (
                                val result = recoveryService.resetPassword(
                                    nickname = nickname,
                                    securityAnswer = securityAnswer,
                                    newPassword = newPassword
                                )
                            ) {
                                is PasswordResetResult.Success -> {
                                    error = null
                                    success = "Contrasena actualizada. Ya puedes iniciar sesion."
                                    securityAnswer = ""
                                    newPassword = ""
                                }
                                is PasswordResetResult.Error -> {
                                    success = null
                                    error = result.message
                                }
                            }
                        },
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(14.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color(0xFF2E9B64),
                            contentColor = Color.White
                        )
                    ) {
                        Text("Cambiar contraseña")
                    }
                }

                if (error != null) {
                    Spacer(modifier = Modifier.height(10.dp))
                    Text(text = error ?: "", color = MaterialTheme.colorScheme.error)
                }

                if (success != null) {
                    Spacer(modifier = Modifier.height(10.dp))
                    Text(text = success ?: "", color = Color(0xFF2E9B64))
                }

                Spacer(modifier = Modifier.height(14.dp))

                Button(
                    onClick = onBackToLogin,
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(14.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFF86A9F0),
                        contentColor = Color.White
                    )
                ) {
                    Text("Volver a iniciar sesion")
                }
            }
        }
    }
}
