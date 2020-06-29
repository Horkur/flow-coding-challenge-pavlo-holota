package com.android.worldwideweather.dependency.component

import android.content.Context
import com.android.worldwideweather.dependency.module.*
import com.android.worldwideweather.presentation.WeatherApplication
import com.android.worldwideweather.presentation.ui.flow.WeatherViewModel

import dagger.Component
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        IpApiServiceModule::class,
        IpApiRepositoryModule::class,
        WeatherServiceModule::class,
        WeatherRepositoryModule::class,
        CitiesServiceModule::class,
        CitiesRepositoryModule::class,
        ApplicationModule::class
    ]
)
interface ApplicationComponent :
    ViewModelInjector {
    fun inject(weatherApplication: WeatherApplication)
}

@Module
class ApplicationModule(private val application: WeatherApplication) {
    @Singleton
    @Provides
    fun provideContext(): Context {
        return application
    }
}

interface ViewModelInjector {
    fun inject(weatherViewModel: WeatherViewModel)
}