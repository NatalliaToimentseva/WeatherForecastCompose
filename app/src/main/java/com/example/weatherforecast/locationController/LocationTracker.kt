package com.example.weatherforecast.locationController

import android.location.Location

interface LocationTracker {

    suspend fun getCurrentLocation(): Location?
}