package com.ismael.kiduaventumundo.kiduaventumundo.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import com.ismael.kiduaventumundo.kiduaventumundo.R
//import org.jetbrains.compose.resources.painterResource

// Que coincidan con ruta de recursos
//import kiduaventumundo.composeapp.generated.resources.Res
import kiduaventumundo.composeapp.generated.resources.logo1

@Composable
fun CompleteCard(
    stars: Int,
    totalPoints: Int,
    onContinue: () -> Unit
) {
    // Fondo suave
    val cardBgGradient = Brush.verticalGradient(
        colors = listOf(Color(0xFFB2EBF2), Color(0xFFE0F2F1))
    )

    Box(
        modifier = Modifier.fillMaxSize().padding(24.dp),
        contentAlignment = Alignment.Center
    ) {
        Card(
            modifier = Modifier.fillMaxWidth().wrapContentHeight(),
            shape = RoundedCornerShape(40.dp),
            colors = CardDefaults.cardColors(containerColor = Color.Transparent),
            elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
        ) {
            Column(
                modifier = Modifier
                    .background(cardBgGradient)
                    .padding(32.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {

                Text("🎉", fontSize = 40.sp)

                Text(
                    text = "¡Muy Bien!",
                    fontSize = 36.sp,
                    fontWeight = FontWeight.Black,
                    color = Color(0xFF006064)
                )

                Spacer(modifier = Modifier.height(16.dp))

                Image(
                    painter = painterResource(R.drawable.owl_wings),
                    contentDescription = "Búho KIDU",
                    modifier = Modifier.size(100.dp),
                    contentScale = ContentScale.Fit
                )

                Spacer(modifier = Modifier.height(8.dp))

                // 4. Puntos
                Text(
                    text = "Puntos: $totalPoints",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF006064)
                )

                Spacer(modifier = Modifier.height(24.dp))

                // 5. Las 3 Estrellas (Cambia color según el puntaje)
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    repeat(3) { index ->
                        val starColor = if (index < stars) Color(0xFFFFD54F) else Color.LightGray
                        Text("⭐", fontSize = 32.sp, color = starColor)
                    }
                } // P E N D I E N T E
                if (stars < 2) { // Ejemplo: si no alcanzó las estrellas suficientes
                    Text(
                        text = "¡Casi! Necesitas más estrellas para el siguiente nivel.",
                        fontSize = 14.sp,
                        color = Color.Red,
                        textAlign = TextAlign.Center
                    )
                }

                Spacer(modifier = Modifier.height(32.dp))

                // 6. Botón ¡Continuar!
                Button(
                    onClick = onContinue,
                    modifier = Modifier.fillMaxWidth(0.8f).height(55.dp),
                    shape = RoundedCornerShape(25.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFFB74D))
                ) {
                    Text(
                        text = "¡Continuar!",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                }
            }
        }
    }
 }