package com.example.myweather.domain.usecases

import com.example.myweather.domain.base.SingleUseCase
import com.example.myweather.domain.models.WeatherCoordinates
import com.example.myweather.domain.models.WeatherModel
import com.example.myweather.domain.repositories.RemoteRepository
import com.example.myweather.domain.repositories.StorageRepository
import com.example.myweather.domain.utils.Resource
import kotlinx.coroutines.Dispatchers
import javax.inject.Inject

class FetchWeatherUseCaseImpl @Inject constructor(
    private val remoteRepository: RemoteRepository,
    private val storageRepository: StorageRepository
) : FetchWeatherUseCase,
    SingleUseCase<WeatherCoordinates, Resource<WeatherModel?>>(Dispatchers.IO) {
    override suspend fun action(input: WeatherCoordinates): Resource<WeatherModel?> {
        return when (
            val res = remoteRepository.fetchCurrentWeather(input.lat, input.lon)
        ) {
            is Resource.Success -> {
                val remoteData = res.successData
                remoteData?.let { weather ->
                    storageRepository.update(weather)
                }
                Resource.Success(remoteData)
            }
            is Resource.Error -> {
                Resource.Error(res.errorMessage)
            }
        }
    }
}
