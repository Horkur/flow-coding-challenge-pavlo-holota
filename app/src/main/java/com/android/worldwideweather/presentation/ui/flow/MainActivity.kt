package com.android.worldwideweather.presentation.ui.flow

import android.os.Bundle
import android.view.View
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import androidx.navigation.findNavController
import kotlinx.android.synthetic.main.layout_weather_toolbar.*

import com.android.worldwideweather.R
import com.android.worldwideweather.data.dto.response.GetWeatherResponse
import com.android.worldwideweather.data.repository.Status

class MainActivity : FragmentActivity() {

    private lateinit var navController: NavController
    private lateinit var viewModel: WeatherViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel = WeatherViewModel.getInstanceForActivity(this)

        setContentView(R.layout.layout_main_activity)

        navController = findNavController(R.id.weatherNavigationNavHost)

        navController.addOnDestinationChangedListener { _, destination, _ ->
            when (destination.label) {
                "HomeWeatherFragment" -> showToolbarWorld()
                "CitySelectFragment" -> showToolbarBack()
            }
        }
    }

    override fun onStart() {
        super.onStart()

        viewModel.getWeatherLiveData().value?.data?.let {
            setupToolbar(it)
        } ?: run {
            viewModel.fetchDataForIp(
                { ipData ->
                    viewModel.fetchWeather(ipData.cityName, ipData.countryCode)

                    navController.navigate(R.id.action_splashFragment_to_homeWeatherFragment)

                    viewModel.getWeatherLiveData().observe(this, Observer { resource ->
                        when (resource!!.status) {
                            Status.SUCCESS -> {
                                resource.data?.let {
                                    setupToolbar(it)
                                } ?: onApiError()
                            }
                            Status.ERROR -> {
                                onApiError()
                            }
                            Status.LOADING -> {
                            }
                        }
                    })
                },
                {
                    onApiError()
                })
        }
    }

    private fun setupToolbar(weatherResponse: GetWeatherResponse) {
        toolbarTitle.text = weatherResponse.city.name

        toolbarWorldIcon.setOnClickListener { navController.navigate(R.id.action_homeWeatherFragment_to_citySelectFragment) }
        toolbarBackIcon.setOnClickListener { navController.navigateUp() }
    }

    private fun showToolbarBack() {
        toolbarBackIcon.visibility = View.VISIBLE
        toolbarWorldIcon.visibility = View.GONE

    }

    private fun showToolbarWorld() {
        toolbarWorldIcon.visibility = View.VISIBLE
        toolbarBackIcon.visibility = View.GONE
    }

    private fun onApiError() {
        toolbarTitle.text = getString(R.string.toolbar_title_error)
    }
}