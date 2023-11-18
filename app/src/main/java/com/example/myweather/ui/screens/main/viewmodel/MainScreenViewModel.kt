package com.example.myweather.ui.screens.main.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myweather.domain.models.WeatherCoordinates
import com.example.myweather.domain.usecases.FetchWeatherUseCase
import com.example.myweather.domain.usecases.GetWeatherUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainScreenViewModel @Inject constructor(
    private val fetchWeatherUseCase: FetchWeatherUseCase,
    private val getWeatherUseCase: GetWeatherUseCase
) : ViewModel() {

    private val _mainScreenState = MutableStateFlow(MainScreenState())
    val mainScreenState: StateFlow<MainScreenState> = _mainScreenState

    init {
        fetchWeather()
        getWeather()
    }

    fun fetchWeather() {
        viewModelScope.launch {
            fetchWeatherUseCase.execute(
                WeatherCoordinates(
                    lat = "52.364138",
                    lon = "4.891697"
                )
            )
        }
    }

    private fun getWeather() {
        viewModelScope.launch {
            getWeatherUseCase.execute(Unit).collect { flow ->
                _mainScreenState.value = _mainScreenState.value.copy(
                    currentWeather = flow
                )
            }
        }
    }

    fun updateLat(value: String) {
        viewModelScope.launch {
            _mainScreenState.value = _mainScreenState.value.copy(
                lat = value
            )
        }
    }

    fun updateLon(value: String) {
        viewModelScope.launch {
            _mainScreenState.value = _mainScreenState.value.copy(
                lon = value
            )
        }
    }
}
