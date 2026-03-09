package com.ismael.kiduaventumundo.kiduaventumundo.data.remote.api

import android.util.Log
import com.ismael.kiduaventumundo.kiduaventumundo.data.remote.ApiClient
import com.ismael.kiduaventumundo.kiduaventumundo.data.remote.model.ProgressEvent
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.header
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.client.statement.bodyAsText
import io.ktor.http.ContentType
import io.ktor.http.HttpHeaders
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

class EventsApi(private val apiClient: ApiClient) {
    suspend fun createEvent(userId: Long, event: ProgressEvent): Boolean = runCatching {
        val url = "${apiClient.baseUrl}/users/$userId/progress/events"
        val requestJson = Json.encodeToString(event)
        Log.d("EventsApi", "POST $url body=$requestJson")
        val response = apiClient.httpClient.post(url) {
            header(HttpHeaders.ContentType, ContentType.Application.Json.toString())
            setBody(event)
        }
        val statusCode = response.status.value
        if (statusCode in 200..299) {
            Log.d("EventsApi", "POST $url status=$statusCode")
            true
        } else {
            val errorBody = response.bodyAsText()
            Log.d("EventsApi", "POST $url status=$statusCode errorBody=$errorBody")
            false
        }
    }.getOrDefault(false)

    suspend fun getEvents(userId: Long): List<ProgressEvent> = runCatching {
        apiClient.httpClient.get("${apiClient.baseUrl}/users/$userId/progress/events")
            .body<List<ProgressEvent>>()
    }.getOrElse { emptyList() }
}
