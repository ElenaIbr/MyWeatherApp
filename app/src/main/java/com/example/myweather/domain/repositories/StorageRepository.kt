package com.example.myweather.domain.repositories

import com.example.myweather.domain.models.WeatherModel

interface StorageRepository {
    suspend fun update(weatherModel: WeatherModel)
    suspend fun get(): WeatherModel?
    suspend fun getAddress(lat: Double, lon: Double): String?
}
