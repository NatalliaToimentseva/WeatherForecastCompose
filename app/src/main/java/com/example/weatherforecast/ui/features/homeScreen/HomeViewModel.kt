package com.example.weatherforecast.ui.features.homeScreen

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weatherforecast.locationService.LocationTracker
import com.example.weatherforecast.ui.features.homeScreen.domain.HomeAction
import com.example.weatherforecast.ui.features.homeScreen.domain.HomeState
import com.example.weatherforecasts.LoadDataException
import com.example.weatherforecasts.domain.UpdateWeatherController
import com.example.weatherforecasts.repository.WeatherDataRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repository: WeatherDataRepository,
    private val controller: UpdateWeatherController,
    private val locationTracker: LocationTracker
) : ViewModel() {

    val state = MutableLiveData(HomeState())

    fun processAction(action: HomeAction) {
        when (action) {
            is HomeAction.GetWeatherData -> getWeatherData(action.city)
            is HomeAction.ClearError -> clearError()
            is HomeAction.HideDialog -> hideDialog()
            is HomeAction.ShowDialog -> showDialog()
            is HomeAction.SetCityFromDialog -> setDialogText(action.text)
            is HomeAction.GetLocation -> getCurrentLocation()
        }
    }

    init {
        viewModelScope.launch {
            controller.listenCurrentWeather().collect {
                state.postValue(state.value?.copy(currentDayWeather = it))
            }
        }
    }

    private fun getCurrentLocation() {
        viewModelScope.launch(Dispatchers.IO) {
            locationTracker.getCurrentLocation().let { location ->
                if (location != null) {
                    getWeatherData("${location.latitude}, ${location.longitude}")
                    state.postValue(
                        state.value?.copy(
                            currentLocation = "${location.latitude}, ${location.longitude}"
                        )
                    )
                } else getWeatherData("Madrid")
            }
        }
    }

    private fun clearError() {
        state.value = state.value?.copy(errorsGettingData = null)
    }

    private fun setError(message: String) {
        state.value = state.value?.copy(errorsGettingData = message)
    }

    private fun showDialog() {
        state.value = state.value?.copy(isShowDialog = true)
    }

    private fun hideDialog() {
        state.value = state.value?.copy(isShowDialog = false, dialogText = "")
    }

    private fun setDialogText(text: String) {
        state.value = state.value?.copy(dialogText = text)
    }

    private fun getWeatherData(city: String) = viewModelScope.launch(Dispatchers.IO) {
        try {
            state.postValue(state.value?.copy(isInProgress = true))
            repository.loadWeatherDate(city)
            delay(1000)
            state.postValue(state.value?.copy(isInProgress = false, isShowDialog = false))
        } catch (e: LoadDataException) {
            state.postValue(state.value?.copy(errorsGettingData = e.message, isInProgress = false))
        }
    }
}