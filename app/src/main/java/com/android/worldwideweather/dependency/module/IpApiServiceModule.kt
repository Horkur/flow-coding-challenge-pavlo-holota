package com.android.worldwideweather.dependency.module

import com.android.worldwideweather.data.network.IpApiService
import com.google.gson.GsonBuilder

import dagger.Module
import dagger.Provides

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

import javax.inject.Singleton

@Module
class IpApiServiceModule {
    companion object {
        private const val BASE_URL = "http://api.ipapi.com/api/"
    }

    @Provides
    @Singleton
    fun provideIpApiService(): IpApiService {
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))
            .baseUrl(BASE_URL)
            .build()
            .create(IpApiService::class.java)
    }
}