package com.ismael.kiduaventumundo.kiduaventumundo.data.remote

import android.util.Log
import io.ktor.client.HttpClient
import io.ktor.client.engine.okhttp.OkHttp
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json

class ApiClient(
    baseUrl: String = ApiConfig.baseUrl
) {
    val baseUrl: String = baseUrl.trimEnd('/')
    init {
        Log.d("ApiClient", "BASE_URL efectiva en runtime: $baseUrl")
    }

    val httpClient: HttpClient = HttpClient(OkHttp) {
        install(ContentNegotiation) {
            json(
                Json {
                    ignoreUnknownKeys = true
                    isLenient = true
                    prettyPrint = true
                }
            )
        }
    }
}
