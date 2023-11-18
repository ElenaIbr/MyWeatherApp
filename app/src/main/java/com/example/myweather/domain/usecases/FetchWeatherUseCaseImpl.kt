package com.example.myweather.domain.usecases

import com.example.myweather.domain.base.SingleUseCase
import com.example.myweather.domain.models.WeatherCoordinates
import com.example.myweather.domain.repositories.RemoteRepository
import com.example.myweather.domain.repositories.StorageRepository
import com.example.myweather.domain.utils.Resource
import kotlinx.coroutines.Dispatchers
import javax.inject.Inject

class FetchWeatherUseCaseImpl @Inject constructor(
    private val remoteRepository: RemoteRepository,
    private val storageRepository: StorageRepository
) : FetchWeatherUseCase,
    SingleUseCase<WeatherCoordinates, Resource<Unit>>(Dispatchers.IO) {
    override suspend fun action(input: WeatherCoordinates): Resource<Unit> {
        return when (
            val res = remoteRepository.fetchCurrentWeather(input.lat, input.lon)
        ) {
            is Resource.Success -> {
                res.successData?.let { weather ->
                    storageRepository.update(weather)
                }
                Resource.Success(Unit)
            }
            is Resource.Error -> {
                Resource.Error(res.errorMessage)
            }
        }
    }
}
