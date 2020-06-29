package com.android.worldwideweather.data.dto.response

import com.google.gson.annotations.SerializedName
import kotlin.collections.ArrayList

data class GetCurrentWeatherResponse(
    @SerializedName("weather")
    val weather: ArrayList<CurrentWeather>,
    @SerializedName("main")
    val temperature: CurrentTemperature,
    @SerializedName("name")
    val city: String
)

data class CurrentTemperature(
    @SerializedName("temp")
    val currentTemperature: Float,
    @SerializedName("temp_min")
    val temperatureMin: Float,
    @SerializedName("temp_max")
    val temperatureMax: Float
)

data class CurrentWeather(
    @SerializedName("main")
    val weatherType: String,
    @SerializedName("icon")
    val weatherIcon: String
)