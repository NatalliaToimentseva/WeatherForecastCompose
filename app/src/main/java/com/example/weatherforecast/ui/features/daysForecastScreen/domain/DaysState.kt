package com.example.weatherforecast.ui.features.daysForecastScreen.domain

import com.example.weatherforecasts.ui.models.DaysForecastModel

data class DaysState(
    val daysForecast: List<DaysForecastModel> = arrayListOf()
)