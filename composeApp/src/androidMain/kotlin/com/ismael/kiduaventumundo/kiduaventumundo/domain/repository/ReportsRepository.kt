package com.ismael.kiduaventumundo.kiduaventumundo.domain.repository

import com.ismael.kiduaventumundo.kiduaventumundo.data.remote.model.UserProgressSummary

interface ReportsRepository {
    suspend fun getSummary(userId: Long): UserProgressSummary?
}
