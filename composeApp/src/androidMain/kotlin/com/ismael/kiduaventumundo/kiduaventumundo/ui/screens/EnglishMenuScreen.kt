package com.ismael.kiduaventumundo.kiduaventumundo.com.ismael.kiduaventumundo.kiduaventumundo.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import com.ismael.kiduaventumundo.kiduaventumundo.R
import androidx.compose.ui.unit.dp

data class EnglishLevel(
    val level: Int,
    val title: String,
    val description: String,
    val isUnlocked: Boolean,
    val isCompleted: Boolean
)

@Composable
fun EnglishMenuScreen(
    levels: List<EnglishLevel>,
    onBack: () -> Unit,
    onLevelClick: (Int) -> Unit
) {
    // B A C K G R O U N D
    Box(modifier = Modifier
        .fillMaxSize()){
        Image(
            painter = painterResource(id = R.drawable.fondo_aqua),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
        )
    }

    Column(Modifier.fillMaxSize().padding(16.dp)) {
        Text(
            text = "Selecciona un Nivel",
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 24.dp, bottom = 16.dp),
            textAlign = TextAlign.Center, // Centrado total
            style = MaterialTheme.typography.headlineMedium.copy(
                fontWeight = FontWeight.ExtraBold,
                fontFamily = FontFamily.SansSerif // Fuente redondeada
            ),
            color = Color(0xFF006064) // Verde oscuro que combine con el fondo
        )
        //Text("Selecciona un nivel", style = MaterialTheme.typography.bodyMedium)

        Spacer(Modifier.height(16.dp))

        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(10.dp),
            modifier = Modifier.weight(1f)
        ) {
            items(levels) { lvl ->
                LevelCard(
                    lvl = lvl,
                    onClick = { onLevelClick(lvl.level) }
                )
            }
        }

        Box(
            modifier = Modifier.fillMaxWidth(),
            contentAlignment = Alignment.Center // Centra el botón pequeño
        ) {
            OutlinedButton(
                onClick = { onBack() },
                modifier = Modifier
                    .width(150.dp) // Tamaño controlado
                    .padding(bottom = 20.dp),
                shape = RoundedCornerShape(20.dp),
                border = BorderStroke(2.dp, Color(0xFFD32F2F)), // Borde rojo indicando salida
                colors = ButtonDefaults.outlinedButtonColors(
                    contentColor = Color(0xFFD32F2F) // Texto rojo
                )
            ) {
                Text("Volver", fontWeight = FontWeight.Bold)
            }
        }
    }
}

@Composable
private fun LevelCard(
    lvl: EnglishLevel,
    onClick: () -> Unit
) {
    val statusText = when {
        lvl.isCompleted -> "✅ Completado"
        lvl.isUnlocked -> "🧩 Disponible"
        else -> "🔒"
    }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .alpha(if (lvl.isUnlocked) 1f else 0.6f), // Opaco si está bloqueado
        shape = RoundedCornerShape(28.dp), // Más redondeado
        colors = CardDefaults.cardColors(
            containerColor = if (lvl.isUnlocked) Color.White else Color(0xFFF5F5F5)
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = if (lvl.isUnlocked) 6.dp else 0.dp // Solo los disponibles tienen sombra
        )
    ) {
        Column(Modifier.padding(14.dp)) {
            Text("Nivel ${lvl.level}: ${lvl.title}", style = MaterialTheme.typography.titleMedium)
            Spacer(Modifier.height(4.dp))
            Text(lvl.description, style = MaterialTheme.typography.bodyMedium)
            Spacer(Modifier.height(8.dp))
            Text(statusText, style = MaterialTheme.typography.labelLarge)
        }
    }
}