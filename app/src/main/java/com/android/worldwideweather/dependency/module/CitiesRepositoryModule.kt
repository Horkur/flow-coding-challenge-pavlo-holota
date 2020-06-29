package com.android.worldwideweather.dependency.module

import com.android.worldwideweather.data.network.CitiesApiService
import com.android.worldwideweather.data.repository.CitiesRepository

import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class CitiesRepositoryModule {

    @Singleton
    @Provides
    fun provideCitiesRepository(service: CitiesApiService) = CitiesRepository(service)
}