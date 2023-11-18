package com.example.myweather.domain.usecases

import com.example.myweather.domain.base.FlowUseCaseInterface
import com.example.myweather.domain.models.WeatherCoordinates
import com.example.myweather.domain.models.WeatherModel

interface GetWeatherUseCase : FlowUseCaseInterface<Unit, WeatherModel?>
