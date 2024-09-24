package com.example.weatherforecast.ui.features.homeScreen.domain

sealed class HomeAction {

    data class GetWeatherData (val city: String) : HomeAction()

    data object ClearError : HomeAction()

    data object ShowDialog : HomeAction()

    data object HideDialog : HomeAction()

    data object GetLocation: HomeAction()

    data class SetCityFromDialog(val text: String): HomeAction()

}