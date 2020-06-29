package com.android.worldwideweather.presentation.ui.flow.home

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.layout_home_weather_item.view.*

import com.android.worldwideweather.R
import com.android.worldwideweather.data.dto.WeatherDailyForecast

import java.math.RoundingMode

class HomeWeatherAdapter(
    private val forecastsList: List<WeatherDailyForecast>,
    private val context: Context
) :
    RecyclerView.Adapter<HomeWeatherAdapter.HomeWeatherViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): HomeWeatherViewHolder = HomeWeatherViewHolder(
        LayoutInflater.from(context).inflate(R.layout.layout_home_weather_item, parent, false)
    )

    override fun getItemCount(): Int = forecastsList.size

    override fun onBindViewHolder(holder: HomeWeatherViewHolder, position: Int) {
        with(holder) {
            day.text = forecastsList[position].day
            date.text = forecastsList[position].date
            icon.setImageResource(forecastsList[position].icon)
            maxTemperature.text = context.getString(
                R.string.home_weather_item_max_value,
                forecastsList[position].maxTemperature.toBigDecimal()
                    .setScale(2, RoundingMode.UP).toFloat().toString()
            )
            minTemperature.text = context.getString(
                R.string.home_weather_item_min_value,
                forecastsList[position].minTemperature.toBigDecimal()
                    .setScale(2, RoundingMode.DOWN).toFloat().toString()
            )

            with(this.itemView) {
                when (position) {
                    0 -> setBackgroundResource(R.drawable.recycler_item_background_rounded_up)
                    itemCount - 1 -> setBackgroundResource(R.drawable.recycler_item_background_rounded_down)
                    else -> setBackgroundResource(R.drawable.recycler_item_background_not_rounded)
                }
            }
        }
    }

    class HomeWeatherViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val day: TextView = itemView.homeWeatherItemDay
        val date: TextView = itemView.homeWeatherItemDate
        val icon: ImageView = itemView.homeWeatherItemPicture
        val maxTemperature: TextView = itemView.homeWeatherItemMaxValue
        val minTemperature: TextView = itemView.homeWeatherItemMinValue
    }
}