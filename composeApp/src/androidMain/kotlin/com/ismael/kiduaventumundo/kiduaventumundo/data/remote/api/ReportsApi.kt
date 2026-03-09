package com.ismael.kiduaventumundo.kiduaventumundo.data.remote.api

import com.ismael.kiduaventumundo.kiduaventumundo.data.remote.ApiClient
import com.ismael.kiduaventumundo.kiduaventumundo.data.remote.model.UserProgressSummary
import io.ktor.client.call.body
import io.ktor.client.request.get

class ReportsApi(private val apiClient: ApiClient) {
    suspend fun getSummary(userId: Long): UserProgressSummary? = runCatching {
        apiClient.httpClient.get("${apiClient.baseUrl}/users/$userId/progress/summary")
            .body<UserProgressSummary>()
    }.getOrNull()
}
