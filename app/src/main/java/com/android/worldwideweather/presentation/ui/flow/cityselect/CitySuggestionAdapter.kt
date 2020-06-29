package com.android.worldwideweather.presentation.ui.flow.cityselect

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.layout_city_suggestion_item.view.*

import com.android.worldwideweather.R
import com.android.worldwideweather.data.dto.response.CityData

class CitySuggestionAdapter(
    private val citiesList: ArrayList<CityData>,
    private val context: Context,
    private val onClickListener: (CityData) -> Unit
) :
    RecyclerView.Adapter<CitySuggestionAdapter.CitySuggestionViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CitySuggestionViewHolder {
        return CitySuggestionViewHolder(
            LayoutInflater.from(context).inflate(
                R.layout.layout_city_suggestion_item,
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int = citiesList.size

    override fun onBindViewHolder(holder: CitySuggestionViewHolder, position: Int) {
        holder.let {
            it.itemView.setOnClickListener {
                onClickListener(citiesList[position])
            }
            it.title.text = citiesList[position].city
            it.subtitle.text = citiesList[position].country
        }
    }

    fun setCitySuggestions(citySuggestions: ArrayList<CityData>) {
        with(this.citiesList) {
            removeAll(this)
            addAll(citySuggestions)
        }
    }

    class CitySuggestionViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val title: TextView = itemView.citySuggestionItemTitle
        val subtitle: TextView = itemView.citySuggestionItemSubtitle
    }
}