package com.ismael.kiduaventumundo.kiduaventumundo.ui.screens

// ================= ANDROID SDK =================
import android.os.Build

// ================= COMPOSE - FOUNDATION =================
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape

// ================= COMPOSE - MATERIAL =================
import androidx.compose.material3.*

// ================= COMPOSE - RUNTIME =================
import androidx.compose.runtime.*

// ================= COMPOSE - UI CORE =================
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

// ================= LIBRERÍAS EXTERNAS =================
import coil.ImageLoader
import coil.compose.AsyncImage
import coil.decode.GifDecoder
import coil.decode.ImageDecoderDecoder

// ================= PROYECTO =================
import com.ismael.kiduaventumundo.kiduaventumundo.R
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

    val imageLoader = ImageLoader.Builder(context)
        .components {
            if (Build.VERSION.SDK_INT >= 28) {
                add(ImageDecoderDecoder.Factory())
            } else {
                add(GifDecoder.Factory())
            }
        }
        .build()

    Box(modifier = Modifier.fillMaxSize()) {

        //  Fondo
        AsyncImage(
            model = R.drawable.fondo_registro,
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )

        //  Búho animado
        AsyncImage(
            model = R.drawable.hello,
            imageLoader = imageLoader,
            contentDescription = "Buho saludando",
            modifier = Modifier
                .size(260.dp)
                .align(Alignment.TopCenter)
                .padding(top = 50.dp)
        )

        //  Card
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp)
                .align(Alignment.Center)
                .offset(y = 70.dp),
            shape = RoundedCornerShape(32.dp),
            elevation = CardDefaults.cardElevation(defaultElevation = 16.dp)
        ) {

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                Text(
                    text = "Iniciar Sesión",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold
                )

                Spacer(modifier = Modifier.height(24.dp))

                OutlinedTextField(
                    value = nickname,
                    onValueChange = { nickname = it },
                    label = { Text("Nickname") },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true
                )

                if (error != null) {
                    Spacer(modifier = Modifier.height(10.dp))
                    Text(
                        text = error!!,
                        color = MaterialTheme.colorScheme.error
                    )
                }

                Spacer(modifier = Modifier.height(24.dp))

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
                            error = "Usuario no encontrado."
                            isLoading = false
                            return@Button
                        }

                        db.setSession(userId)
                        isLoading = false
                        onLoginSuccess()
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(55.dp),
                    shape = RoundedCornerShape(20.dp),
                    enabled = !isLoading
                ) {
                    Text(if (isLoading) "Entrando..." else "Entrar")
                }

                Spacer(modifier = Modifier.height(12.dp))

                //  MENSAJE
                TextButton(
                    onClick = onGoRegister,
                    enabled = !isLoading
                ) {
                    Text("¿No tienes cuenta? Regístrate")
                }
            }
        }
    }
}