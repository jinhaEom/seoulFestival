package com.example.seoulfestival.ui.search

import android.content.pm.PackageManager
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.seoulfestival.R
import com.example.seoulfestival.Util.LocationProvider
import com.example.seoulfestival.base.BaseFragment
import com.example.seoulfestival.databinding.FragmentSearchBinding
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions

class SearchFragment : BaseFragment<FragmentSearchBinding>(), OnMapReadyCallback, LocationProvider.LocationListener {

    private lateinit var googleMap: GoogleMap
    private lateinit var locationProvider: LocationProvider

    override val layoutResourceId: Int = R.layout.fragment_search

    override fun aboutBinding() {
        val vt = viewDataBinding.searchToolbar
        vt.toolbarTitle.text = getString(R.string.mapSearch)
        vt.toolbarBack.visibility = View.GONE

        viewDataBinding.mapView.onCreate(null)
        viewDataBinding.mapView.getMapAsync(this)
        locationProvider = LocationProvider(requireContext(), this)
    }

    override fun observeData() {
    }

    override fun onMapReady(map: GoogleMap) {
        googleMap = map
        if (ContextCompat.checkSelfPermission(requireContext(), android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            googleMap.isMyLocationEnabled = true
            locationProvider.getLastLocation()
        } else {
            ActivityCompat.requestPermissions(requireActivity(), arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION), 1000)
        }
    }


    override fun onLocationError(error: String) {
        Toast.makeText(context, "Location error: $error", Toast.LENGTH_SHORT).show()
    }

    // 생명주기 메서드들
    override fun onStart() {
        super.onStart()
        viewDataBinding.mapView.onStart()
    }

    override fun onResume() {
        super.onResume()
        viewDataBinding.mapView.onResume()
    }

    override fun onPause() {
        viewDataBinding.mapView.onPause()
        super.onPause()
    }

    override fun onStop() {
        viewDataBinding.mapView.onStop()
        super.onStop()
    }

    override fun onDestroy() {
        viewDataBinding.mapView.onDestroy()
        super.onDestroy()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        viewDataBinding.mapView.onSaveInstanceState(outState)
    }

    override fun onLowMemory() {
        super.onLowMemory()
        viewDataBinding.mapView.onLowMemory()
    }
    override fun onLocationReceived(latitude: Double, longitude: Double) {
        val userLocation = LatLng(latitude, longitude)
        googleMap.clear()
        googleMap.addMarker(MarkerOptions().position(userLocation).title("My Location"))
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(userLocation, 15f))
    }
}
