package com.ismael.kiduaventumundo.kiduaventumundo.datasource.repository

import com.ismael.kiduaventumundo.kiduaventumundo.data.remote.api.SessionApi
import com.ismael.kiduaventumundo.kiduaventumundo.domain.repository.SessionRepository

class SessionRepositoryImpl(
    private val sessionApi: SessionApi
) : SessionRepository {
    override suspend fun getSessionUserId(): Long? = sessionApi.getSession()?.userId

    override suspend fun setSessionUserId(userId: Long): Boolean = sessionApi.setSession(userId)

    override suspend fun clearSession(): Boolean = sessionApi.clearSession()
}
