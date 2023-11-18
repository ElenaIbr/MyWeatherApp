package com.example.myweather.domain.repositories

import com.example.myweather.domain.models.WeatherModel
import kotlinx.coroutines.flow.Flow

interface StorageRepository {
    suspend fun update(weatherModel: WeatherModel)
    suspend fun get(): Flow<WeatherModel?>
}
