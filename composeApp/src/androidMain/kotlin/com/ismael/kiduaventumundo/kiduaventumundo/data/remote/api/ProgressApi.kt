package com.ismael.kiduaventumundo.kiduaventumundo.data.remote.api

import android.util.Log
import com.ismael.kiduaventumundo.kiduaventumundo.data.remote.ApiClient
import com.ismael.kiduaventumundo.kiduaventumundo.data.remote.model.UserActivityProgress
import com.ismael.kiduaventumundo.kiduaventumundo.data.remote.model.UserLevelProgress
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.header
import io.ktor.client.request.put
import io.ktor.client.request.setBody
import io.ktor.client.statement.bodyAsText
import io.ktor.http.ContentType
import io.ktor.http.HttpHeaders
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

class ProgressApi(private val apiClient: ApiClient) {
    suspend fun getUserLevelProgress(userId: Long): List<UserLevelProgress> = runCatching {
        apiClient.httpClient.get("${apiClient.baseUrl}/users/$userId/progress/levels")
            .body<List<UserLevelProgress>>()
    }.getOrElse { emptyList() }

    suspend fun getUserLevelProgress(userId: Long, level: Int): UserLevelProgress? = runCatching {
        apiClient.httpClient.get("${apiClient.baseUrl}/users/$userId/progress/levels/$level")
            .body<UserLevelProgress>()
    }.getOrNull()

    suspend fun upsertUserLevelProgress(userId: Long, level: Int, progress: UserLevelProgress): Boolean {
        return runCatching {
            val url = "${apiClient.baseUrl}/users/$userId/progress/levels/$level"
            val requestJson = Json.encodeToString(progress)
            Log.d("ProgressApi", "PUT $url body=$requestJson")
            val response = apiClient.httpClient.put(url) {
                header(HttpHeaders.ContentType, ContentType.Application.Json.toString())
                setBody(progress)
            }
            val statusCode = response.status.value
            if (statusCode in 200..299) {
                Log.d("ProgressApi", "PUT $url status=$statusCode")
                true
            } else {
                val errorBody = response.bodyAsText()
                Log.d("ProgressApi", "PUT $url status=$statusCode errorBody=$errorBody")
                false
            }
        }.getOrDefault(false)
    }

    suspend fun getUserActivityProgress(userId: Long, level: Int): List<UserActivityProgress> = runCatching {
        apiClient.httpClient.get("${apiClient.baseUrl}/users/$userId/progress/levels/$level/activities")
            .body<List<UserActivityProgress>>()
    }.getOrElse { emptyList() }

    suspend fun upsertUserActivityProgress(
        userId: Long,
        level: Int,
        activityIndex: Int,
        progress: UserActivityProgress
    ): Boolean {
        return runCatching {
            val url =
                "${apiClient.baseUrl}/users/$userId/progress/levels/$level/activities/$activityIndex"
            val requestJson = Json.encodeToString(progress)
            Log.d("ProgressApi", "PUT $url body=$requestJson")
            val response = apiClient.httpClient.put(url) {
                header(HttpHeaders.ContentType, ContentType.Application.Json.toString())
                setBody(progress)
            }
            val statusCode = response.status.value
            if (statusCode in 200..299) {
                Log.d("ProgressApi", "PUT $url status=$statusCode")
                true
            } else {
                val errorBody = response.bodyAsText()
                Log.d("ProgressApi", "PUT $url status=$statusCode errorBody=$errorBody")
                false
            }
        }.getOrDefault(false)
    }
}
