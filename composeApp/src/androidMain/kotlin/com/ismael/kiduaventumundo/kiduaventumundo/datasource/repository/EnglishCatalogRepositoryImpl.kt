package com.ismael.kiduaventumundo.kiduaventumundo.datasource.repository

import com.ismael.kiduaventumundo.kiduaventumundo.data.remote.api.EnglishCatalogApi
import com.ismael.kiduaventumundo.kiduaventumundo.data.remote.model.EnglishActivity
import com.ismael.kiduaventumundo.kiduaventumundo.data.remote.model.EnglishLevel
import com.ismael.kiduaventumundo.kiduaventumundo.domain.repository.EnglishCatalogRepository

class EnglishCatalogRepositoryImpl(
    private val catalogApi: EnglishCatalogApi
) : EnglishCatalogRepository {
    override suspend fun getLevels(): List<EnglishLevel> = catalogApi.getLevels()

    override suspend fun getActivities(level: Int): List<EnglishActivity> = catalogApi.getActivities(level)
}
