package com.android.worldwideweather.presentation

import android.app.Application

import com.android.worldwideweather.dependency.component.ApplicationComponent
import com.android.worldwideweather.dependency.component.DaggerApplicationComponent
import com.android.worldwideweather.dependency.module.*

class WeatherApplication : Application() {
    companion object {
        var applicationComponent: ApplicationComponent? = null
    }

    override fun onCreate() {
        super.onCreate()
        appComponent().inject(this)
    }
}

private fun buildDagger(): ApplicationComponent {
    return WeatherApplication.applicationComponent ?: run {
        WeatherApplication.applicationComponent = DaggerApplicationComponent
            .builder()
            .ipApiRepositoryModule(IpApiRepositoryModule())
            .ipApiServiceModule(IpApiServiceModule())
            .weatherRepositoryModule(WeatherRepositoryModule())
            .weatherServiceModule(WeatherServiceModule())
            .citiesRepositoryModule(CitiesRepositoryModule())
            .citiesServiceModule(CitiesServiceModule())
            .build()

        return WeatherApplication.applicationComponent as ApplicationComponent
    }
}

fun appComponent(): ApplicationComponent {
    return buildDagger()
}