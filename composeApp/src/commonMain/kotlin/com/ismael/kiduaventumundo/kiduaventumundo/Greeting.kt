package com.ismael.kiduaventumundo.kiduaventumundo

class Greeting {
    private val platform = getPlatform()

    fun greet(): String {
        return "Hello, ${platform.name}!"
    }
}