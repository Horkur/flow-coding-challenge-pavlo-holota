package com.android.worldwideweather.presentation.ui.flow.cityselect

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.text.Editable
import android.text.InputType
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.layout_city_select_fragment.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

import com.android.worldwideweather.R
import com.android.worldwideweather.data.dto.response.CityData
import com.android.worldwideweather.data.repository.Status
import com.android.worldwideweather.presentation.ui.flow.WeatherViewModel

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class CitySelectFragment : Fragment() {

    private lateinit var viewModel: WeatherViewModel
    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var savedCities: ArrayList<CityData>

    companion object {
        const val SHARED_PREF_KEY = "SAVED_CITIES"
        const val MAX_CITIES = 5
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel = WeatherViewModel.getInstanceForActivity(requireActivity())

        return inflater.inflate(R.layout.layout_city_select_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        sharedPreferences = requireActivity().getPreferences(Context.MODE_PRIVATE)
        savedCities = Gson().fromJson(
            sharedPreferences.getString(SHARED_PREF_KEY, ""),
            object : TypeToken<ArrayList<CityData>>() {}.type
        ) ?: ArrayList()

        disableSearchBox()
        setupSearchBox()
        setupCitiesList()
    }

    private fun setupCitiesList() {

        with(citySelectCities) {
            layoutManager = LinearLayoutManager(context)
            adapter = CitySelectAdapter(savedCities, context, {
                savedCities.remove(it)
                (adapter as CitySelectAdapter).notifyDataSetChanged()
                enableSearchBox()
                saveSharedPreferences()
            }, {
                viewModel.fetchWeather(it.city, it.countryCode)
                findNavController().navigateUp()
            })
            (adapter as CitySelectAdapter).notifyDataSetChanged()
        }
    }

    private fun setupSearchBox() {
        with(citySelectSearchBox) {
            isHintEnabled = true
            isErrorEnabled = false
            isCounterEnabled = false

            setOnFocusChangeListener { _, hasFocus ->
                if (!hasFocus) citySelectSuggestions.visibility = View.GONE
            }
        }

        with(citySelectInputEditText) {
            inputType = InputType.TYPE_TEXT_FLAG_CAP_SENTENCES
            imeOptions = EditorInfo.IME_ACTION_DONE
            setImeActionLabel(
                resources.getString(R.string.city_select_searchbox_done),
                EditorInfo.IME_ACTION_DONE
            )

            setOnEditorActionListener { _, actionId, _ ->
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    closeKeyboard(this)
                }
                clearFocus()
                true
            }

            addTextChangedListener(object : TextWatcher {
                var firstRequest = true

                override fun afterTextChanged(s: Editable?) {}

                override fun beforeTextChanged(
                    s: CharSequence?,
                    start: Int,
                    count: Int,
                    after: Int
                ) {
                }

                override fun onTextChanged(
                    text: CharSequence?,
                    start: Int,
                    before: Int,
                    count: Int
                ) {
                    text?.let {
                        viewModel.fetchCities(it.toString())

                        if (firstRequest) {
                            setupCitySuggestions()
                        }
                    }
                }
            })
        }
    }

    private fun setupCitySuggestions() {
        with(citySelectSuggestions) {
            layoutManager = LinearLayoutManager(context)
            adapter = CitySuggestionAdapter(ArrayList(), requireContext()) { cityData ->

                if (!savedCities.contains(cityData)) {
                    savedCities.add(cityData)
                    (citySelectCities.adapter as CitySelectAdapter).notifyDataSetChanged()
                    disableSearchBox()
                    saveSharedPreferences()
                }

                closeKeyboard(this)

                with(this.adapter as CitySuggestionAdapter) {
                    setCitySuggestions(ArrayList())
                    notifyDataSetChanged()
                }
            }
        }

        viewModel.getSuggestedCitiesLiveData()
            .observe(viewLifecycleOwner, Observer { resource ->
                when (resource!!.status) {
                    Status.SUCCESS -> {
                        resource.data?.cityData?.let {
                            with(citySelectSuggestions) {
                                visibility = View.VISIBLE
                                with(adapter as CitySuggestionAdapter) {
                                    setCitySuggestions(it)
                                    notifyDataSetChanged()
                                }
                            }
                        } ?: run {
                            onApiError()
                        }
                    }
                    Status.ERROR -> {
                        onApiError()
                    }
                    Status.LOADING -> {
                    }
                }
            })
    }

    private fun onApiError() {
        Toast.makeText(context, "ERROR", Toast.LENGTH_LONG).show()
        citySelectSuggestions.visibility = View.GONE
    }

    private fun disableSearchBox() {
        if (savedCities.size >= MAX_CITIES) {
            citySelectInputEditText.isEnabled = false
        }
    }

    private fun enableSearchBox() {
        if (savedCities.size < MAX_CITIES) {
            citySelectInputEditText.isEnabled = true
        }
    }

    private fun closeKeyboard(view: View) {
        (requireContext().getSystemService(
            Activity.INPUT_METHOD_SERVICE
        ) as InputMethodManager).hideSoftInputFromWindow(view.windowToken, 0)
    }

    @SuppressLint("ApplySharedPref")
    private fun saveSharedPreferences() {
        GlobalScope.launch {
            with(sharedPreferences.edit()) {
                putString(
                    SHARED_PREF_KEY, Gson().toJson(savedCities)
                )
                commit()
            }
        }
    }
}