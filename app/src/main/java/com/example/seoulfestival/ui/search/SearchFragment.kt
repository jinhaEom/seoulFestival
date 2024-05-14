package com.example.seoulfestival.ui.search

import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.seoulfestival.R
import com.example.seoulfestival.base.BaseFragment
import com.example.seoulfestival.databinding.FragmentSearchBinding
import com.example.seoulfestival.response.Event
import com.example.seoulfestival.ui.search.adapter.FestivalInfoAdapter
import com.example.seoulfestival.util.AnimationUtils
import com.naver.maps.geometry.LatLng
import com.naver.maps.map.NaverMap
import com.naver.maps.map.OnMapReadyCallback
import com.naver.maps.map.overlay.Marker
import com.example.seoulfestival.viewModel.CulturalEventsViewModelFactory
import com.example.seoulfestival.viewmodel.CulturalEventsViewModel
import com.naver.maps.map.MapFragment

class SearchFragment : BaseFragment<FragmentSearchBinding>(), OnMapReadyCallback {
    private lateinit var viewModel: CulturalEventsViewModel
    private var naverMap: NaverMap? = null
    private val markers = mutableListOf<Marker>()
    private val eventsByLocation = mutableMapOf<LatLng, MutableList<Event>>()

    override val layoutResourceId: Int = R.layout.fragment_search

    override fun aboutBinding() {
        val vt = viewDataBinding.searchToolbar
        vt.toolbarTitle.text = getString(R.string.mapSearch)
        vt.toolbarTitle.visibility = View.VISIBLE
        vt.toolbarBack.visibility = View.GONE

        viewModel = ViewModelProvider(this, CulturalEventsViewModelFactory(requireContext())).get(
            CulturalEventsViewModel::class.java
        )
        viewModel.fetchMapsMarkersCulturalEvents()

        val mapFragment = childFragmentManager.findFragmentById(R.id.mapView) as MapFragment?
            ?: MapFragment.newInstance().also {
                childFragmentManager.beginTransaction().add(R.id.mapView, it).commit()
            }
        mapFragment.getMapAsync(this)
    }

    override fun observeData() {
        viewModel.events.observe(viewLifecycleOwner, Observer { events ->
            events?.let {
                groupEventsByLocation(it)
                displayMarkers()
            }
        })
    }

    private fun groupEventsByLocation(events: List<Event>) {
        eventsByLocation.clear()
        events.forEach { event ->
            val latLng = LatLng(event.lot, event.lat)
            if (eventsByLocation[latLng] == null) {
                eventsByLocation[latLng] = mutableListOf(event)
            } else {
                eventsByLocation[latLng]?.add(event)
            }
        }
    }

    private fun displayMarkers() {
        clearMarkers()
        eventsByLocation.forEach { (location, eventList) ->
            val marker = Marker()
            marker.position = location
            marker.map = naverMap
            marker.captionText = if (eventList.size > 1) "${eventList.first().title} 외" else eventList.first().title ?: "No title"
            markers.add(marker)

            viewDataBinding.apply {
                marker.setOnClickListener {
                    val events = eventsByLocation[LatLng(marker.position.latitude, marker.position.longitude)]
                    events?.let {
                        val adapter = FestivalInfoAdapter(requireContext(), it) {
                            AnimationUtils.fadeOut(viewPager, 300)
                        }
                        viewPager.adapter = adapter
                        AnimationUtils.fadeIn(viewPager, 300)
                    }
                    true
                }
            }
        }
    }

    private fun clearMarkers() {
        markers.forEach { it.map = null }
        markers.clear()
    }

    override fun onMapReady(naverMap: NaverMap) {
        this.naverMap = naverMap
        observeData()
    }
}
