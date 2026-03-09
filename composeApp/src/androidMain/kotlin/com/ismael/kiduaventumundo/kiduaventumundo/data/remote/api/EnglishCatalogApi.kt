package com.ismael.kiduaventumundo.kiduaventumundo.data.remote.api

import com.ismael.kiduaventumundo.kiduaventumundo.data.remote.ApiClient
import com.ismael.kiduaventumundo.kiduaventumundo.data.remote.model.EnglishActivity
import com.ismael.kiduaventumundo.kiduaventumundo.data.remote.model.EnglishLevel
import io.ktor.client.call.body
import io.ktor.client.request.get

class EnglishCatalogApi(private val apiClient: ApiClient) {
    suspend fun getLevels(): List<EnglishLevel> = runCatching {
        apiClient.httpClient.get("${apiClient.baseUrl}/english/levels").body<List<EnglishLevel>>()
    }.getOrElse { emptyList() }

    suspend fun getActivities(level: Int): List<EnglishActivity> = runCatching {
        apiClient.httpClient.get("${apiClient.baseUrl}/english/levels/$level/activities")
            .body<List<EnglishActivity>>()
    }.getOrElse { emptyList() }
}
