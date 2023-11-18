package com.example.myweather.domain.usecases

import com.example.myweather.domain.base.SingleUseCase
import com.example.myweather.domain.models.WeatherModel
import com.example.myweather.domain.repositories.StorageRepository
import kotlinx.coroutines.Dispatchers
import javax.inject.Inject

class GetWeatherUseCaseImpl @Inject constructor(
    private val storageRepository: StorageRepository
) : GetWeatherUseCase,
    SingleUseCase<Unit, WeatherModel?>(Dispatchers.IO)
{
    override suspend fun action(input: Unit): WeatherModel? {
        return storageRepository.get()
    }
}
