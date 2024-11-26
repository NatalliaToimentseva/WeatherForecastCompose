package com.example.weatherforecast.ui.features.daysForecastScreen.domain

import com.example.weatherforecast.models.DaysForecastModel

data class DaysState(
    val daysForecast: List<DaysForecastModel> = arrayListOf()
)