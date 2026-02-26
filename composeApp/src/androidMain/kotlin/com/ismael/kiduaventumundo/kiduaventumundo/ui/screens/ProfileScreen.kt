package com.ismael.kiduaventumundo.kiduaventumundo.ui.screens

// ============================
// Android / Context
// ============================
import androidx.compose.ui.platform.LocalContext

// ============================
// Compose Foundation
// ============================
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll

// ============================
// Animations
// ============================
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween

// ============================
// Material 3
// ============================
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text

// ============================
// Runtime
// ============================
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue

// ============================
// UI
// ============================
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

// ============================
// Project
// ============================
import com.ismael.kiduaventumundo.kiduaventumundo.R
import com.ismael.kiduaventumundo.kiduaventumundo.ui.model.Avatar
import com.ismael.kiduaventumundo.kiduaventumundo.ui.utils.SoundManager
import front.models.UserProfileUi

@Composable
fun ProfileScreen(
    profile: UserProfileUi,
    avatars: List<Avatar>,
    selectedAvatar: Avatar,
    onAvatarSelected: (Avatar) -> Unit,
    onSaveAvatar: () -> Unit,
    onLogout: () -> Unit
) {

    var tempSelectedAvatar by remember { mutableStateOf(selectedAvatar) }

    val context = LocalContext.current
    val soundManager = remember { SoundManager(context) }

    DisposableEffect(Unit) {
        onDispose {
            soundManager.release()
        }
    }

    // Animación flotante avatar principal
    val infiniteTransition = rememberInfiniteTransition(label = "")
    val floatOffset by infiniteTransition.animateFloat(
        initialValue = -10f,
        targetValue = 10f,
        animationSpec = infiniteRepeatable(
            animation = tween(3000, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ), label = ""
    )

    Box(modifier = Modifier.fillMaxSize()) {

        // Fondo
        Image(
            painter = painterResource(id = R.drawable.fondo_saf),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Spacer(modifier = Modifier.height(60.dp))

            // =============================
            // AVATAR PRINCIPAL
            // =============================
            Box(
                modifier = Modifier
                    .offset(y = floatOffset.dp)
                    .size(170.dp)
                    .shadow(20.dp, CircleShape)
                    .clip(CircleShape)
                    .background(Color(0xFFFFE082))
                    .padding(16.dp),
                contentAlignment = Alignment.Center
            ) {
                Image(
                    painter = painterResource(id = tempSelectedAvatar.imageRes),
                    contentDescription = null,
                    contentScale = ContentScale.Fit,
                    modifier = Modifier.fillMaxSize()
                )
            }

            Spacer(modifier = Modifier.height(28.dp))

            // =============================
            // CARD GLASS
            // =============================
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .shadow(18.dp, RoundedCornerShape(28.dp))
                    .clip(RoundedCornerShape(28.dp))
                    .background(Color.White.copy(alpha = 0.85f))
                    .padding(vertical = 30.dp)
            ) {
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {

                    Text(
                        text = profile.name.uppercase(),
                        fontSize = 26.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF2F2F2F)
                    )

                    Spacer(modifier = Modifier.height(6.dp))

                    Text(
                        text = "@${profile.username}",
                        fontSize = 15.sp,
                        color = Color(0xFF555555)
                    )

                    Spacer(modifier = Modifier.height(4.dp))

                    Text(
                        text = "${profile.age} años",
                        fontSize = 14.sp,
                        color = Color(0xFF777777)
                    )
                }
            }

            Spacer(modifier = Modifier.height(40.dp))

            Text(
                text = "Elige tu avatar",
                fontSize = 20.sp,
                fontWeight = FontWeight.SemiBold,
                color = Color.White
            )

            Spacer(modifier = Modifier.height(20.dp))

            // =============================
            // SELECTOR CON ANIMACIÓN
            // =============================
            LazyRow(
                horizontalArrangement = Arrangement.spacedBy(20.dp),
                contentPadding = PaddingValues(horizontal = 8.dp)
            ) {
                items(avatars) { avatar ->

                    val isSelected = avatar.id == tempSelectedAvatar.id

                    val scale by animateFloatAsState(
                        targetValue = if (isSelected) 1.15f else 1f,
                        animationSpec = spring(
                            dampingRatio = Spring.DampingRatioMediumBouncy
                        ), label = ""
                    )

                    Box(
                        modifier = Modifier
                            .graphicsLayer {
                                scaleX = scale
                                scaleY = scale
                            }
                            .size(95.dp)
                            .shadow(
                                elevation = if (isSelected) 16.dp else 6.dp,
                                shape = CircleShape
                            )
                            .clip(CircleShape)
                            .background(
                                brush = if (isSelected)
                                    Brush.radialGradient(
                                        colors = listOf(
                                            Color(0xFFFFF176),
                                            Color(0xFFFFC107)
                                        )
                                    )
                                else
                                    Brush.radialGradient(
                                        colors = listOf(
                                            Color.White.copy(alpha = 0.30f),
                                            Color.White.copy(alpha = 0.30f)
                                        )
                                    )
                            )
                            .border(
                                width = if (isSelected) 0.dp else 2.dp,
                                color = Color.White,
                                shape = CircleShape
                            )
                            .clickable {
                                if (tempSelectedAvatar.id != avatar.id) {
                                    tempSelectedAvatar = avatar
                                    soundManager.playSound(avatar.id)
                                }
                            }
                            .padding(12.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Image(
                            painter = painterResource(id = avatar.imageRes),
                            contentDescription = null,
                            contentScale = ContentScale.Fit,
                            modifier = Modifier.fillMaxSize()
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(50.dp))

            // =============================
            // BOTÓN
            // =============================
            Button(
                onClick = {
                    onAvatarSelected(tempSelectedAvatar)
                    onSaveAvatar()
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(64.dp)
                    .shadow(18.dp, RoundedCornerShape(30.dp)),
                shape = RoundedCornerShape(30.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.Transparent
                ),
                contentPadding = PaddingValues()
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(
                            brush = Brush.horizontalGradient(
                                listOf(
                                    Color(0xFF58CC02),
                                    Color(0xFF46A302)
                                )
                            )
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "Guardar avatar",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                }
            }

            Spacer(modifier = Modifier.height(28.dp))

            Button(
                onClick = { onLogout() },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp)
                    .shadow(12.dp, RoundedCornerShape(24.dp)),
                shape = RoundedCornerShape(24.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFFFFE5E5), // fondo rojo suave
                    contentColor = Color(0xFFB3261E)
                ),
                elevation = ButtonDefaults.buttonElevation(
                    defaultElevation = 0.dp
                )
            ) {
                Text(
                    text = "Cerrar sesión",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.SemiBold
                )
            }
        }
    }
}