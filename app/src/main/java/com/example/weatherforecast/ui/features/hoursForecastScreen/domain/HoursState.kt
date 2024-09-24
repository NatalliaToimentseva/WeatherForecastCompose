package com.example.weatherforecast.ui.features.hoursForecastScreen.domain

import com.example.weatherforecasts.ui.models.HoursForecastModel

data class HoursState(
    val hoursForecast: List<HoursForecastModel> = arrayListOf()
)
