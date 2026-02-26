package com.ismael.kiduaventumundo.kiduaventumundo.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import com.ismael.kiduaventumundo.kiduaventumundo.R
import com.ismael.kiduaventumundo.kiduaventumundo.ui.model.Avatar

class ProfileViewModel : ViewModel() {

    val avatars = listOf(
        Avatar(1, R.drawable.avatar_gato),
        Avatar(2, R.drawable.avatar_perro),
        Avatar(3, R.drawable.avatar_oso),
        Avatar(4, R.drawable.avatar_leon),
    )

    var selectedAvatar by mutableStateOf(avatars.first())
        private set

    fun setAvatar(avatar: Avatar) {
        selectedAvatar = avatar
    }
}