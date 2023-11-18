package com.example.myweather.remote.openweathermap

import com.example.myweather.domain.models.WeatherModel
import com.example.myweather.domain.repositories.RemoteRepository
import com.example.myweather.domain.utils.Resource
import com.example.myweather.remote.base.ApiNetworkResult
import javax.inject.Inject

class RemoteRepositoryImpl @Inject constructor(
    private val weatherApi: OpenWeatherMapApi,
    private val openWeatherMapper: OpenWeatherMapper
): RemoteRepository {
    override suspend fun fetchCurrentWeather(lat: String, lon: String): Resource<WeatherModel> {
        return weatherApi.getCurrentWeather(lat, lon, "hourly,daily", "cd8eb75dc9af795cbeb5e149d4bc0438").let { response ->
            when (response) {
                is ApiNetworkResult.Success -> {
                    if (response.data != null) {
                        Resource.Success(openWeatherMapper.convertFromRemote(response.data))
                    } else {
                        Resource.Error("Empty body")
                    }
                }
                is ApiNetworkResult.Error -> {
                    Resource.Error(response.message.toString())
                }
                is ApiNetworkResult.Exception -> {
                    Resource.Error(response.e.message.toString())
                }
            }
        }
    }
}
