package com.android.worldwideweather.data.dto.response

import com.google.gson.annotations.SerializedName

data class GetIpDataResponse(
    @SerializedName("city")
    val cityName: String,
    @SerializedName("country_code")
    val countryCode: String,
    @SerializedName("country_name")
    val countryName: String
)