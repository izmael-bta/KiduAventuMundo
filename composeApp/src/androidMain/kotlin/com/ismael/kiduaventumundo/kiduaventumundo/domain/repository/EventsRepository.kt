package com.ismael.kiduaventumundo.kiduaventumundo.domain.repository

import com.ismael.kiduaventumundo.kiduaventumundo.data.remote.model.ProgressEvent

interface EventsRepository {
    suspend fun createEvent(userId: Long, event: ProgressEvent): Boolean
    suspend fun getEvents(userId: Long): List<ProgressEvent>
}
