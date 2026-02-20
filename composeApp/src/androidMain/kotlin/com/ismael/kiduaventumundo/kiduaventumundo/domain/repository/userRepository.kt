package com.ismael.kiduaventumundo.kiduaventumundo.domain.repository

import com.ismael.kiduaventumundo.kiduaventumundo.com.ismael.kiduaventumundo.kiduaventumundo.domain.model.User

interface userRepository {
    fun register(user: User): Long

    fun getUserById(id: Long): User?

    fun getUserByNickname(nickname: String): User?

    fun nicknameExists(nickname: String): Boolean

    fun updateUser(user: User)

    fun setSession(userId: Long)

    fun getSessionUserId(): Long?

    fun clearSession()
}