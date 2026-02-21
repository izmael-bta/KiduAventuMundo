package com.ismael.kiduaventumundo.kiduaventumundo.ui.screens

// ============================
// Compose Foundation
// ============================
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape

// ============================
// Compose Material
// ============================
import androidx.compose.material3.*
import androidx.compose.runtime.Composable

// ============================
// Compose UI
// ============================
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp

// ============================
// App Logic
// ============================
import com.ismael.kiduaventumundo.kiduaventumundo.back.logic.EnglishManager

// ============================
// ViewModel
// ============================
import com.ismael.kiduaventumundo.kiduaventumundo.ui.viewmodel.ProfileViewModel

// ============================
// Components
// ============================
import com.ismael.kiduaventumundo.kiduaventumundo.ui.components.AnimatedCircle
import com.ismael.kiduaventumundo.kiduaventumundo.ui.components.BackGroundMenu
import com.ismael.kiduaventumundo.kiduaventumundo.ui.components.Stars

// ============================
// Resources
// ============================
import com.ismael.kiduaventumundo.kiduaventumundo.R

@Composable
fun MenuScreen(
    nickname: String,
    profileViewModel: ProfileViewModel,
    onGoEnglish: () -> Unit,
    onGoProfile: () -> Unit
) {

    val selectedAvatar = profileViewModel.selectedAvatar

    Box(modifier = Modifier.fillMaxSize()) {

        // Fondo animado
        BackGroundMenu()
        AnimatedCircle()
        Stars()

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 28.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Spacer(modifier = Modifier.height(90.dp))

            // =========================
            // AVATAR REAL DEL USUARIO
            // =========================
            Box(
                modifier = Modifier
                    .size(150.dp)
                    .shadow(20.dp, CircleShape)
                    .clip(CircleShape)
                    .background(Color.White.copy(alpha = 0.25f)),
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

            Spacer(modifier = Modifier.height(28.dp))

            // =========================
            // SALUDO
            // =========================
            Text(
                text = "Hola,",
                style = MaterialTheme.typography.titleMedium,
                color = Color.White.copy(alpha = 0.85f)
            )

            Text(
                text = nickname,
                style = MaterialTheme.typography.headlineLarge,
                color = Color.White
            )

            Spacer(modifier = Modifier.height(16.dp))

            // =========================
            // ESTRELLAS
            // =========================
            Box(
                modifier = Modifier
                    .shadow(12.dp, RoundedCornerShape(50))
                    .clip(RoundedCornerShape(50))
                    .background(Color.White.copy(alpha = 0.20f))
                    .padding(horizontal = 20.dp, vertical = 8.dp)
            ) {
                Text(
                    text = "⭐ ${EnglishManager.stars.value} estrellas",
                    style = MaterialTheme.typography.titleMedium,
                    color = Color.White
                )
            }

            Spacer(modifier = Modifier.height(70.dp))

            // =========================
            // BOTÓN PRINCIPAL
            // =========================
            Button(
                onClick = onGoEnglish,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(64.dp)
                    .shadow(16.dp, RoundedCornerShape(32.dp)),
                shape = RoundedCornerShape(32.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF6A5ACD)
                )
            ) {
                Text(
                    text = "Inglés",
                    style = MaterialTheme.typography.titleMedium,
                    color = Color.White
                )
            }

            Spacer(modifier = Modifier.height(22.dp))

            // =========================
            // BOTÓN SECUNDARIO
            // =========================
            Button(
                onClick = onGoProfile,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(60.dp),
                shape = RoundedCornerShape(28.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.White.copy(alpha = 0.20f)
                )
            ) {
                Text(
                    text = "Ajustes",
                    style = MaterialTheme.typography.titleMedium,
                    color = Color.White
                )
            }
        }
    }
}