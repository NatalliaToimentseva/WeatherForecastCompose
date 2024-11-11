package com.example.weatherforecasts.utils

import com.example.weatherforecast.network.responses.WeatherEntityResponse
import com.example.weatherforecasts.ui.models.CurrentDayModel
import com.example.weatherforecasts.ui.models.DaysForecastModel
import com.example.weatherforecasts.ui.models.HoursForecastModel

fun WeatherEntityResponse.toCurrentDayModel(): CurrentDayModel {
    return CurrentDayModel(
        city = this.location.name,
        dateTime = this.current.lastUpdated,
        condition = this.current.condition.text,
        currentTemp = this.current.tempC.toInt().toString(),
        maxTemp = this.forecast.forecastDay[0].day.maxTemp.toInt().toString(),
        mintTemp = this.forecast.forecastDay[0].day.minTemp.toInt().toString(),
        imageUrl = this.current.condition.icon
    )
}

fun WeatherEntityResponse.toListHoursForecastModel(): List<HoursForecastModel> {
    val hoursList = ArrayList<HoursForecastModel>()
    val currentDayHours = this.forecast.forecastDay[0].hour
    for (hour in currentDayHours) {
        hoursList.add(
            HoursForecastModel(
                time = hour.time,
                condition = hour.condition.text,
                currentTemp = hour.temp.toInt().toString(),
                imageUrl = hour.condition.icon
            )
        )
    }
    return hoursList
}

fun WeatherEntityResponse.toListDaysForecastModel(): List<DaysForecastModel> {
    val daysList = ArrayList<DaysForecastModel>()
    val days = this.forecast.forecastDay
    for (day in days) {
        daysList.add(
            DaysForecastModel(
                date = day.date,
                condition = day.day.condition.text,
                maxTemp = day.day.maxTemp.toInt().toString(),
                mintTemp = day.day.minTemp.toInt().toString(),
                imageUrl = day.day.condition.icon,
                rainChance = day.day.rainChance.toString()
            )
        )
    }
    return daysList
}