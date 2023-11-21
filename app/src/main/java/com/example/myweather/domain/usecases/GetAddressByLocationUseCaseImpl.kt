package com.example.myweather.domain.usecases

import com.example.myweather.domain.base.SingleUseCase
import com.example.myweather.domain.models.WeatherCoordinates
import com.example.myweather.domain.repositories.StorageRepository
import kotlinx.coroutines.Dispatchers
import javax.inject.Inject

class GetAddressByLocationUseCaseImpl @Inject constructor(
    private val storageRepository: StorageRepository
) : GetAddressByLocationUseCase,
    SingleUseCase<WeatherCoordinates, String?>(Dispatchers.IO) {
    override suspend fun action(input: WeatherCoordinates): String? {
        return storageRepository.getAddress(
            lat = input.lat.toDouble(),
            lon = input.lon.toDouble()
        )
    }
}
