package com.android.worldwideweather.data.dto.response

import com.google.gson.annotations.SerializedName

data class GetCitiesResponse(
    @SerializedName("data")
    val cityData: ArrayList<CityData>
)

data class CityData(
    val city: String,
    val country: String,
    val countryCode: String
)