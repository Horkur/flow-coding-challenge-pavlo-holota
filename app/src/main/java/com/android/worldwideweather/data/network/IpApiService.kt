package com.android.worldwideweather.data.network

import com.android.worldwideweather.data.dto.response.GetIpDataResponse

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface IpApiService {

    @GET(ApiConstants.pathGetIpData)
    fun getIpData(
        @Query("access_key") accessKey: String
    ): Call<GetIpDataResponse>
}