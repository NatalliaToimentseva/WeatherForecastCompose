package com.example.weatherforecast.network.responses

import com.google.gson.annotations.SerializedName

data class ConditionResponse(
    @SerializedName("icon")
    val icon: String,
    @SerializedName("text")
    val text: String
)