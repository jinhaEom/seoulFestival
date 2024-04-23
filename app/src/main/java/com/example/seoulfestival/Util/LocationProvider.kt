package com.example.seoulfestival.Util

import android.content.Context
import android.content.pm.PackageManager
import androidx.core.app.ActivityCompat
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices

class LocationProvider(private val context: Context, private val listener: LocationListener) {
    private var fusedLocationClient: FusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(context)

    interface LocationListener {
        fun onLocationReceived(latitude: Double, longitude: Double)
        fun onLocationError(error: String)
    }

    fun getLastLocation() {
        if (ActivityCompat.checkSelfPermission(context, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
            ActivityCompat.checkSelfPermission(context, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            listener.onLocationError("권한이 업습니다.")
            return
        }

        fusedLocationClient.lastLocation.addOnSuccessListener { location ->
            if (location != null) {
                listener.onLocationReceived(location.latitude, location.longitude)
            } else {
                listener.onLocationError("에러발생")
            }
        }.addOnFailureListener {
            listener.onLocationError(it.message ?: "에러발생")
        }
    }
}
