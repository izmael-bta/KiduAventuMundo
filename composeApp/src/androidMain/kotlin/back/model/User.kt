package com.ismael.kiduaventumundo.kiduaventumundo.back.model

data class User(
    val id: Long = 0L,
    val name: String,
    val age: Int,
    val nickname: String,
    val avatarId: String,
    val stars: Int = 0
)