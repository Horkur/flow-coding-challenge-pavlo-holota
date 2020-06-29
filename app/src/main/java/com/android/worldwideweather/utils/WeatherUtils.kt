package com.android.worldwideweather.utils

import android.annotation.SuppressLint
import android.text.format.DateFormat
import java.text.SimpleDateFormat
import java.util.*

class WeatherUtils {
    companion object {
        fun getDateFromUnix(unix: Long): String {
            val calendar = Calendar.getInstance(TimeZone.getDefault())

            calendar.timeInMillis = unix * 1000L
            return DateFormat.format("dd/MM", calendar).toString()
        }

        @SuppressLint("SimpleDateFormat")
        fun getDayFromUnix(unix: Long): String {
            val calendar = Calendar.getInstance(TimeZone.getDefault())

            calendar.timeInMillis = unix * 1000L
            return SimpleDateFormat("EEEE").format(calendar.time)
        }
    }
}