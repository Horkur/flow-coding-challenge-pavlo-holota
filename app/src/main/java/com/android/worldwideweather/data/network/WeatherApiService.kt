package com.android.worldwideweather.data.network

import com.android.worldwideweather.data.dto.response.GetCurrentWeatherResponse
import com.android.worldwideweather.data.dto.response.GetWeatherResponse
import retrofit2.Response

import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherApiService {

    @GET(ApiConstants.pathGetWeather)
    suspend fun getWeather(
        @Query("q") cityAndCountry: String,
        @Query("units") unitType: String,
        @Query("appid") apiKey: String
    ): Response<GetWeatherResponse>

    @GET(ApiConstants.pathGetCurrentWeather)
    suspend fun getCurrentWeather(
        @Query("q") cityAndCountry: String,
        @Query("units") unitType: String,
        @Query("appid") apiKey: String
    ): Response<GetCurrentWeatherResponse>
}