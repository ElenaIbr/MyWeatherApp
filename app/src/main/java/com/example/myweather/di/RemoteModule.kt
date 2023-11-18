package com.example.myweather.di

import com.example.myweather.domain.repositories.RemoteRepository
import com.example.myweather.remote.inteceptors.AuthenticationInterceptor
import com.example.myweather.remote.openweathermap.OpenWeatherApiFactory
import com.example.myweather.remote.openweathermap.OpenWeatherMapApi
import com.example.myweather.remote.openweathermap.OpenWeatherMapper
import com.example.myweather.remote.openweathermap.RemoteRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RemoteModule {
    @Provides
    fun provideOkHttpBuilder(): OkHttpClient.Builder {
        return OkHttpClient.Builder().connectTimeout(40, TimeUnit.SECONDS)
    }

    @Singleton
    @Provides
    fun provideWeatherApi(
        okHttpClient: OkHttpClient.Builder,
        authenticationInterceptor: AuthenticationInterceptor
    ): OpenWeatherMapApi {
        return OpenWeatherApiFactory.createWeatherApi(
            okHttpClient,
            authenticationInterceptor
        )
    }

    @Singleton
    @Provides
    fun provideRemoteRepository(
        weatherApi: OpenWeatherMapApi,
        weatherMapper: OpenWeatherMapper
    ): RemoteRepository {
        return RemoteRepositoryImpl(
            weatherApi = weatherApi,
            openWeatherMapper = weatherMapper
        )
    }
}
