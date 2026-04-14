package com.ismael.kiduaventumundo.kiduaventumundo.back.logic.english

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.LineHeightStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ismael.kiduaventumundo.kiduaventumundo.R
import com.ismael.kiduaventumundo.kiduaventumundo.back.logic.EnglishManager
import com.ismael.kiduaventumundo.kiduaventumundo.ui.components.AnimatedCloud
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.painterResource

/**
 * Opcion generica para niveles tipo quiz.
 */
data class QuizOption(
    val id: String,
    val label: String
)

/**
 * Pregunta generica para niveles tipo quiz.
 */
data class QuizQuestion(
    val prompt: String,
    val hint: String,
    val correctId: String,
    val options: List<QuizOption>
)

/**
 * Plantilla reutilizable de UI+logica para niveles de quiz.
 *
 * Nota: esta funcion vive en back por estructura actual del proyecto.
 */
@Composable
fun EnglishQuizLevelScreen(
    levelNumber: Int,
    levelTitle: String,
    unlockMessage: String,
    questionsSource: List<QuizQuestion>,
    onBack: () -> Unit,
    onFinished: (Int?) -> Unit
) {
    val passStars = 13
    val questions = remember { questionsSource }
    val totalActivities = questions.size
    val startIndex = remember {
        EnglishManager.consumeStartActivity(level = levelNumber).coerceIn(0, questions.lastIndex)
    }

    var index by remember { mutableIntStateOf(startIndex) }
    var starsLevel by remember {
        mutableIntStateOf(EnglishManager.getLevelStars(levelNumber, totalActivities))
    }
    var mistakes by remember { mutableIntStateOf(0) }
    val activityStars = remember {
        mutableStateListOf<Int?>().apply {
            addAll(EnglishManager.getActivityStars(levelNumber, totalActivities))
        }
    }

    var feedback by remember { mutableStateOf<String?>(null) }
    var locked by remember { mutableStateOf(false) }

    var showEndDialog by remember { mutableStateOf(false) }
    var passed by remember { mutableStateOf(false) }
    val scope = rememberCoroutineScope()

    val current = questions[index]

    // Reglas de puntuacion por actividad segun errores acumulados.
    fun earnedStarsForThisActivity(m: Int): Int = when {
        m == 0 -> 3
        m == 1 -> 2
        m == 2 -> 1
        else -> 0
    }

    // Reinicio de flujo local de nivel (sin perder mejores estrellas ya registradas).
    fun restartLevel() {
        index = 0
        starsLevel = activityStars.sumOf { it ?: 0 }
        mistakes = 0
        feedback = null
        locked = false
        showEndDialog = false
        passed = false
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
         horizontalAlignment = Alignment.CenterHorizontally  //verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Spacer(modifier = Modifier.height(30.dp))
        Text("Nivel: $levelNumber", fontSize = 18.sp, color = Color.White, fontFamily = FontFamily.SansSerif)
        Text("$levelTitle", fontSize = 36.sp, fontWeight = FontWeight.Bold, color = Color.White, textAlign = TextAlign.Center)
        // --- SECCIÓN DE ESTRELLAS Y RETO ---
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 16.dp),
            horizontalArrangement = Arrangement.Center
        ) {
            // ======= CÁPSULA DE ESTRELLAS =======
            Surface(
                shape = CircleShape, // Forma de cápsula
                color = Color(0xFFFF9100) .copy(alpha = 0.4f), // Naranja translúcido
                border = BorderStroke(1.dp, Color.White.copy(alpha = 0.6f))
            ) {
                Text(
                    text = "${starsLevel} / ${passStars} ⭐",
                    modifier = Modifier.padding(horizontal = 20.dp, vertical = 8.dp),
                    style = MaterialTheme.typography.labelLarge.copy(
                        color = Color.White,
                        fontWeight = FontWeight.ExtraBold
                    )
                )
            }

            Spacer(modifier = Modifier.width(12.dp))

            // ======= CÁPSULA RETO =======
            Surface(
                shape = CircleShape, // Forma de cápsula
                color = Color(0xFF039BE5).copy(alpha = 0.4f), // Azul translúcido
                border = BorderStroke(1.dp, Color.White.copy(alpha = 0.6f))
            ) {
                Text(
                    text = "${index + 1} / ${totalActivities}",
                    modifier = Modifier.padding(horizontal = 20.dp, vertical = 8.dp),
                    style = MaterialTheme.typography.labelLarge.copy(
                        color = Color.White,
                        fontWeight = FontWeight.ExtraBold
                    )
                )
            }
        }
        //      ====== E R R O R E S ======
        Card(Modifier.fillMaxWidth()) {
            Column(Modifier.padding(16.dp)) {
                Text(
                    current.prompt,
                    style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold)
                )
                Spacer(Modifier.height(6.dp))
                Text(current.hint, style = MaterialTheme.typography.bodyMedium)
                Spacer(Modifier.height(6.dp))
                Text("❌: $mistakes", style = MaterialTheme.typography.bodySmall) // Errores en esta actividad ============
            }
        }

        Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
            current.options.chunked(2).forEach { row ->
                Row(
                    Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    row.forEach { opt ->
                        Card(
                            modifier = Modifier
                                .weight(1f)
                                .height(84.dp)
                                .clickable(enabled = !locked) {
                                    val ok = opt.id == current.correctId
                                    if (ok) {
                                        locked = true
                                        val earned = earnedStarsForThisActivity(mistakes)
                                        EnglishManager.recordActivityResult(
                                            level = levelNumber,
                                            activityIndex = index,
                                            starsEarned = earned,
                                            totalActivities = totalActivities
                                        )
                                        activityStars[index] = maxOf(activityStars[index] ?: -1, earned)
                                        starsLevel = activityStars.sumOf { it ?: 0 }
                                        feedback = "+$earned *"

                                        scope.launch {
                                            delay(650)
                                            val isLast = index == questions.lastIndex
                                            if (!isLast) {
                                                index++
                                                mistakes = 0
                                                feedback = null
                                                locked = false
                                            } else {
                                                passed = starsLevel >= passStars
                                                showEndDialog = true
                                            }
                                        }
                                    } else {
                                        mistakes++
                                        feedback = "Intenta de nuevo😅"
                                    }
                                }
                        ) {
                            Column(
                                modifier = Modifier.fillMaxSize(),
                                horizontalAlignment = Alignment.CenterHorizontally,
                                verticalArrangement = Arrangement.Center
                            ) {
                                Text(opt.label, fontWeight = FontWeight.Bold)
                                Spacer(Modifier.height(4.dp))           // P R U E B A  E S P A C I O * *
                                //Text(opt.labelEn, fontWeight = FontWeight.Bold)
                            }
                        }
                    }
                }
            }
        }
//                         ====== V O L V E R ======
        feedback?.let {
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

    if (showEndDialog) {
        AlertDialog(
            onDismissRequest = { },
            title = { Text(if (passed) "Nivel completado" else "Casi") },
            text = {
                if (passed) {
                    Text("Ganaste $starsLevel estrellas. $unlockMessage")
                } else {
                    Text("Ganaste $starsLevel estrellas. Necesitas $passStars para pasar.")
                }
            },
            confirmButton = {
                if (passed) {
                    Button(onClick = {
                        val nextLevel = EnglishManager.completeLevelAndGetNext(
                            level = levelNumber,
                            starsEarned = starsLevel
                        )
                        showEndDialog = false
                        onFinished(nextLevel)
                    }) {
                        Text("Continuar")
                    }
                } else {
                    Button(onClick = { restartLevel() }) {
                        Text("Reintentar")
                    }
                }
            },
            dismissButton = {
                TextButton(onClick = {
                    showEndDialog = false
                    onBack()
                }) {
                    Text("Salir")
                }
            }
        )
    }
}
