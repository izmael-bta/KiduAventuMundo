package com.ismael.kiduaventumundo.kiduaventumundo.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import com.ismael.kiduaventumundo.kiduaventumundo.R

@Composable
fun BackGroundMenu() {
    Box(
        modifier = Modifier
            .fillMaxSize()){
        Image(
            painter = painterResource(id = R.drawable.fondo_arcom),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
        )
    }
}