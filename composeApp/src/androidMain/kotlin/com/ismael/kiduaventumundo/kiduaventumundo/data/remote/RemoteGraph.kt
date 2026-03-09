package com.ismael.kiduaventumundo.kiduaventumundo.data.remote

import com.ismael.kiduaventumundo.kiduaventumundo.data.remote.api.EnglishCatalogApi
import com.ismael.kiduaventumundo.kiduaventumundo.data.remote.api.EventsApi
import com.ismael.kiduaventumundo.kiduaventumundo.data.remote.api.ProgressApi
import com.ismael.kiduaventumundo.kiduaventumundo.data.remote.api.ReportsApi
import com.ismael.kiduaventumundo.kiduaventumundo.data.remote.api.SessionApi
import com.ismael.kiduaventumundo.kiduaventumundo.data.remote.api.UserApi
import com.ismael.kiduaventumundo.kiduaventumundo.datasource.repository.EnglishCatalogRepositoryImpl
import com.ismael.kiduaventumundo.kiduaventumundo.datasource.repository.EventsRepositoryImpl
import com.ismael.kiduaventumundo.kiduaventumundo.datasource.repository.ProgressRepositoryImpl
import com.ismael.kiduaventumundo.kiduaventumundo.datasource.repository.ReportsRepositoryImpl
import com.ismael.kiduaventumundo.kiduaventumundo.datasource.repository.SessionRepositoryImpl
import com.ismael.kiduaventumundo.kiduaventumundo.datasource.repository.UserRepositoryImpl
import com.ismael.kiduaventumundo.kiduaventumundo.domain.repository.EnglishCatalogRepository
import com.ismael.kiduaventumundo.kiduaventumundo.domain.repository.EventsRepository
import com.ismael.kiduaventumundo.kiduaventumundo.domain.repository.ProgressRepository
import com.ismael.kiduaventumundo.kiduaventumundo.domain.repository.ReportsRepository
import com.ismael.kiduaventumundo.kiduaventumundo.domain.repository.SessionRepository
import com.ismael.kiduaventumundo.kiduaventumundo.domain.repository.UserRepository

class RemoteGraph(baseUrl: String = ApiConfig.baseUrl) {
    private val apiClient = ApiClient(baseUrl)

    private val userApi = UserApi(apiClient)
    private val sessionApi = SessionApi(apiClient)
    private val englishCatalogApi = EnglishCatalogApi(apiClient)
    private val progressApi = ProgressApi(apiClient)
    private val eventsApi = EventsApi(apiClient)
    private val reportsApi = ReportsApi(apiClient)

    val userRepository: UserRepository = UserRepositoryImpl(userApi)
    val sessionRepository: SessionRepository = SessionRepositoryImpl(sessionApi)
    val englishCatalogRepository: EnglishCatalogRepository = EnglishCatalogRepositoryImpl(englishCatalogApi)
    val progressRepository: ProgressRepository = ProgressRepositoryImpl(progressApi)
    val eventsRepository: EventsRepository = EventsRepositoryImpl(eventsApi)
    val reportsRepository: ReportsRepository = ReportsRepositoryImpl(reportsApi)
}
