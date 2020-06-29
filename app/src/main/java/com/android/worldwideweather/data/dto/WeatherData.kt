package com.android.worldwideweather.data.dto

import com.android.worldwideweather.R

enum class WeatherData {
    RAIN {
        override fun weatherBackground() = R.drawable.background_rain
        override fun weatherIcon() = R.drawable.ic_rain
    },
    CLEAR {
        override fun weatherBackground() = R.drawable.background_clear
        override fun weatherIcon() = R.drawable.ic_clear
    },
    CLOUDS {
        override fun weatherBackground() = R.drawable.background_cloudy
        override fun weatherIcon() = R.drawable.ic_cloudy
    },
    MIST {
        override fun weatherBackground() = R.drawable.background_mist
        override fun weatherIcon() = R.drawable.ic_mist
    },
    SNOW {
        override fun weatherBackground() = R.drawable.background_snow
        override fun weatherIcon() = R.drawable.ic_snow
    },
    UNKNOWN {
        override fun weatherBackground() = R.color.colorPrimaryDark
        override fun weatherIcon() = R.drawable.ic_unknown
    };

    abstract fun weatherBackground(): Int
    abstract fun weatherIcon(): Int
}