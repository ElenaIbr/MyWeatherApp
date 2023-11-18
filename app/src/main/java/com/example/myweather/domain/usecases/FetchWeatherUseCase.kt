package com.example.myweather.domain.usecases

import com.example.myweather.domain.base.SingleUseCaseInterface
import com.example.myweather.domain.models.WeatherCoordinates
import com.example.myweather.domain.utils.Resource

interface FetchWeatherUseCase : SingleUseCaseInterface<WeatherCoordinates, Resource<Unit>>
