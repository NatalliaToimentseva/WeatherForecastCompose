package com.example.weatherforecasts.ui.hoursForecastScreen

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weatherforecast.ui.features.hoursForecastScreen.domain.HoursState
import com.example.weatherforecasts.domain.UpdateWeatherController
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HoursViewModel @Inject constructor(
    private val controller: UpdateWeatherController
) : ViewModel() {

    val state = MutableLiveData(HoursState())

    init {
        viewModelScope.launch {
            controller.listenHoursWeather().collect {
                state.postValue(state.value?.copy(hoursForecast = it))
            }
        }
    }
}