package com.android.worldwideweather.data.repository

import androidx.lifecycle.LiveData

import com.android.worldwideweather.data.dto.request.GetCitiesRequest
import com.android.worldwideweather.data.dto.response.GetCitiesResponse
import com.android.worldwideweather.data.network.ApiConstants
import com.android.worldwideweather.data.network.CitiesApiService

import retrofit2.Response
import javax.inject.Inject

class CitiesRepository @Inject constructor(
    private val citiesApiService: CitiesApiService
) {

    fun getCitiesLiveData(getCitiesRequest: GetCitiesRequest):
            LiveData<Resource<GetCitiesResponse>> {
        return object :
            ProcessedNetworkResource<GetCitiesResponse, GetCitiesResponse>() {
            override suspend fun createCall(): Response<GetCitiesResponse> =
                citiesApiService.getCities(
                    getCitiesRequest.cityPrefix,
                    ApiConstants.citiesLimit
                )

            override fun processResponse(response: GetCitiesResponse): GetCitiesResponse? {
                return response
            }
        }.asLiveData()
    }
}