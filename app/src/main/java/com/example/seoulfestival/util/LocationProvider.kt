package com.example.seoulfestival.util

import android.content.Context
import android.content.pm.PackageManager
import androidx.core.app.ActivityCompat
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationResult

class LocationProvider(private val context: Context, private val listener: LocationListener) {
    private var fusedLocationClient: FusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(context)

    interface LocationListener {
        fun onLocationReceived(latitude: Double, longitude: Double)
        fun onLocationError(error: String)
    }

    fun startLocationUpdates() {
        val locationRequest = LocationRequest.create()?.apply {

            priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        }

        val locationCallback = object : LocationCallback() {
            override fun onLocationResult(locationResult: LocationResult) {
                locationResult.locations.lastOrNull()?.let {
                    listener.onLocationReceived(it.latitude, it.longitude)
                } ?: listener.onLocationError("위치 정보를 받을 수 없습니다.")
            }
        }

        if (ActivityCompat.checkSelfPermission(context, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
            ActivityCompat.checkSelfPermission(context, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            listener.onLocationError("위치 서비스 권한이 필요합니다.")
            return
        }

        locationRequest?.let { fusedLocationClient.requestLocationUpdates(it, locationCallback, null) }
    }
}
