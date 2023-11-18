package com.example.myweather.remote.openweathermap

import com.example.myweather.remote.base.NetworkResultCallAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object OpenWeatherApiFactory {
    fun createWeatherApi(): OpenWeatherMapApi {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://api.openweathermap.org")
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(NetworkResultCallAdapterFactory.create())
            .build()
        return retrofit.create(OpenWeatherMapApi::class.java)
    }
}
