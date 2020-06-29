package com.android.worldwideweather.data.repository

import com.android.worldwideweather.data.dto.response.GetIpDataResponse
import com.android.worldwideweather.data.network.ApiConstants
import com.android.worldwideweather.data.network.IpApiService

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

import javax.inject.Inject

class IpApiRepository @Inject constructor(
    private val ipApiService: IpApiService
) {

    fun getIpData(
        onSuccess: (ipData: GetIpDataResponse) -> Unit,
        onError: () -> Unit
    ) {
        val call = ipApiService.getIpData(ApiConstants.ipAccessKey)

        call.enqueue(object : Callback<GetIpDataResponse> {
            override fun onResponse(
                call: Call<GetIpDataResponse>,
                dataResponse: Response<GetIpDataResponse>
            ) {
                dataResponse.body()?.cityName?.let {
                    onSuccess(dataResponse.body()!!)
                } ?: onError()
            }

            override fun onFailure(call: Call<GetIpDataResponse>, t: Throwable) {
                onError()
            }
        })
    }
}