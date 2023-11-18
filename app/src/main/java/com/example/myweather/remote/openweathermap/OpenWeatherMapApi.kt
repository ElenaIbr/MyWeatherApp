package com.example.myweather.remote.openweathermap

import com.example.myweather.remote.base.ApiNetworkResult
import retrofit2.http.GET
import retrofit2.http.Query

interface OpenWeatherMapApi {
    @GET("/data/2.5/onecall?")
    suspend fun getCurrentWeather(
        @Query("lat") lat: String,
        @Query("lon") lon: String,
        @Query("exclude") exclude: String,
        @Query("appid") appid: String
    ): ApiNetworkResult<OpenWeatherResponse>
}
