package com.android.worldwideweather.data.network

import com.android.worldwideweather.data.dto.response.GetCitiesResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface CitiesApiService {

    @GET(ApiConstants.pathFindCities)
    suspend fun getCities(
        @Query("namePrefix") cityPrefix: String,
        @Query("limit") cityLimit: String
    ): Response<GetCitiesResponse>
}