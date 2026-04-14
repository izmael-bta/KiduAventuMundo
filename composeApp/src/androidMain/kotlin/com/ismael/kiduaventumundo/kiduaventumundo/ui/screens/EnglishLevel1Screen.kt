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
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ismael.kiduaventumundo.kiduaventumundo.R
import com.ismael.kiduaventumundo.kiduaventumundo.back.data.english.EnglishLevel1Data
import com.ismael.kiduaventumundo.kiduaventumundo.back.logic.english.DialogConfirmAction
import com.ismael.kiduaventumundo.kiduaventumundo.back.logic.english.EnglishLevelSession
import com.ismael.kiduaventumundo.kiduaventumundo.ui.components.AnimatedCircle
import com.ismael.kiduaventumundo.kiduaventumundo.ui.components.AnimatedFlower
import com.ismael.kiduaventumundo.kiduaventumundo.ui.components.CompleteCard
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun EnglishLevel1Screen(
    onBack: () -> Unit,
    onFinished: (Int?) -> Unit
) {
    val questions = remember { EnglishLevel1Data.questions() }
    val session = remember { EnglishLevelSession(level = 1, totalActivities = questions.size) }
    var state = remember { mutableStateOf(session.state) }
    val scope = rememberCoroutineScope()

    val current = questions[state.value.index]

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Image(
            painter = painterResource(R.drawable.fondo_aqua),
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
    ) {
        Spacer(modifier = Modifier.height(30.dp))
        Text("Nivel 1", fontSize = 18.sp, color = Color.White, fontFamily = FontFamily.SansSerif)
        Text("Colors", fontSize = 36.sp, fontWeight = FontWeight.Bold, color = Color.White, textAlign = TextAlign.Center)
                                // 32.sp
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
        /*Text(
            "* ${state.value.starsLevel} / ${state.value.passStars}",
            shape = RoundedCornerShape(50.dp),
            color = Color(0xFFF8134),
        )
        Text(
            "Actividad ",
            style = MaterialTheme.typography.bodyMedium
        )*/
//                         ===== Card Instruction ======
        Card(modifier = Modifier.fillMaxWidth() .wrapContentHeight(),
            shape = RoundedCornerShape(40.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White)
         ) {
            Column(Modifier.padding(16.dp)) {
                Text(
                    current.promptEn,
                    style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold)
                )
                Spacer(Modifier.height(6.dp))
                Text("Tap 👆 the correct color", style = MaterialTheme.typography.bodyMedium)
                Spacer(Modifier.height(6.dp))
                Text(
                    "❌ ${state.value.mistakes}",            // E R R O R E S * * *
                    style = MaterialTheme.typography.bodySmall
                )
            }
        }
        Spacer(modifier = Modifier.width(12.dp))         // Espacio Pendiente * *

        Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
            current.options.chunked(2).forEach { row ->
                Row(
                    Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    row.forEach { opt ->
                        Box(
                            modifier = Modifier
                                .weight(1f)
                                .height(70.dp)
                                .clip(RoundedCornerShape(16.dp))
                                .background(opt.color)
                                .then(
                                    if (!state.value.locked) {
                                        Modifier.clickable {
                                            val result = session.submitSelection(
                                                selectedId = opt.id,
                                                correctId = current.correctId
                                            )
                                            state.value = result.state

                                            if (result.isCorrect) {
                                                scope.launch {
                                                    delay(650)
                                                    state.value = session.advanceAfterCorrect()
                                                }
                                            }
                                        }
                                    } else {
                                        Modifier
                                    }
                                ),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                opt.labelEn,
                                color = Color.White,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }
                }
            }
        }
                            // P R O G R E S S  B A R

                                    // V O L V E R
        state.value.feedback?.let {
            Text(it, style = MaterialTheme.typography.titleMedium)
        }

        Spacer(Modifier.weight(1f))

        OutlinedButton(onClick = onBack, modifier = Modifier
            .width(160.dp) .height(50.dp),
            shape = RoundedCornerShape(25.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xD2AD2605))
            ) {
            Text("Volver", fontWeight = FontWeight.Bold, color = Color.White)
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
                totalPoints = state.value.starsLevel * 50,
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
