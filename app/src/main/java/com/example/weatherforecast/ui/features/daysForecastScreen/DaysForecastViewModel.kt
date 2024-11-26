package com.example.weatherforecast.ui.features.daysForecastScreen

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weatherforecast.ui.features.daysForecastScreen.domain.DaysState
import com.example.weatherforecast.domain.UpdateWeatherController
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DaysForecastViewModel @Inject constructor(
    private val controller: UpdateWeatherController
) : ViewModel() {

    val state = MutableLiveData(DaysState())

    init {
        viewModelScope.launch {
            controller.listenDaysWeather().collect {
                state.postValue(state.value?.copy(daysForecast = it))
            }
        }
    }
}