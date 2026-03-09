package com.ismael.kiduaventumundo.kiduaventumundo.domain.repository

interface SessionRepository {
    suspend fun getSessionUserId(): Long?
    suspend fun setSessionUserId(userId: Long): Boolean
    suspend fun clearSession(): Boolean
}
