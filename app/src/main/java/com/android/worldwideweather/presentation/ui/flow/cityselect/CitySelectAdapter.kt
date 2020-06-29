package com.android.worldwideweather.presentation.ui.flow.cityselect

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.layout_city_select_item.view.*

import com.android.worldwideweather.R
import com.android.worldwideweather.data.dto.response.CityData

class CitySelectAdapter(
    private val citiesList: ArrayList<CityData>,
    private val context: Context,
    private val onDeleteItem: (cityData: CityData) -> Unit,
    private val onClickListener: (cityData: CityData) -> Unit
) :
    RecyclerView.Adapter<CitySelectAdapter.CitySelectViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CitySelectViewHolder {
        return CitySelectViewHolder(
            LayoutInflater.from(context).inflate(R.layout.layout_city_select_item, parent, false)
        )
    }

    override fun getItemCount(): Int = citiesList.size

    override fun onBindViewHolder(holder: CitySelectViewHolder, position: Int) {
        with(citiesList[position]) {
            holder.cityName.text = context.resources.getString(
                R.string.city_select_saved_city,
                this.city,
                this.country
            )

            holder.itemView.setOnClickListener {
                onClickListener(this)
            }

            holder.removeIcon.setOnClickListener {
                onDeleteItem(this)
            }
        }

        with(holder.itemView) {
            when (position) {
                0 -> setBackgroundResource(R.drawable.recycler_item_background_rounded_up)
                itemCount - 1 -> setBackgroundResource(R.drawable.recycler_item_background_rounded_down)
                else -> setBackgroundResource(R.drawable.recycler_item_background_not_rounded)
            }
        }
    }

    fun saveSelectedCity(selectedCity: CityData) {
        with(this.citiesList) {
            add(selectedCity)
        }
    }

    class CitySelectViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val cityName: TextView = itemView.citySelectItemCity
        val removeIcon: ImageView = itemView.citySelectItemDelete
    }
}