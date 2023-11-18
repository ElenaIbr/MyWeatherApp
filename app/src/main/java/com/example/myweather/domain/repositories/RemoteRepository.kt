package com.example.myweather.domain.repositories

import com.example.myweather.domain.models.WeatherModel
import com.example.myweather.domain.utils.Resource

interface RemoteRepository {
    suspend fun fetchCurrentWeather(
        lat: String,
        lon: String
    ): Resource<WeatherModel>
}
