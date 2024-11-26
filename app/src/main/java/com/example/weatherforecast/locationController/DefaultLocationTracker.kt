package com.example.weatherforecast.locationController

import android.app.Application
import android.content.Context
import android.content.pm.PackageManager
import android.location.LocationManager
import androidx.core.content.ContextCompat
import com.google.android.gms.location.FusedLocationProviderClient
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.suspendCancellableCoroutine

class DefaultLocationTracker (
    private val fusedLocationProviderClient: FusedLocationProviderClient,
    private val application: Application
) : LocationTracker {

    @OptIn(ExperimentalCoroutinesApi::class)
    override suspend fun getCurrentLocation(): android.location.Location? {
        val hasAccessFineLocationPermission = ContextCompat.checkSelfPermission(
            application,
            android.Manifest.permission.ACCESS_FINE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED

        val hasAccessCoarseLocationPermission = ContextCompat.checkSelfPermission(
            application,
            android.Manifest.permission.ACCESS_COARSE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED

        val locationManager = application.getSystemService(
            Context.LOCATION_SERVICE
        ) as LocationManager

        val isGpsEnabled = locationManager
            .isProviderEnabled(LocationManager.NETWORK_PROVIDER) ||
                locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)

        if (!isGpsEnabled && !(hasAccessCoarseLocationPermission || hasAccessFineLocationPermission)) {
            return null
        }

        return suspendCancellableCoroutine { continuation ->
            fusedLocationProviderClient.lastLocation.apply {
                if (isComplete) {
                    if (isSuccessful) {
                        continuation.resume(result) {} // Resume coroutine with location result
                    } else {
                        continuation.resume(null) {} // Resume coroutine with null location result
                    }
                    return@suspendCancellableCoroutine
                }
                addOnSuccessListener {
                    continuation.resume(it) {}  // Resume coroutine with location result
                }
                addOnFailureListener {
                    continuation.resume(null) {} // Resume coroutine with null location result
                }
                addOnCanceledListener {
                    continuation.cancel() // Cancel the coroutine
                }
            }
        }
    }
}