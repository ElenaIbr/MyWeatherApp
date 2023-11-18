package com.example.myweather.domain.usecases

import com.example.myweather.domain.base.FlowUseCase
import com.example.myweather.domain.models.WeatherCoordinates
import com.example.myweather.domain.models.WeatherModel
import com.example.myweather.domain.repositories.StorageRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetWeatherUseCaseImpl @Inject constructor(
    private val storageRepository: StorageRepository
):  GetWeatherUseCase,
    FlowUseCase<Unit, WeatherModel?>(Dispatchers.IO) {
    override fun action(input: Unit): Flow<WeatherModel?> = flow {
        storageRepository.get().collect {
            emit(it)
        }
    }
}
