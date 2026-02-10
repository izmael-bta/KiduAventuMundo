package com.ismael.kiduaventumundo.kiduaventumundo.back.model

data class User(
    val id: Long = 0L,
    val email: String,
    val name: String,
    val age: Int,
    val nickname: String,
    val password: String
)