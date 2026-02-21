package com.ismael.kiduaventumundo.kiduaventumundo.ui.components

import androidx.compose.foundation.border



import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.ismael.kiduaventumundo.kiduaventumundo.ui.model.Avatar

@Composable
fun AvatarSelect(
    avatars: List<Avatar>,
    selectedAvatarId: Int,
    onSelect: (Int) -> Unit
) {
    Column {
        Text("Elige tu avatar", style = MaterialTheme.typography.titleMedium)
        Spacer(Modifier.height(10.dp))

        LazyVerticalGrid(
            columns = GridCells.Fixed(4),
            verticalArrangement = Arrangement.spacedBy(12.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            modifier = Modifier.heightIn(max = 240.dp)
        ) {
            items(avatars) { avatar ->
                val selected = avatar.id == selectedAvatarId

                Box(
                    modifier = Modifier
                        .size(72.dp)
                        .clip(CircleShape)
                        .border(
                            width = if (selected) 3.dp else 1.dp,
                            color = if (selected)
                                MaterialTheme.colorScheme.primary
                            else
                                MaterialTheme.colorScheme.outline,
                            shape = CircleShape
                        )
                        .clickable { onSelect(avatar.id) },
                    contentAlignment = Alignment.Center
                ) {
                    Image(
                        painter = painterResource(id = avatar.imageRes),
                        contentDescription = null,
                        modifier = Modifier.size(56.dp)
                    )
                }
            }
        }
    }
}