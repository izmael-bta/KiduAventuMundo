package com.ismael.kiduaventumundo.kiduaventumundo.data.remote

import com.ismael.kiduaventumundo.kiduaventumundo.BuildConfig

object ApiConfig {
    const val DEFAULT_BACKEND_IP: String = "192.168.0.7"
    const val DEFAULT_BACKEND_BASE_URL: String = "http://192.168.0.7:8080"

    var baseUrl: String = BuildConfig.BACKEND_BASE_URL.ifBlank { DEFAULT_BACKEND_BASE_URL }
}
