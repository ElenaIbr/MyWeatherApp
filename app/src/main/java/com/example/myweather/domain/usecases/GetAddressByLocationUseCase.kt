package com.example.myweather.domain.usecases

import com.example.myweather.domain.base.SingleUseCaseInterface
import com.example.myweather.domain.models.WeatherCoordinates

interface GetAddressByLocationUseCase : SingleUseCaseInterface<WeatherCoordinates, String?>
