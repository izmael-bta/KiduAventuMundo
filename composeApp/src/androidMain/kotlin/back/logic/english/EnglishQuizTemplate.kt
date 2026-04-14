package com.ismael.kiduaventumundo.kiduaventumundo.back.logic.english

import androidx.compose.foundation.BorderStroke
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ismael.kiduaventumundo.kiduaventumundo.back.logic.EnglishManager
import com.ismael.kiduaventumundo.kiduaventumundo.ui.components.CompleteCard

data class QuizOption(
    val id: String,
    val label: String
)

data class QuizQuestion(
    val prompt: String,
    val hint: String,
    val correctId: String,
    val options: List<QuizOption>
)

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
    var showActivityResultDialog by remember { mutableStateOf(false) }
    var activityStarsEarned by remember { mutableIntStateOf(0) }
    var showEndDialog by remember { mutableStateOf(false) }
    var passed by remember { mutableStateOf(false) }

    val current = questions[index]

    fun earnedStarsForThisActivity(mistakesCount: Int): Int = when {
        mistakesCount == 0 -> 3
        mistakesCount == 1 -> 2
        mistakesCount == 2 -> 1
        else -> 0
    }

    fun restartLevel() {
        index = 0
        starsLevel = activityStars.sumOf { it ?: 0 }
        mistakes = 0
        feedback = null
        locked = false
        showActivityResultDialog = false
        activityStarsEarned = 0
        showEndDialog = false
        passed = false
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(30.dp))
        Text(
            text = "Nivel: $levelNumber",
            fontSize = 18.sp,
            color = Color.White,
            fontFamily = FontFamily.SansSerif
        )
        Text(
            text = levelTitle,
            fontSize = 36.sp,
            fontWeight = FontWeight.Bold,
            color = Color.White,
            textAlign = TextAlign.Center
        )

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 16.dp),
            horizontalArrangement = Arrangement.Center
        ) {
            Surface(
                shape = CircleShape,
                color = Color(0xFFFF9100).copy(alpha = 0.4f),
                border = BorderStroke(1.dp, Color.White.copy(alpha = 0.6f))
            ) {
                Text(
                    text = "$starsLevel / $passStars ⭐",
                    modifier = Modifier.padding(horizontal = 20.dp, vertical = 8.dp),
                    style = MaterialTheme.typography.labelLarge.copy(
                        color = Color.White,
                        fontWeight = FontWeight.ExtraBold
                    )
                )
            }

            Spacer(modifier = Modifier.width(12.dp))

            Surface(
                shape = CircleShape,
                color = Color(0xFF039BE5).copy(alpha = 0.4f),
                border = BorderStroke(1.dp, Color.White.copy(alpha = 0.6f))
            ) {
                Text(
                    text = "${index + 1} / $totalActivities",
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
                    text = current.prompt,
                    style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold)
                )
                Spacer(Modifier.height(6.dp))
                Text(current.hint, style = MaterialTheme.typography.bodyMedium)
                Spacer(Modifier.height(6.dp))
                Text("❌: $mistakes", style = MaterialTheme.typography.bodySmall)
            }
        }

        Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
            current.options.chunked(2).forEach { row ->
                Row(
                    Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    row.forEach { option ->
                        Card(
                            modifier = Modifier
                                .weight(1f)
                                .height(84.dp)
                                .clickable(enabled = !locked) {
                                    val isCorrect = option.id == current.correctId
                                    if (isCorrect) {
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
                                        feedback = if (earned > 0) "+$earned estrellas" else "0 estrellas"
                                        activityStarsEarned = earned
                                        showActivityResultDialog = true
                                    } else {
                                        mistakes++
                                        feedback = "Intenta de nuevo"
                                    }
                                }
                        ) {
                            Column(
                                modifier = Modifier.fillMaxSize(),
                                horizontalAlignment = Alignment.CenterHorizontally,
                                verticalArrangement = Arrangement.Center
                            ) {
                                Text(option.label, fontWeight = FontWeight.Bold)
                            }
                        }
                    }
                }
            }
        }

        feedback?.let {
            Text(it, style = MaterialTheme.typography.titleMedium)
        }

        Spacer(Modifier.weight(1f))

        OutlinedButton(
            onClick = onBack,
            modifier = Modifier
                .width(160.dp)
                .height(50.dp),
            shape = RoundedCornerShape(25.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xD2AD2605))
        ) {
            Text("Volver", fontWeight = FontWeight.Bold, color = Color.White)
        }
    }

    if (showActivityResultDialog) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Black.copy(alpha = 0.5f)),
            contentAlignment = Alignment.Center
        ) {
            CompleteCard(
                stars = activityStarsEarned,
                summaryText = when (activityStarsEarned) {
                    3 -> "Ganaste 3 estrellas en tu primer intento"
                    2 -> "Ganaste 2 estrellas en tu segundo intento"
                    1 -> "Ganaste 1 estrella en tu tercer intento"
                    else -> "Desde el cuarto intento ya no ganas estrellas"
                },
                onContinue = {
                    showActivityResultDialog = false
                    val isLast = index == questions.lastIndex
                    if (isLast) {
                        passed = starsLevel >= passStars
                        showEndDialog = true
                    } else {
                        index++
                        mistakes = 0
                        feedback = null
                        locked = false
                        activityStarsEarned = 0
                    }
                }
            )
        }
    }

    if (showEndDialog) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Black.copy(alpha = 0.5f)),
            contentAlignment = Alignment.Center
        ) {
            CompleteCard(
                stars = if (passed) 3 else 1,
                summaryText = "Estrellas acumuladas del nivel: $starsLevel",
                warningText = if (passed) null else "Necesitas mas estrellas para desbloquear el siguiente nivel.",
                onContinue = {
                    if (passed) {
                        val nextLevel = EnglishManager.completeLevelAndGetNext(
                            level = levelNumber,
                            starsEarned = starsLevel
                        )
                        showEndDialog = false
                        onFinished(nextLevel)
                    } else {
                        restartLevel()
                    }
                }
            )
        }
    }
}
