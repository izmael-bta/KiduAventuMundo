package com.ismael.kiduaventumundo.kiduaventumundo

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import kiduaventumundo.composeapp.generated.resources.Res
import kiduaventumundo.composeapp.generated.resources.fondo_inicio
import kiduaventumundo.composeapp.generated.resources.logo1
import org.jetbrains.compose.resources.painterResource

@Composable
fun App(){
    MaterialTheme{
        //Para llamar el fondo
        Image(
            painter = painterResource(Res.drawable.fondo_inicio),
            contentDescription = null,
            contentScale = ContentScale.Crop, //Hace que llene toda la pantalla
            modifier = Modifier.fillMaxSize()
        )

        //Logo sobre el fondo
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ){
            Image(
                painter = painterResource(Res.drawable.logo1),
                contentDescription = "Logo Kidu",
                modifier = Modifier.size(200.dp)
            )
        }
    }
}