package com.ismael.kiduaventumundo.kiduaventumundo.ui.screens.steps

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.lazy.grid.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp

import com.ismael.kiduaventumundo.kiduaventumundo.ui.model.Avatar

@Composable
fun AvatarStep(
    avatars: List<Avatar>,
    onNext: (Int) -> Unit,
    onBack: () -> Unit
){

    var selectedAvatar by remember { mutableStateOf<Int?>(null) }

    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ){

        Text(
            text = "Elige tu avatar",
            style = MaterialTheme.typography.titleLarge
        )

        Spacer(modifier = Modifier.height(28.dp))

        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            horizontalArrangement = Arrangement.spacedBy(24.dp),
            verticalArrangement = Arrangement.spacedBy(24.dp),
            modifier = Modifier
                .height(200.dp)
        ){

            items(avatars){ avatar ->

                val selected = selectedAvatar == avatar.id

                val scale by animateFloatAsState(
                    targetValue = if(selected) 1.2f else 1f,
                    label = ""
                )

                Card(
                    onClick = {
                        selectedAvatar = avatar.id
                    },
                    shape = CircleShape,
                    border = if(selected)
                        BorderStroke(4.dp, MaterialTheme.colorScheme.primary)
                    else
                        BorderStroke(2.dp, Color.LightGray),
                    modifier = Modifier
                        .size(90.dp)
                        .scale(scale)
                ){

                    Image(
                        painter = painterResource(id = avatar.imageRes),
                        contentDescription = null,
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(12.dp)
                    )

                }
            }
        }

        Spacer(modifier = Modifier.height(36.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ){

            OutlinedButton(
                onClick = onBack
            ){
                Text("Volver")
            }

            Button(
                onClick = {
                    selectedAvatar?.let { onNext(it) }
                },
                enabled = selectedAvatar != null
            ){
                Text("Continuar")
            }

        }

    }
}