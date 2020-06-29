package com.android.worldwideweather.data.dto

data class WeatherDailyForecast(
    val day: String,
    val date: String,
    val icon: Int,
    val maxTemperature: Float,
    val minTemperature: Float
)