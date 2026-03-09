package com.ismael.kiduaventumundo.kiduaventumundo.domain.repository

import com.ismael.kiduaventumundo.kiduaventumundo.data.remote.model.LoginResponse
import com.ismael.kiduaventumundo.kiduaventumundo.data.remote.model.User

interface UserRepository {
    suspend fun register(user: User): User?
    suspend fun getUserById(userId: Long): User?
    suspend fun getUserByNickname(nickname: String): User?
    suspend fun login(nickname: String, passwordHash: String): LoginResponse?
    fun getLastErrorMessage(): String?
    suspend fun nicknameExists(nickname: String): Boolean
    suspend fun updateAvatar(userId: Long, avatarId: String): Boolean
    suspend fun updatePassword(userId: Long, passwordHash: String): Boolean
}
