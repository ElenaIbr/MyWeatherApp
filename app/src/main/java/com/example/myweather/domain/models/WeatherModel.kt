package com.example.myweather.domain.models

data class WeatherModel(
    var lat: Double?,
    var lon: Double?,
    var timezone: String?,
    var current: Current?
) {
    data class Current(
        var temp: Double?,
        var feelsLike: Double?,
        var windSpeed: Double?,
        var weather: List<Weather>?
    ) {
        data class Weather(
            var id: Int?,
            var main: String?,
            var description: String?,
            var icon: String?
        )
    }
}
