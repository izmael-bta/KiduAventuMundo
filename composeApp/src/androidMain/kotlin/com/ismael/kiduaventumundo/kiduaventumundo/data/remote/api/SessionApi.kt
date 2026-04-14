package com.ismael.kiduaventumundo.kiduaventumundo.data.remote.api

import com.ismael.kiduaventumundo.kiduaventumundo.data.remote.ApiClient
import com.ismael.kiduaventumundo.kiduaventumundo.data.remote.model.SessionRow
import io.ktor.client.call.body
import io.ktor.client.request.delete
import io.ktor.client.request.get
import io.ktor.client.request.put
import io.ktor.client.request.setBody
import io.ktor.http.isSuccess

class SessionApi(private val apiClient: ApiClient) {
    suspend fun getSession(): SessionRow? = runCatching {
        apiClient.httpClient.get("${apiClient.baseUrl}/session").body<SessionRow>()
    }.getOrNull()

    suspend fun setSession(userId: Long): Boolean = runCatching {
        val response = apiClient.httpClient.put("${apiClient.baseUrl}/session") {
            setBody(SessionRow(id = 1, userId = userId))
        }
        response.status.isSuccess()
    }.getOrDefault(false)

    suspend fun clearSession(): Boolean = runCatching {
        val response = apiClient.httpClient.delete("${apiClient.baseUrl}/session")
        response.status.isSuccess()
    }.getOrDefault(false)
}
