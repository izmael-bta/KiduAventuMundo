package com.ismael.kiduaventumundo.kiduaventumundo.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.ismael.kiduaventumundo.kiduaventumundo.ui.components.AnimatedCircle
import com.ismael.kiduaventumundo.kiduaventumundo.ui.components.BackGroundMenu
import com.ismael.kiduaventumundo.kiduaventumundo.ui.components.Stars
import com.ismael.kiduaventumundo.kiduaventumundo.ui.viewmodel.ProfileViewModel

@Composable
fun MenuScreen(
    nickname: String,
    starsCount: Int,
    profileViewModel: ProfileViewModel,
    onGoEnglish: () -> Unit,
    onGoProgress: () -> Unit,
    onGoProfile: () -> Unit
) {
    val selectedAvatar = profileViewModel.selectedAvatar
    val donationOptions = listOf(25, 50, 100, 200)
    var showDonationDialog by remember { mutableStateOf(false) }
    var selectedDonation by remember { mutableStateOf(donationOptions[1]) }
    var donationConfirmed by remember { mutableStateOf(false) }

    Box(modifier = Modifier.fillMaxSize()) {
        BackGroundMenu()
        AnimatedCircle()
        Stars()

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(84.dp))

            Box(
                modifier = Modifier
                    .size(152.dp)
                    .shadow(18.dp, CircleShape)
                    .clip(CircleShape)
                    .background(Color.White.copy(alpha = 0.26f)),
                contentAlignment = Alignment.Center
            ) {
                Image(
                    painter = painterResource(id = selectedAvatar.imageRes),
                    contentDescription = null,
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(20.dp)
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            Text(
                text = "Hola,",
                style = MaterialTheme.typography.titleMedium,
                color = Color.White.copy(alpha = 0.88f)
            )

            Text(
                text = nickname,
                style = MaterialTheme.typography.headlineLarge,
                color = Color.White,
                fontWeight = FontWeight.ExtraBold
            )

            Spacer(modifier = Modifier.height(14.dp))

            Box(
                modifier = Modifier
                    .shadow(10.dp, RoundedCornerShape(99.dp))
                    .clip(RoundedCornerShape(99.dp))
                    .background(Color.White.copy(alpha = 0.22f))
                    .padding(horizontal = 18.dp, vertical = 8.dp)
            ) {
                Text(
                    text = "$starsCount estrellas",
                    style = MaterialTheme.typography.titleMedium,
                    color = Color.White
                )
            }

            Spacer(modifier = Modifier.height(64.dp))

            Button(
                onClick = onGoEnglish,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(62.dp)
                    .shadow(14.dp, RoundedCornerShape(28.dp)),
                shape = RoundedCornerShape(28.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF4A7CDB))
            ) {
                Text(
                    text = "Inglés",
                    style = MaterialTheme.typography.titleMedium,
                    color = Color.White
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = onGoProgress,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(58.dp),
                shape = RoundedCornerShape(26.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.White.copy(alpha = 0.22f)
                )
            ) {
                Text(
                    text = "Progreso",
                    style = MaterialTheme.typography.titleMedium,
                    color = Color.White
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = onGoProfile,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(58.dp),
                shape = RoundedCornerShape(26.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.White.copy(alpha = 0.22f)
                )
            ) {
                Text(
                    text = "Perfil",
                    style = MaterialTheme.typography.titleMedium,
                    color = Color.White
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = {
                    showDonationDialog = true
                    donationConfirmed = false
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                shape = RoundedCornerShape(26.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFFFFD166),
                    contentColor = Color(0xFF5B3600)
                )
            ) {
                Text(
                    text = "Apoyar al programador",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )
            }
        }

        if (showDonationDialog) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color(0x99000F1F)),
                contentAlignment = Alignment.Center
            ) {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 24.dp),
                    shape = RoundedCornerShape(32.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = Color(0xFFFFF8EA)
                    ),
                    elevation = CardDefaults.cardElevation(defaultElevation = 16.dp)
                ) {
                    Column(
                        modifier = Modifier.padding(24.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = if (donationConfirmed) "Gracias por tu apoyo" else "Simulador de donacion",
                            style = MaterialTheme.typography.headlineSmall,
                            fontWeight = FontWeight.ExtraBold,
                            color = Color(0xFF5B3600),
                            textAlign = TextAlign.Center
                        )

                        Spacer(modifier = Modifier.height(12.dp))

                        Text(
                            text = if (donationConfirmed) {
                                "Se registro un apoyo simulado de $$selectedDonation MXN para el programador."
                            } else {
                                "Elige un monto para simular una donacion al desarrollador."
                            },
                            style = MaterialTheme.typography.bodyMedium,
                            color = Color(0xFF7A5A20),
                            textAlign = TextAlign.Center
                        )

                        if (!donationConfirmed) {
                            Spacer(modifier = Modifier.height(20.dp))

                            Row {
                                donationOptions.chunked(2).forEachIndexed { columnIndex, chunk ->
                                    Column(
                                        modifier = Modifier
                                            .weight(1f)
                                            .padding(start = if (columnIndex == 0) 0.dp else 8.dp, end = if (columnIndex == 0) 8.dp else 0.dp)
                                    ) {
                                        chunk.forEach { amount ->
                                            OutlinedButton(
                                                onClick = { selectedDonation = amount },
                                                modifier = Modifier
                                                    .fillMaxWidth()
                                                    .padding(vertical = 6.dp),
                                                shape = RoundedCornerShape(20.dp),
                                                border = androidx.compose.foundation.BorderStroke(
                                                    width = 2.dp,
                                                    color = if (selectedDonation == amount) Color(0xFFE09F3E) else Color(0xFFD8C39A)
                                                ),
                                                colors = ButtonDefaults.outlinedButtonColors(
                                                    containerColor = if (selectedDonation == amount) Color(0xFFFFE0A3) else Color.White,
                                                    contentColor = Color(0xFF5B3600)
                                                )
                                            ) {
                                                Text("$${amount} MXN", fontWeight = FontWeight.Bold)
                                            }
                                        }
                                    }
                                }
                            }
                        }

                        Spacer(modifier = Modifier.height(20.dp))

                        Button(
                            onClick = {
                                if (donationConfirmed) {
                                    showDonationDialog = false
                                } else {
                                    donationConfirmed = true
                                }
                            },
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(20.dp),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = Color(0xFFE09F3E),
                                contentColor = Color.White
                            )
                        ) {
                            Text(
                                text = if (donationConfirmed) "Cerrar" else "Confirmar apoyo",
                                fontWeight = FontWeight.Bold
                            )
                        }

                        Spacer(modifier = Modifier.height(10.dp))

                        OutlinedButton(
                            onClick = { showDonationDialog = false },
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(20.dp),
                            colors = ButtonDefaults.outlinedButtonColors(
                                contentColor = Color(0xFF7A5A20)
                            )
                        ) {
                            Text(
                                text = if (donationConfirmed) "Seguir explorando" else "Cancelar",
                                fontWeight = FontWeight.SemiBold
                            )
                        }
                    }
                }
            }
        }
    }
}
