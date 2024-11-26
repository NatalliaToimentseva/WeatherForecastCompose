package com.example.weatherforecast.repository

import com.example.weatherforecast.LoadDataException
import com.example.weatherforecast.constants.DAYS
import com.example.weatherforecast.constants.DIVIDER
import com.example.weatherforecast.constants.KEY
import com.example.weatherforecast.domain.UpdateWeatherController
import com.example.weatherforecast.network.RetrofitApi
import com.example.weatherforecast.utils.toCurrentDayModel
import com.example.weatherforecast.utils.toListDaysForecastModel
import com.example.weatherforecast.utils.toListHoursForecastModel
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

interface WeatherDataRepository {

    suspend fun loadWeatherDate(location: String)
}

class RetrofitWeatherRepository @Inject constructor(
    private val api: RetrofitApi,
    private val controller: UpdateWeatherController
) : WeatherDataRepository {

    override suspend fun loadWeatherDate(location: String) {
        try {
            val result = api.getWeatherData(KEY, location, DAYS)
            if (result.isSuccessful) {
                result.body()?.let { weatherResponse ->
                    controller.updatedCurrentWeather.tryEmit(weatherResponse.toCurrentDayModel())
                    controller.updatedHoursWeather.tryEmit(weatherResponse.toListHoursForecastModel())
                    controller.updatedDaysWeather.tryEmit(weatherResponse.toListDaysForecastModel())
                }
            } else {
                throw LoadDataException(
                    result.code().toString() + DIVIDER + result.errorBody()?.string()
                )
            }
        } catch (e: IOException) {
            throw LoadDataException(e.message)
        } catch (e: HttpException) {
            throw LoadDataException(e.message)
        }
    }
}