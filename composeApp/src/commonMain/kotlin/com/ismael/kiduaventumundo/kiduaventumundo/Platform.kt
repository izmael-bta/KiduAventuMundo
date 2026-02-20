package com.ismael.kiduaventumundo.kiduaventumundo

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform