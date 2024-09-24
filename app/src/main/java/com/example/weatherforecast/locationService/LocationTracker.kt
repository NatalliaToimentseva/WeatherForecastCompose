package com.example.weatherforecast.locationService

import android.location.Location

interface LocationTracker {

    suspend fun getCurrentLocation(): Location?
}