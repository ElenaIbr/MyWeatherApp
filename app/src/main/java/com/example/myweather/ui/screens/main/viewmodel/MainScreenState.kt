package com.example.myweather.ui.screens.main.viewmodel

import com.example.myweather.domain.models.WeatherModel

data class MainScreenState (
    val lat: String? = null,
    val lon: String? = null,
    val currentWeather: WeatherModel? = null,
    val address: String? = null,
    val isLoading: Boolean = true,
    val fetchFailed: Boolean = false
)
