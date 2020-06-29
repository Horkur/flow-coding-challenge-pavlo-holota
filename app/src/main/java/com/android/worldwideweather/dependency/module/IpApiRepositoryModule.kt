package com.android.worldwideweather.dependency.module

import com.android.worldwideweather.data.network.IpApiService
import com.android.worldwideweather.data.repository.IpApiRepository

import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class IpApiRepositoryModule {

    @Singleton
    @Provides
    fun provideIpApiRepository(service: IpApiService) = IpApiRepository(service)
}