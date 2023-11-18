package com.example.myweather.remote.openweathermap

import com.example.myweather.remote.base.NetworkResultCallAdapterFactory
import com.example.myweather.remote.inteceptors.AuthenticationInterceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object OpenWeatherApiFactory {
    fun createWeatherApi(
        okHttpBuilder: OkHttpClient.Builder,
        authenticationInterceptor: AuthenticationInterceptor
    ): OpenWeatherMapApi {
        val okHttpClient = okHttpBuilder
            .addNetworkInterceptor(authenticationInterceptor)
            .build()
        val retrofit = Retrofit.Builder()
            .baseUrl("https://api.openweathermap.org")
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(NetworkResultCallAdapterFactory.create())
            .client(okHttpClient)
            .build()
        return retrofit.create(OpenWeatherMapApi::class.java)
    }
}
