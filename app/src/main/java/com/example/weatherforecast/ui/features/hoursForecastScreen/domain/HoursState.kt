package com.example.weatherforecast.ui.features.hoursForecastScreen.domain

import com.example.weatherforecast.models.HoursForecastModel

data class HoursState(
    val hoursForecast: List<HoursForecastModel> = arrayListOf()
)
