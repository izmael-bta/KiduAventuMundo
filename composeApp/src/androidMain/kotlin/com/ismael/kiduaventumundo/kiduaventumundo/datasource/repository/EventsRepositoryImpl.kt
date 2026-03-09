package com.ismael.kiduaventumundo.kiduaventumundo.datasource.repository

import com.ismael.kiduaventumundo.kiduaventumundo.data.remote.api.EventsApi
import com.ismael.kiduaventumundo.kiduaventumundo.data.remote.model.ProgressEvent
import com.ismael.kiduaventumundo.kiduaventumundo.domain.repository.EventsRepository

class EventsRepositoryImpl(
    private val eventsApi: EventsApi
) : EventsRepository {
    override suspend fun createEvent(userId: Long, event: ProgressEvent): Boolean {
        return eventsApi.createEvent(userId, event)
    }

    override suspend fun getEvents(userId: Long): List<ProgressEvent> {
        return eventsApi.getEvents(userId)
    }
}
