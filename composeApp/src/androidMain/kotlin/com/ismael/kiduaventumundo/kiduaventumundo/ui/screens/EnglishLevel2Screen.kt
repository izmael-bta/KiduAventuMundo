package com.ismael.kiduaventumundo.kiduaventumundo.front.english

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ismael.kiduaventumundo.kiduaventumundo.R
import com.ismael.kiduaventumundo.kiduaventumundo.back.data.english.EnglishLevel2Data
import com.ismael.kiduaventumundo.kiduaventumundo.back.logic.english.DialogConfirmAction
import com.ismael.kiduaventumundo.kiduaventumundo.back.logic.english.EnglishLevelSession
import com.ismael.kiduaventumundo.kiduaventumundo.ui.components.AnimatedCircle
import com.ismael.kiduaventumundo.kiduaventumundo.ui.components.CompleteCard

@Composable
fun EnglishLevel2Screen(
    onBack: () -> Unit,
    onFinished: (Int?) -> Unit
) {
    val questions = remember { EnglishLevel2Data.questions() }
    val session = remember { EnglishLevelSession(level = 2, totalActivities = questions.size) }
    var state = remember { mutableStateOf(session.state) }

    val current = questions[state.value.index]

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Image(
            painter = painterResource(R.drawable.fondo_purh),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
        )
    }
    Box(modifier = Modifier.fillMaxSize()){
        AnimatedCircle()
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
       // verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Spacer(modifier = Modifier.height(30.dp))
        Text("Nivel 2", fontSize = 18.sp, color = Color.White, fontFamily = FontFamily.SansSerif)
        Text("Objects", fontSize = 36.sp, fontWeight = FontWeight.Bold, color = Color.White, textAlign = TextAlign.Center)
        //Text("Actividad ${state.value.index + 1} / ${state.value.totalActivities}", style = MaterialTheme.typography.bodyMedium)
        // --- SECCIÓN DE ESTRELLAS Y RETO ---
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 16.dp),
            horizontalArrangement = Arrangement.Center
        ) {
            // CÁPSULA DE ESTRELLAS
            Surface(
                shape = CircleShape, // Forma de cápsula
                color = Color(0xFFFF9100) .copy(alpha = 0.4f), // Naranja translúcido
                border = BorderStroke(1.dp, Color.White.copy(alpha = 0.6f))
            ) {
                Text(
                    text = "${state.value.starsLevel} / ${state.value.passStars} ⭐",
                    modifier = Modifier.padding(horizontal = 20.dp, vertical = 8.dp),
                    style = MaterialTheme.typography.labelLarge.copy(
                        color = Color.White,
                        fontWeight = FontWeight.ExtraBold
                    )
                )
            }

            Spacer(modifier = Modifier.width(12.dp))

            // CÁPSULA RETO
            Surface(
                shape = CircleShape, // Forma de cápsula
                color = Color(0xFF039BE5).copy(alpha = 0.4f), // Azul translúcido
                border = BorderStroke(1.dp, Color.White.copy(alpha = 0.6f))
            ) {
                Text(
                    text = "${state.value.index + 1} / ${state.value.totalActivities}",
                    modifier = Modifier.padding(horizontal = 20.dp, vertical = 8.dp),
                    style = MaterialTheme.typography.labelLarge.copy(
                        color = Color.White,
                        fontWeight = FontWeight.ExtraBold
                    )
                )
            }
        }

        Card(Modifier.fillMaxWidth()) {
            Column(Modifier.padding(16.dp)) {
                Text(
                    current.promptEn,
                    style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold)
                )
                Spacer(Modifier.height(6.dp))
                Text("Tap 👆 the correct object", style = MaterialTheme.typography.bodyMedium)
                Spacer(Modifier.height(6.dp))
                Text(
                    "❌ ${state.value.mistakes}",            // E R R O R E S * * *
                    style = MaterialTheme.typography.bodySmall
                )
            }
        }
       /* Card(Modifier.fillMaxWidth()) {
            Column(Modifier.padding(16.dp)) {
                Text(
                    current.promptEn,
                    style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold)
                )
                Spacer(Modifier.height(6.dp))
                Text("Tap the correct object", style = MaterialTheme.typography.bodyMedium)
                Spacer(Modifier.height(6.dp))
                Text(
                    "Errores en esta actividad: ${state.value.mistakes}",
                    style = MaterialTheme.typography.bodySmall
                )
            }
        } */

        Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
            current.options.chunked(2).forEach { row ->
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    row.forEach { opt ->
                        Card(
                            modifier = Modifier
                                .weight(1f)
                                .height(84.dp)
                                .clickable(enabled = !state.value.locked) {
                                    val result = session.submitSelection(
                                        selectedId = opt.id,
                                        correctId = current.correctId
                                    )
                                    state.value = result.state
                                }
                        ) {
                            Column(
                                modifier = Modifier.fillMaxSize(),
                                horizontalAlignment = Alignment.CenterHorizontally,
                                verticalArrangement = Arrangement.Center
                            ) {
                                Text(opt.emoji, style = MaterialTheme.typography.headlineSmall)
                                Spacer(Modifier.height(4.dp))
                                Text(opt.labelEn, fontWeight = FontWeight.Bold)
                            }
                        }
                    }
                }
            }
        }

        state.value.feedback?.let {
            Text(it, style = MaterialTheme.typography.titleMedium)
        }

        Spacer(Modifier.weight(1f))

        OutlinedButton(onClick = onBack, modifier = Modifier
            .width(200.dp) .height(50.dp),
            shape = RoundedCornerShape(25.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xD2AD2605))
        ) {
            Text("Volver", fontWeight = FontWeight.Bold, color = Color.White)
        }
    }

    if (state.value.showActivityResultDialog) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Black.copy(alpha = 0.5f)),
            contentAlignment = Alignment.Center
        ) {
            CompleteCard(
                stars = state.value.activityStarsEarned,
                summaryText = when (state.value.activityStarsEarned) {
                    3 -> "Ganaste 3 estrellas en tu primer intento"
                    2 -> "Ganaste 2 estrellas en tu segundo intento"
                    1 -> "Ganaste 1 estrella en tu tercer intento"
                    else -> "Desde el cuarto intento ya no ganas estrellas"
                },
                onContinue = {
                    state.value = session.continueAfterActivityResult()
                }
            )
        }
    }

    if (state.value.showEndDialog) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Black.copy(alpha = 0.5f)),
            contentAlignment = Alignment.Center
        ) {
            CompleteCard(
                stars = if (state.value.passed) 3 else 1,
                summaryText = "Estrellas acumuladas del nivel: ${state.value.starsLevel}",
                warningText = if (state.value.passed) null else "Necesitas mas estrellas para desbloquear el siguiente nivel.",
                onContinue = {
                    val action = session.confirmDialog()
                    state.value = session.state
                    if (action == DialogConfirmAction.CONTINUE) {
                        onFinished(session.consumeNextLevelAfterCompletion())
                    }
                }
            )
        }
    }
}
