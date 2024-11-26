package com.example.weatherforecast.domain


import com.example.weatherforecast.models.CurrentDayModel
import com.example.weatherforecast.models.DaysForecastModel
import com.example.weatherforecast.models.HoursForecastModel
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UpdateWeatherController @Inject constructor() {

    val updatedCurrentWeather = MutableSharedFlow<CurrentDayModel>(
        replay = 1,
        extraBufferCapacity = 1,
        onBufferOverflow = BufferOverflow.DROP_OLDEST
    )

    fun listenCurrentWeather(): Flow<CurrentDayModel> = updatedCurrentWeather

    val updatedHoursWeather = MutableSharedFlow<List<HoursForecastModel>>(
        replay = 1,
        extraBufferCapacity = 1,
        onBufferOverflow = BufferOverflow.DROP_OLDEST
    )

    fun listenHoursWeather(): Flow<List<HoursForecastModel>> = updatedHoursWeather

    val updatedDaysWeather = MutableSharedFlow<List<DaysForecastModel>>(
        replay = 1,
        extraBufferCapacity = 1,
        onBufferOverflow = BufferOverflow.DROP_OLDEST
    )

    fun listenDaysWeather(): Flow<List<DaysForecastModel>> = updatedDaysWeather
}