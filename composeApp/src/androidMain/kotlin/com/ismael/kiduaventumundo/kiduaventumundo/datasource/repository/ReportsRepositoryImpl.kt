package com.ismael.kiduaventumundo.kiduaventumundo.datasource.repository

import com.ismael.kiduaventumundo.kiduaventumundo.data.remote.api.ReportsApi
import com.ismael.kiduaventumundo.kiduaventumundo.data.remote.model.UserProgressSummary
import com.ismael.kiduaventumundo.kiduaventumundo.domain.repository.ReportsRepository

class ReportsRepositoryImpl(
    private val reportsApi: ReportsApi
) : ReportsRepository {
    override suspend fun getSummary(userId: Long): UserProgressSummary? {
        return reportsApi.getSummary(userId)
    }
}
