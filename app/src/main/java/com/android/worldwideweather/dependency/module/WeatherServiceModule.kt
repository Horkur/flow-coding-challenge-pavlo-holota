package com.android.worldwideweather.dependency.module

import com.android.worldwideweather.data.network.WeatherApiService

import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
class WeatherServiceModule {
    companion object {
        private const val BASE_URL = "https://api.openweathermap.org/data/2.5/"
    }

    @Provides
    @Singleton
    fun provideWeatherApiService(): WeatherApiService {
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))
            .baseUrl(BASE_URL)
            .build()
            .create(WeatherApiService::class.java)
    }
}