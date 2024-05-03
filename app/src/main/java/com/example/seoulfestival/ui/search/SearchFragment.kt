package com.example.seoulfestival.ui.search


import android.content.pm.PackageManager
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
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions


class SearchFragment : BaseFragment<FragmentSearchBinding>(), OnMapReadyCallback, LocationProvider.LocationListener {

    private lateinit var googleMap: GoogleMap
    private lateinit var locationProvider: LocationProvider
    private lateinit var viewModel: CulturalEventsViewModel
    private var isMapReady = false
    private var markerPositions: MutableList<LatLng> = mutableListOf()

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

        val initialPosition = LatLng(37.5665, 126.9780)
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(initialPosition, 10f))

        if (ContextCompat.checkSelfPermission(requireContext(), android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            googleMap.isMyLocationEnabled = true
            locationProvider.startLocationUpdates()
        } else {
            ActivityCompat.requestPermissions(requireActivity(), arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION), 1000)
        }

        viewModel.events.value?.let {
            loadEventsAndDisplayMarkers(it)
        }
        setupMap()
    }

    override fun onLocationError(error: String) {
        Toast.makeText(context, "Location error: $error", Toast.LENGTH_SHORT).show()
    }

    override fun onStart() {
        super.onStart()
        locationProvider.startLocationUpdates()
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
        events.forEach { event ->
            val position = LatLng(event.lot, event.lat)
            event.title?.let { addMarkerForEvent(position, it) }
            markerPositions.add(position)  // 마커 위치를 리스트에 저장
        }
    }


    private fun addMarkerForEvent(position: LatLng, title: String) {
        val markerOptions = MarkerOptions().position(position).title(title)
        googleMap.addMarker(markerOptions)
    }


    private fun setupMap() {
        viewModel.events.observe(viewLifecycleOwner, Observer { events ->
            events?.let {
                loadEventsAndDisplayMarkers(it)
            }
        })

        if (markerPositions.isNotEmpty()) {
            val randomPosition = markerPositions.random()
            googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(randomPosition, 15f))
        }
    }


}
