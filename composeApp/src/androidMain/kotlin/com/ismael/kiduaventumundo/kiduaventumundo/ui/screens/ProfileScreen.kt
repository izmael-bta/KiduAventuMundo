package com.ismael.kiduaventumundo.kiduaventumundo.ui.screens


import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ismael.kiduaventumundo.kiduaventumundo.R
import com.ismael.kiduaventumundo.kiduaventumundo.ui.model.Avatar
import front.models.UserProfileUi
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.ui.platform.LocalContext
import com.ismael.kiduaventumundo.kiduaventumundo.ui.utils.SoundManager

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

    //  Liberar SoundPool cuando el composable muere
    DisposableEffect(Unit) {
        onDispose {
            soundManager.release()
        }
    }

    Box(modifier = Modifier.fillMaxSize()) {

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

            Spacer(modifier = Modifier.height(40.dp))


            // AVATAR PRINCIPAL

            Box(
                modifier = Modifier
                    .size(180.dp)
                    .clip(CircleShape)
                    .background(Color(0xFFFFE082)),
                contentAlignment = Alignment.Center
            ) {
                Image(
                    painter = painterResource(id = tempSelectedAvatar.imageRes),
                    contentDescription = null,
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp),
                    contentScale = ContentScale.Fit
                )
            }

            Spacer(modifier = Modifier.height(28.dp))


            // CARD INFO

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(24.dp))
                    .background(Color.White.copy(alpha = 0.30f))
                    .padding(vertical = 28.dp)
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
                        color = Color(0xFF4A4A4A)
                    )

                    Spacer(modifier = Modifier.height(4.dp))

                    Text(
                        text = "${profile.age} años",
                        fontSize = 14.sp,
                        color = Color(0xFF6B6B6B)
                    )
                }
            }

            Spacer(modifier = Modifier.height(36.dp))

            Text(
                text = "Elige tu avatar",
                fontSize = 18.sp,
                fontWeight = FontWeight.SemiBold,
                color = Color.White
            )

            Spacer(modifier = Modifier.height(20.dp))


            // SELECTOR CON SONIDO

            LazyRow(
                horizontalArrangement = Arrangement.spacedBy(20.dp),
                contentPadding = PaddingValues(horizontal = 8.dp)
            ) {
                items(avatars) { avatar ->

                    val isSelected = avatar.id == tempSelectedAvatar.id

                    Box(
                        modifier = Modifier
                            .size(90.dp)
                            .clip(CircleShape)
                            .background(
                                if (isSelected)
                                    Color(0xFFFFF3CD)
                                else
                                    Color.White.copy(alpha = 0.25f)
                            )
                            .border(
                                width = if (isSelected) 4.dp else 2.dp,
                                color = if (isSelected)
                                    Color(0xFFFFC107)
                                else
                                    Color.White,
                                shape = CircleShape
                            )
                            .clickable {

                                //  Solo si cambia selección
                                if (tempSelectedAvatar.id != avatar.id) {
                                    tempSelectedAvatar = avatar
                                    soundManager.playSound(avatar.id)
                                }

                            },
                        contentAlignment = Alignment.Center
                    ) {
                        Image(
                            painter = painterResource(id = avatar.imageRes),
                            contentDescription = null,
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(12.dp),
                            contentScale = ContentScale.Fit
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(48.dp))

            Button(
                onClick = {
                    onAvatarSelected(tempSelectedAvatar)
                    onSaveAvatar()
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(60.dp),
                shape = RoundedCornerShape(24.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF58CC02)
                )
            ) {
                Text(
                    text = "Guardar avatar",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
            }

            Spacer(modifier = Modifier.height(28.dp))

            Text(
                text = "Cerrar sesión",
                fontSize = 14.sp,
                color = Color(0xFFFF6B6B),
                modifier = Modifier.clickable { onLogout() }
            )

            Spacer(modifier = Modifier.height(30.dp))
        }
    }
}