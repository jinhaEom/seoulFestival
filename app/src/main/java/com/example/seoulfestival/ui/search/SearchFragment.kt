package com.example.seoulfestival.ui.search

import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.seoulfestival.R
import com.example.seoulfestival.Util.LocationProvider
import com.example.seoulfestival.base.BaseFragment
import com.example.seoulfestival.databinding.FragmentSearchBinding
import com.example.seoulfestival.response.Event
import com.example.seoulfestival.viewModel.CulturalEventsViewModelFactory
import com.example.seoulfestival.viewmodel.CulturalEventsViewModel
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.BitmapDescriptor
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions

class SearchFragment : BaseFragment<FragmentSearchBinding>(), OnMapReadyCallback, LocationProvider.LocationListener {

    private lateinit var googleMap: GoogleMap
    private lateinit var locationProvider: LocationProvider
    private lateinit var viewModel: CulturalEventsViewModel
    private var isMapReady = false


    override val layoutResourceId: Int = R.layout.fragment_search

    override fun aboutBinding() {
        val vt = viewDataBinding.searchToolbar
        vt.toolbarTitle.text = getString(R.string.mapSearch)
        vt.toolbarBack.visibility = View.GONE
        viewModel = ViewModelProvider(this, CulturalEventsViewModelFactory(requireContext())).get(CulturalEventsViewModel::class.java)

        viewModel.fetchMapsMarkersCulturalEvents()

        viewDataBinding.mapView.onCreate(null)
        viewDataBinding.mapView.getMapAsync(this)
        locationProvider = LocationProvider(requireContext(), this)
    }


    override fun observeData() {
        viewModel.events.observe(viewLifecycleOwner, Observer { events ->
            events?.let {
                loadEventsAndDisplayMarkers(it)
            }
        })

    }

    override fun onMapReady(map: GoogleMap) {
        googleMap = map
        isMapReady = true

        // 초기 카메라 위치 설정
        val initialPosition = LatLng(37.5665, 126.9780)
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(initialPosition, 10f))

        if (ContextCompat.checkSelfPermission(requireContext(), android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            googleMap.isMyLocationEnabled = true
            locationProvider.getLastLocation()
        } else {
            ActivityCompat.requestPermissions(requireActivity(), arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION), 1000)
        }

        viewModel.events.value?.let {
            loadEventsAndDisplayMarkers(it)
        }
    }



    override fun onLocationError(error: String) {
        Toast.makeText(context, "Location error: $error", Toast.LENGTH_SHORT).show()
    }

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
        val fixedLocation = LatLng(37.49810, 127.0276)
        googleMap.clear()
        googleMap.addMarker(MarkerOptions().position(fixedLocation).title("My Location"))
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(fixedLocation, 15f))
    }
    private fun loadEventsAndDisplayMarkers(events: List<Event>) {
        if (!::googleMap.isInitialized) {
            return
        }
        googleMap.clear()
        if (events.isNotEmpty()) {
            val firstEvent = events[0]
            val firstPosition = LatLng(firstEvent.lat, firstEvent.lot)
            googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(firstPosition, 15f))
        }
        events.forEach { event ->
            addMarkerForEvent(event)
        }
    }



    private fun addMarkerForEvent(event: Event) {
        val position = LatLng(event.lot, event.lat)
        val icon = resizeMapIcons(R.drawable.marker, 80, 150)
        val markerOptions = MarkerOptions()
            .position(position)
            .title(event.title)
            .icon(icon)

        val marker = googleMap.addMarker(markerOptions)
        marker?.tag = event
    }
    private fun showEventDetails(event: Event) {
    }

    private fun resizeMapIcons(iconId: Int, width: Int, height: Int): BitmapDescriptor {
        val imageBitmap = BitmapFactory.decodeResource(resources, iconId)
        val resizedBitmap = Bitmap.createScaledBitmap(imageBitmap, width, height, false)
        return BitmapDescriptorFactory.fromBitmap(resizedBitmap)
    }
}
