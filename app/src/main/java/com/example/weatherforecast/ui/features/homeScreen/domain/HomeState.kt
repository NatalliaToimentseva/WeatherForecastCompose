package com.example.weatherforecast.ui.features.homeScreen.domain

import com.example.weatherforecast.models.CurrentDayModel

data class HomeState(
    val currentDayWeather: CurrentDayModel? = null,
    val errorsGettingData: String? = null,
    val isInProgress: Boolean = false,
    val tabsName: List<String> = listOf("Hours", "3-days"),
    val initialPage: Int = 0,
    val dialogText: String = "",
    val isShowDialog: Boolean = false,
    val currentLocation: String? = null
)