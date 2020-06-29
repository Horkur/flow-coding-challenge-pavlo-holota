package com.android.worldwideweather.data.dto.response

import com.google.gson.annotations.SerializedName
import kotlin.collections.ArrayList

data class GetWeatherResponse(
    @SerializedName("list")
    val forecastsList: ArrayList<ForecastListElement>,
    val city: ForecastCity
)

data class ForecastListElement(
    @SerializedName("dt")
    val date: Long,
    @SerializedName("main")
    val temperature: ForecastListElementTemperature,
    @SerializedName("weather")
    val weather: ArrayList<ForecastListElementWeather>
)

data class ForecastListElementTemperature(
    @SerializedName("temp")
    val currentTemperature: Float,
    @SerializedName("temp_min")
    val temperatureMin: Float,
    @SerializedName("temp_max")
    val temperatureMax: Float
)

data class ForecastListElementWeather(
    @SerializedName("main")
    val weatherType: String,
    @SerializedName("icon")
    val weatherIcon: String
)

data class ForecastCity(
    val name: String
)