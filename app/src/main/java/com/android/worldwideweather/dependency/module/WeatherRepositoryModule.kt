package com.android.worldwideweather.dependency.module

import com.android.worldwideweather.data.network.WeatherApiService
import com.android.worldwideweather.data.repository.WeatherRepository

import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class WeatherRepositoryModule {

    @Singleton
    @Provides
    fun provideWeatherRepository(service: WeatherApiService) = WeatherRepository(service)
}