package com.android.worldwideweather.presentation.ui.flow.home

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.layout_home_weather_fragment.*
import java.lang.Exception

import com.android.worldwideweather.R
import com.android.worldwideweather.data.dto.WeatherDailyForecast
import com.android.worldwideweather.data.dto.WeatherData
import com.android.worldwideweather.data.dto.response.GetCurrentWeatherResponse
import com.android.worldwideweather.data.dto.response.GetWeatherResponse
import com.android.worldwideweather.data.repository.Resource
import com.android.worldwideweather.data.repository.Status
import com.android.worldwideweather.presentation.ui.flow.WeatherViewModel
import com.android.worldwideweather.utils.WeatherUtils

class HomeWeatherFragment : Fragment() {

    private lateinit var viewModel: WeatherViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel = WeatherViewModel.getInstanceForActivity(requireActivity())

        return inflater.inflate(R.layout.layout_home_weather_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        viewModel.getWeatherLiveData()
            .observe(viewLifecycleOwner, Observer { resource ->
                when (resource!!.status) {
                    Status.SUCCESS -> {
                        resource.data?.let {
                            buildRecycler(it)
                        } ?: onApiError(resource)
                    }
                    Status.ERROR -> {
                        onApiError(resource)
                    }
                    Status.LOADING -> {
                        onLoading()
                    }
                }
            })

        viewModel.getCurrenteatherLiveData()
            .observe(viewLifecycleOwner, Observer { resource ->
                when (resource!!.status) {
                    Status.SUCCESS -> {
                        resource.data?.let {
                            buildTitle(it)
                        } ?: onApiError(resource)
                    }
                    Status.ERROR -> {
                        onApiError(resource)
                    }
                    Status.LOADING -> {
                        onLoading()
                    }
                }
            })
    }

    @SuppressLint("DefaultLocale")
    private fun buildRecycler(weatherResponse: GetWeatherResponse) {
        with(homeWeatherDays) {
            val forecastList = ArrayList<WeatherDailyForecast>()

            weatherResponse.forecastsList.groupBy { WeatherUtils.getDateFromUnix(it.date) }
                .forEach { forecastListMap ->
                    forecastList.add(
                        WeatherDailyForecast(
                            day = WeatherUtils.getDayFromUnix(forecastListMap.value[0].date),
                            date = WeatherUtils.getDateFromUnix(forecastListMap.value[0].date),
                            icon = try {
                                WeatherData.valueOf(forecastListMap.value[0].weather[0].weatherType.toUpperCase())
                                    .weatherIcon()
                            } catch (exception: Exception) {
                                WeatherData.UNKNOWN.weatherIcon()
                            },
                            maxTemperature = forecastListMap.value.map { it.temperature.currentTemperature }
                                .max() ?: 0F,
                            minTemperature = forecastListMap.value.map { it.temperature.currentTemperature }
                                .min() ?: 0F
                        )
                    )
                }

            layoutManager = LinearLayoutManager(context)
            adapter = HomeWeatherAdapter(forecastList, context)
            (adapter as HomeWeatherAdapter).notifyDataSetChanged()
        }
    }

    @SuppressLint("DefaultLocale")
    private fun buildTitle(weatherResponse: GetCurrentWeatherResponse) {
        homeWeatherCurrentTemperature.text =
            weatherResponse.temperature.currentTemperature.toString()

        with(weatherResponse.weather[0].weatherType) {
            homeWeatherCurrentWeather.text = this

            view?.background = resources.getDrawable(
                try {
                    WeatherData.valueOf(this.toUpperCase()).weatherBackground()
                } catch (exception: Exception) {
                    WeatherData.UNKNOWN.weatherBackground()
                }, null
            )
        }
    }

    private fun onApiError(resource: Resource<Any>? = null) {
        Toast.makeText(context, "ERROR", Toast.LENGTH_LONG).show()
    }

    private fun onLoading() {

    }
}