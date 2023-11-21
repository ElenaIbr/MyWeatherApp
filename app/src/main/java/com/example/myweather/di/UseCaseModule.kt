package com.example.myweather.di

import com.example.myweather.domain.usecases.FetchWeatherUseCase
import com.example.myweather.domain.usecases.FetchWeatherUseCaseImpl
import com.example.myweather.domain.usecases.GetAddressByLocationUseCase
import com.example.myweather.domain.usecases.GetAddressByLocationUseCaseImpl
import com.example.myweather.domain.usecases.GetWeatherUseCase
import com.example.myweather.domain.usecases.GetWeatherUseCaseImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class UseCaseModule {
    @Binds
    abstract fun bindFetchWeatherUseCase(
        fetchWeatherUseCase: FetchWeatherUseCaseImpl
    ): FetchWeatherUseCase

    @Binds
    abstract fun bindGetWeatherUseCase(
        getWeatherUseCase: GetWeatherUseCaseImpl
    ): GetWeatherUseCase

    @Binds
    abstract fun bindGetAddressByLocationUseCase(
        getAddressByLocationUseCase: GetAddressByLocationUseCaseImpl
    ): GetAddressByLocationUseCase
}
