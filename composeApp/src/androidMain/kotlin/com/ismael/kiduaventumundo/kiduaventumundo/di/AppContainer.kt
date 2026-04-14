package com.ismael.kiduaventumundo.kiduaventumundo.di

import com.ismael.kiduaventumundo.kiduaventumundo.data.remote.RemoteGraph

class AppContainer {
    private val remoteGraph = RemoteGraph()

    val userRepository = remoteGraph.userRepository
    val sessionRepository = remoteGraph.sessionRepository
}
