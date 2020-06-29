package com.android.worldwideweather.presentation.ui.flow

import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.*
import com.android.worldwideweather.data.dto.request.GetCitiesRequest

import com.android.worldwideweather.data.dto.request.GetWeatherRequest
import com.android.worldwideweather.data.dto.response.GetCitiesResponse
import com.android.worldwideweather.data.dto.response.GetCurrentWeatherResponse
import com.android.worldwideweather.data.dto.response.GetIpDataResponse
import com.android.worldwideweather.data.dto.response.GetWeatherResponse
import com.android.worldwideweather.data.network.AbsentLiveData
import com.android.worldwideweather.data.repository.CitiesRepository
import com.android.worldwideweather.data.repository.IpApiRepository
import com.android.worldwideweather.data.repository.Resource
import com.android.worldwideweather.data.repository.WeatherRepository
import com.android.worldwideweather.presentation.appComponent

import java.lang.Exception
import javax.inject.Inject

class WeatherViewModel : ViewModel() {

    @Inject
    lateinit var ipApiRepository: IpApiRepository

    @Inject
    lateinit var weatherRepository: WeatherRepository

    @Inject
    lateinit var citiesRepository: CitiesRepository

    private var currentCity: String? = null
    private var currentCountry: String? = null

    private var weatherLiveData: LiveData<Resource<GetWeatherResponse>>
    private var currentWeatherLiveData: LiveData<Resource<GetCurrentWeatherResponse>>
    private var currentCityLiveData = MutableLiveData<GetWeatherRequest>()

    private var suggestedCitiesLiveData: LiveData<Resource<GetCitiesResponse>>
    private var searchCityLiveData = MutableLiveData<GetCitiesRequest>()

    companion object {
        fun getInstanceForActivity(activity: FragmentActivity): WeatherViewModel {
            return ViewModelProvider(activity).get(
                WeatherViewModel::class.java
            )
        }
    }

    init {
        appComponent().inject(this)

        weatherLiveData = Transformations.switchMap(currentCityLiveData) {
            when (it) {
                is GetWeatherRequest -> weatherRepository.getWeatherLiveData(it)
                else -> AbsentLiveData.create()
            }
        }

        currentWeatherLiveData = Transformations.switchMap(currentCityLiveData) {
            when (it) {
                is GetWeatherRequest -> weatherRepository.getCurrentWeatherLiveData(it)
                else -> AbsentLiveData.create()
            }
        }

        currentCityLiveData.value = null

        suggestedCitiesLiveData = Transformations.switchMap(searchCityLiveData) {
            when (it) {
                is GetCitiesRequest -> citiesRepository.getCitiesLiveData(it)
                else -> AbsentLiveData.create()
            }
        }

        searchCityLiveData.value = null
    }

    fun getWeatherLiveData(): LiveData<Resource<GetWeatherResponse>> {
        return weatherLiveData
    }

    fun getCurrenteatherLiveData(): LiveData<Resource<GetCurrentWeatherResponse>> {
        return currentWeatherLiveData
    }

    fun getSuggestedCitiesLiveData(): LiveData<Resource<GetCitiesResponse>> {
        return suggestedCitiesLiveData
    }

    fun fetchDataForIp(
        onSuccess: (ipData: GetIpDataResponse) -> Unit,
        onError: () -> Unit
    ) {
        try {
            ipApiRepository.getIpData({
                onSuccess(it)
            }, onError)

        } catch (exception: Exception) {
            onError()
        }
    }

    fun fetchWeather(cityName: String, countryCode: String) {
        if (currentCity == null || currentCity != cityName || currentCountry != countryCode) {
            currentCity = cityName
            currentCountry = countryCode
            currentCityLiveData.value = GetWeatherRequest("$cityName,$countryCode")
        }
    }

    fun fetchCities(cityText: String) {
        searchCityLiveData.value = GetCitiesRequest(cityText)
    }
}