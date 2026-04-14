package com.ismael.kiduaventumundo.kiduaventumundo.ui.screens

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape

import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.ProgressBarRangeInfo
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

import kiduaventumundo.composeapp.generated.resources.Res
import kiduaventumundo.composeapp.generated.resources.fondo_arcom
import org.jetbrains.compose.resources.painterResource

/**
 * Pantalla solo de presentacion.
 * Recibe datos ya calculados desde back (sin reglas de negocio internas).
 */
@Composable
fun ProgressScreen(
    totalStars: Int,
    activitiesCompleted: Int,
    currentLevel: Int,
    unlockedLevels: Int,
    onBack: () -> Unit,

) {         // B O R D E S  R E D O N D E A D O S **
    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        Image(
            painter = painterResource(Res.drawable.fondo_arcom),
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )

        /* // Burbujas del menú principal
        // Image(painter = painterResource(Res.drawable.background_burbujas), ...)
        // O gradiente suave
        Box(modifier = Modifier.fillMaxSize().background(
            Brush.verticalGradient(listOf(Color(0xFF81D4FA), Color(0xFFE1F5FE)))
        ))*/

        Column(
            modifier = Modifier.fillMaxSize().padding(16.dp),
            verticalArrangement = Arrangement.Center, // P E N D I E N T E *
            horizontalAlignment = Alignment.CenterHorizontally
            //verticalArrangement = Arrangement.Center // Centra Card y botón//
        ) {
            // Título Superior
            Text(
                text = "¡Tu Progreso!",
                style = MaterialTheme.typography.displayMedium.copy(
                    fontWeight = FontWeight.Black, //ExtraBold,
                    color = Color(0xFFFFFFFF), // Azul KIDU
                    fontSize = 48.sp, // Tamaño grande 42
                    fontFamily = FontFamily.SansSerif // Fuente redondeada
                ),
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(top = 40.dp) // bottom = 24.dp

                /* modifier = Modifier.fillMaxWidth().padding(top = 40.dp),
                textAlign = TextAlign.Center */
            )
            // 2. LA TARJETA
            Card(        // Esquinas Redondeadas - Tipo de letra - Título
                modifier = Modifier
                    .fillMaxWidth() // 0.9f
                    .wrapContentHeight()
                    //.align(Alignment.Center)
                    .padding(16.dp), // 80
                shape = RoundedCornerShape(40.dp),
                colors = CardDefaults.cardColors(containerColor = Color.Transparent),
                elevation = CardDefaults.cardElevation(defaultElevation = 12.dp) // 0
            ) {
                Column(
                    modifier = Modifier
                        .background(Brush.verticalGradient(colors = listOf(Color(0xFAF5CE5A), Color(0xFFE8C275))))
                        /*.padding(horizontal = 26.dp, vertical = 30.dp)*/
                        .padding(32.dp), // 24
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(20.dp) // 16
                ) {
                    // Barras de progreso (Rosa, Naranja, Verde, Azul)
                    AnimatedProgressBar("Estrellas Totales", totalStars.toString(), Color(0xFFF48FB1), totalStars / 100f)
                    AnimatedProgressBar("Actividades", "$activitiesCompleted", Color(0xFFC165FA), activitiesCompleted / 10f)
                    AnimatedProgressBar("Nivel Actual", currentLevel.toString(), Color(0xFF81C784), currentLevel / 8f)
                    AnimatedProgressBar("Desbloqueados", unlockedLevels.toString(), Color(0xFF81D4FA), unlockedLevels / 8f)
                }
            }
            Spacer(modifier = Modifier.height(32.dp))

            // Botón "Volver" centrado, pequeño y rojo
            Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
                OutlinedButton(onClick = onBack,        // Padding pendiente *
                    modifier = Modifier.width(160.dp) .padding(top = 24.dp), // Tamaño rducido
                    //border = BorderStroke(2.dp, Color.Yellow), // Delineado rojo
                    shape = RoundedCornerShape(20.dp), // Bordes redondeados
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xD2AD2605))
                    //colors = ButtonDefaults.outlinedButtonColors(contentColor = Color.Red)
                ){
                    Text("Volver", fontWeight = FontWeight.Bold, color = Color.White)
                   // Text("Volver", fontWeight = FontWeight.Bold)
                }
            }
        }
    }
}
// Componente auxiliar para las barras
@Composable
fun AnimatedProgressBar(
    title: String,
    value: String,
    barColor: Color,
    targetProgress: Float
   // progressPercentage: Float // Entre 0.0f y 1.0f
) {
    // Para Iniciar la animacion
    var startAnim by remember { mutableStateOf(false) }

    // Define animacion de Float
    val progressAnimation by animateFloatAsState(
        targetValue = if (startAnim) targetProgress else 0f,
        animationSpec = tween(durationMillis = 1000) // 1 seg duracion
    )
    // Inicia animacion al cargar pantalla
    LaunchedEffect(Unit){
        startAnim = true
    }

    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        // Título de la barra
        Text(
            text = title,
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF01579B),
            modifier = Modifier.padding(bottom = 4.dp)
            /*style = MaterialTheme.typography.titleLarge.copy(
                fontWeight = FontWeight.Bold,
                color = Color(0xFFFFFFFF), // Azul KIDU
                fontSize = 22.sp
            )*/
        )

        // La Barra "Cápsula"
        Box(
            modifier = Modifier
                .fillMaxWidth(0.9f)
                .height(30.dp)
                .background(
                    Color.White.copy(alpha = 0.6f),
                    RoundedCornerShape(15.dp) // Fondo blanco suave
                ) // FONDO DE CAPSULA
                .padding(3.dp) // Espacio interno
        ) {
                                        // Progreso real ANIMADO
            Box(
                modifier = Modifier
                    .fillMaxWidth(progressAnimation) // Valor animado // progressPorcentage
                    .fillMaxHeight()
                    .background(barColor, RoundedCornerShape(12.dp)) // Color de la barra // 16
            )
                                // V A L O R  N U M E R I C O
            Text(
                text = value,
                modifier = Modifier.align(Alignment.Center),
                color = if (progressAnimation > 0.1f) Color.White else Color.Gray, // White-Gray // Color.White,
                fontWeight = FontWeight.Black,
                fontSize = 16.sp // 18
            )
        }
    }
}

/*  // BARRA 1: Estrellas Acumuladas (Referencia Rosa)
  ProgressBarItem(
      title = "Estrellas Totales",
      value = totalStars.toString(),
      barColor = Color(0xFFF48FB1), // Rosa suave
      progressPercentage = totalStars / 100f // Ejemplo de cálculo de %
  )

  // BARRA 2: Actividades Completadas (Referencia Naranja)
  ProgressBarItem(
      title = "Actividades Hechas",
      value = "$activitiesDone",
      barColor = Color(0xFFFFB74D), // Naranja suave
      progressPercentage = activitiesDone / 10f // Ejemplo: 5 de 10 actividades
  )

  // BARRA 3: Nivel Actual (Referencia Verde)
  ProgressBarItem(
      title = "Tu Nivel Actual",
      value = currentLevel.toString(),
      barColor = Color(0xFF81C784), // Verde suave
      progressPercentage = currentLevel / 8f // Ejemplo: nivel 1 de 8
  )

  // BARRA 4: Niveles Desbloqueados (Usamos un color extra o repetimos)
  ProgressBarItem(
      title = "Niveles Abiertos",
      value = levelsUnlocked.toString(),
      barColor = Color(0xFF81D4FA), // Azul cielo suave
      progressPercentage = levelsUnlocked / 8f // Ejemplo: 1 de 8 niveles
  )
}
}
}
} */
    // P E N D I E N T E * ACTIVIDAD 1 - DESBLOQUEO ACT 2 - MUSICA - PROGRESO SCREEN

   /* Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Top
    ) {

        /*Text(
            text = "Progreso",
            style = MaterialTheme.typography.headlineSmall
        )*/

        Spacer(modifier = Modifier.height(16.dp))

        ProgressItem(
            label = "Estrellas totales acumuladas",
            value = totalStars.toString()
        )
        ProgressItem(
            label = "Actividades completadas",
            value = activitiesCompleted.toString()
        )
        ProgressItem(
            label = "Nivel actual",
            value = currentLevel.toString()
        )
        ProgressItem(
            label = "Niveles desbloqueados",
            value = unlockedLevels.toString()
        )

        Spacer(modifier = Modifier.height(20.dp))

        OutlinedButton(
            onClick = onBack,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Volver")
        }
    }
}*/


/*@Composable
private fun ProgressBarItem(
    value: String,
    title: String,
    barColor: Color,
    progressPercentage: Float
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 10.dp)
    ) {
        Column(modifier = Modifier.padding(14.dp)) {
            Text(
                text = label,
                style = MaterialTheme.typography.bodyLarge
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = value,
                style = MaterialTheme.typography.titleLarge
            )
        }
    }
}*/
