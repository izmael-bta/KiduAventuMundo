package com.ismael.kiduaventumundo.kiduaventumundo.domain.repository

import com.ismael.kiduaventumundo.kiduaventumundo.data.remote.model.EnglishActivity
import com.ismael.kiduaventumundo.kiduaventumundo.data.remote.model.EnglishLevel

interface EnglishCatalogRepository {
    suspend fun getLevels(): List<EnglishLevel>
    suspend fun getActivities(level: Int): List<EnglishActivity>
}
