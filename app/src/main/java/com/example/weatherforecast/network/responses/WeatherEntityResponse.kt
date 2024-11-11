package com.example.weatherforecast.network.responses

import com.google.gson.annotations.SerializedName

data class WeatherEntityResponse(
    @SerializedName("current")
    val current: CurrentResponse,
    @SerializedName("forecast")
    val forecast: ForecastResponse,
    @SerializedName("location")
    val location: LocationResponse
)