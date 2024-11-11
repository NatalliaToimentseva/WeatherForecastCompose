package com.example.weatherforecast.network.responses

import com.google.gson.annotations.SerializedName

data class ForecastdayResponse(
    @SerializedName("date")
    val date: String,
    @SerializedName("day")
    val day: DayResponse,
    @SerializedName("hour")
    val hour: List<HourResponse>
)