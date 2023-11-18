package com.example.myweather.remote.openweathermap

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class OpenWeatherResponse(
    @SerializedName("lat")
    @Expose
    var lat: Double? = null,
    @SerializedName("lon")
    @Expose
    var lon: Double? = null,
    @SerializedName("timezone")
    @Expose
    var timezone: String? = null,
    @SerializedName("current")
    @Expose
    var current: Current? = null
) {
    data class Current(
        @SerializedName("temp")
        @Expose
        var temp: Double? = null,
        @SerializedName("feels_like")
        @Expose
        var feelsLike: Double? = null,
        @SerializedName("wind_speed")
        @Expose
        var windSpeed: Double? = null,
        @SerializedName("weather")
        @Expose
        var weather: List<Weather>? = null
    ) {
        data class Weather(
            @SerializedName("id")
            @Expose
            var id: Int? = null,
            @SerializedName("main")
            @Expose
            var main: String? = null,
            @SerializedName("description")
            @Expose
            var description: String? = null,
            @SerializedName("icon")
            @Expose
            var icon: String? = null
        )
    }
}
