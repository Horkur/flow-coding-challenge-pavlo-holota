package com.android.worldwideweather.data.repository

import androidx.lifecycle.LiveData

import com.android.worldwideweather.data.dto.request.GetWeatherRequest
import com.android.worldwideweather.data.dto.response.GetCurrentWeatherResponse
import com.android.worldwideweather.data.dto.response.GetWeatherResponse
import com.android.worldwideweather.data.network.ApiConstants
import com.android.worldwideweather.data.network.WeatherApiService

import retrofit2.Response
import javax.inject.Inject

class WeatherRepository @Inject constructor(
    private val weatherService: WeatherApiService
) {

    fun getWeatherLiveData(getWeatherRequest: GetWeatherRequest):
            LiveData<Resource<GetWeatherResponse>> {
        return object :
            ProcessedNetworkResource<GetWeatherResponse, GetWeatherResponse>() {
            override suspend fun createCall(): Response<GetWeatherResponse> =
                weatherService.getWeather(
                    getWeatherRequest.cityAndCountry,
                    ApiConstants.weatherApiUnitType,
                    ApiConstants.weatherApiKey
                )

            override fun processResponse(response: GetWeatherResponse): GetWeatherResponse? {
                return response
            }
        }.asLiveData()
    }

    fun getCurrentWeatherLiveData(getWeatherRequest: GetWeatherRequest):
            LiveData<Resource<GetCurrentWeatherResponse>> {
        return object :
            ProcessedNetworkResource<GetCurrentWeatherResponse, GetCurrentWeatherResponse>() {
            override suspend fun createCall(): Response<GetCurrentWeatherResponse> =
                weatherService.getCurrentWeather(
                    getWeatherRequest.cityAndCountry,
                    ApiConstants.weatherApiUnitType,
                    ApiConstants.weatherApiKey
                )

            override fun processResponse(response: GetCurrentWeatherResponse): GetCurrentWeatherResponse? {
                return response
            }
        }.asLiveData()
    }
}