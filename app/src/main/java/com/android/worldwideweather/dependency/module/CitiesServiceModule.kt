package com.android.worldwideweather.dependency.module

import com.android.worldwideweather.data.network.CitiesApiService

import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
class CitiesServiceModule {
    companion object {
        private const val BASE_URL = "http://geodb-free-service.wirefreethought.com/"
    }

    @Provides
    @Singleton
    fun provideCitiesApiService(): CitiesApiService {
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))
            .baseUrl(BASE_URL)
            .build()
            .create(CitiesApiService::class.java)
    }
}