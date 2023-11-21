package com.example.myweather.ui.screens.main.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myweather.domain.models.WeatherCoordinates
import com.example.myweather.domain.usecases.FetchWeatherUseCase
import com.example.myweather.domain.usecases.GetAddressByLocationUseCase
import com.example.myweather.domain.usecases.GetWeatherUseCase
import com.example.myweather.domain.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainScreenViewModel @Inject constructor(
    private val fetchWeatherUseCase: FetchWeatherUseCase,
    private val getWeatherUseCase: GetWeatherUseCase,
    private val getAddressByLocationUseCase: GetAddressByLocationUseCase
) : ViewModel() {

    private val _mainScreenState = MutableStateFlow(MainScreenState())
    val mainScreenState: StateFlow<MainScreenState> = _mainScreenState

    init {
        // default location
        fetchWeather(52.364138, 4.891697)
    }

    fun fetchWeather(lat: Double, lon: Double) {
        viewModelScope.launch {
            _mainScreenState.value = _mainScreenState.value.copy(
                isLoading = true
            )
            when(
                val res = fetchWeatherUseCase.execute(
                    WeatherCoordinates(
                        lat = lat,
                        lon = lon
                    )
                )
            ) {
                is Resource.Success -> {
                    _mainScreenState.value = _mainScreenState.value.copy(
                        currentWeather = res.successData,
                        isLoading = false
                    )
                }
                is Resource.Error -> {
                    _mainScreenState.value = _mainScreenState.value.copy(
                        isLoading = false,
                        fetchFailed = true
                    )
                    getLastWeather()
                }
            }

        }
    }
    private fun getLastWeather() {
        viewModelScope.launch {
            getWeatherUseCase.execute(Unit)?.let { lastWeather ->
                _mainScreenState.value = _mainScreenState.value.copy(
                    currentWeather = lastWeather
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

    fun getAddressByLocationUseCase(lat: Double, lon: Double) {
        viewModelScope.launch {
            _mainScreenState.value = _mainScreenState.value.copy(
                address = getAddressByLocationUseCase.execute(
                    WeatherCoordinates(
                        lat = lat,
                        lon = lon
                    )
                )
            )
        }
    }
}
