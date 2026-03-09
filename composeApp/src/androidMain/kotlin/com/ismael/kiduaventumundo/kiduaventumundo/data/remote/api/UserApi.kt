package com.ismael.kiduaventumundo.kiduaventumundo.data.remote.api

import android.util.Log
import com.ismael.kiduaventumundo.kiduaventumundo.data.remote.ApiClient
import com.ismael.kiduaventumundo.kiduaventumundo.data.remote.model.LoginRequest
import com.ismael.kiduaventumundo.kiduaventumundo.data.remote.model.LoginResponse
import com.ismael.kiduaventumundo.kiduaventumundo.data.remote.model.UpdateAvatarRequest
import com.ismael.kiduaventumundo.kiduaventumundo.data.remote.model.UpdatePasswordRequest
import com.ismael.kiduaventumundo.kiduaventumundo.data.remote.model.User
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.header
import io.ktor.client.request.post
import io.ktor.client.request.put
import io.ktor.client.request.setBody
import io.ktor.client.statement.bodyAsText
import io.ktor.http.ContentType
import io.ktor.http.HttpHeaders
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.jsonObject
import kotlinx.serialization.json.jsonPrimitive

class UserApi(private val apiClient: ApiClient) {
    private var lastErrorMessage: String? = null

    fun getLastErrorMessage(): String? = lastErrorMessage

    suspend fun createUser(user: User): User? = runCatching {
        lastErrorMessage = null
        val safeRequest = user.copy(passwordHash = "***", securityAnswerHash = "***")
        val url = "${apiClient.baseUrl}/users"
        Log.d("UserApi", "POST $url body=${Json.encodeToString(safeRequest)}")
        val response = apiClient.httpClient.post(url) {
            header(HttpHeaders.ContentType, ContentType.Application.Json.toString())
            setBody(user)
        }
        val statusCode = response.status.value
        if (statusCode == 201 || statusCode == 200) {
            Log.d("UserApi", "POST $url status=$statusCode")
            response.body<User>()
        } else {
            val errorBody = response.bodyAsText()
            lastErrorMessage = extractMessage(errorBody) ?: "Error HTTP $statusCode"
            Log.d("UserApi", "POST $url status=$statusCode errorBody=$errorBody")
            null
        }
    }.getOrNull()

    suspend fun getUserById(userId: Long): User? = runCatching {
        // TODO: confirmar ruta exacta para buscar usuario por id.
        apiClient.httpClient.get("${apiClient.baseUrl}/users/id/$userId").body<User>()
    }.getOrNull()

    suspend fun getUserByNickname(nickname: String): User? = runCatching {
        apiClient.httpClient.get("${apiClient.baseUrl}/users/$nickname").body<User>()
    }.getOrNull()

    suspend fun login(nickname: String, passwordHash: String): LoginResponse? = runCatching {
        lastErrorMessage = null
        val request = LoginRequest(nickname = nickname, passwordHash = passwordHash)
        val safeRequest = request.copy(passwordHash = "***")
        val url = "${apiClient.baseUrl}/login"
        Log.d("UserApi", "POST $url body=${Json.encodeToString(safeRequest)}")
        val response = apiClient.httpClient.post(url) {
            header(HttpHeaders.ContentType, ContentType.Application.Json.toString())
            setBody(request)
        }
        val statusCode = response.status.value
        if (statusCode == 200) {
            Log.d("UserApi", "POST $url status=$statusCode")
            response.body<LoginResponse>()
        } else {
            val errorBody = response.bodyAsText()
            val message = extractMessage(errorBody) ?: "Error HTTP $statusCode"
            lastErrorMessage = message
            Log.d("UserApi", "POST $url status=$statusCode errorBody=$errorBody")
            LoginResponse(success = false, message = message)
        }
    }.getOrNull()

    suspend fun updateUserAvatar(userId: Long, avatarId: String): Boolean = runCatching {
        apiClient.httpClient.put("${apiClient.baseUrl}/users/$userId/avatar") {
            header(HttpHeaders.ContentType, ContentType.Application.Json.toString())
            setBody(UpdateAvatarRequest(avatarId = avatarId))
        }
        true
    }.getOrDefault(false)

    suspend fun updateUserPassword(userId: Long, passwordHash: String): Boolean = runCatching {
        apiClient.httpClient.put("${apiClient.baseUrl}/users/$userId/password") {
            header(HttpHeaders.ContentType, ContentType.Application.Json.toString())
            setBody(UpdatePasswordRequest(passwordHash = passwordHash))
        }
        true
    }.getOrDefault(false)

    private fun extractMessage(errorBody: String): String? {
        return runCatching {
            Json.parseToJsonElement(errorBody)
                .jsonObject["message"]
                ?.jsonPrimitive
                ?.content
        }.getOrNull()
    }
}
