package com.example.seoulfestival.ui.search

import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.seoulfestival.R
import com.example.seoulfestival.base.BaseFragment
import com.example.seoulfestival.databinding.FragmentSearchBinding
import com.example.seoulfestival.response.Event
import com.example.seoulfestival.ui.search.adapter.CustomSpinnerAdapter
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
    private val allEvents = mutableListOf<Event>()

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

        val districts = resources.getStringArray(R.array.seoul_districts).toList()
        val spinnerAdapter = CustomSpinnerAdapter(
            requireContext(),
            R.layout.custom_spinner_item,
            R.layout.custom_spinner_drop_down_item,
            districts
        )
        viewDataBinding.guNameSpinner.adapter = spinnerAdapter

        viewDataBinding.guNameSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {
                val selectedGu = parent.getItemAtPosition(position) as String
                when (selectedGu) {
                    "지역을 선택해주세요", "전체" -> filterMarkersByGuName("")
                    else -> filterMarkersByGuName(selectedGu)
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>) {}
        }
    }

    override fun observeData() {
        viewModel.events.observe(viewLifecycleOwner, Observer { events ->
            events?.let {
                allEvents.clear()
                allEvents.addAll(it)
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

    private fun filterMarkersByGuName(guName: String) {
        val filteredEvents = if (guName.isBlank()) {
            allEvents
        } else {
            allEvents.filter { it.guname?.contains(guName, ignoreCase = true) == true }
        }
        groupEventsByLocation(filteredEvents)
        displayMarkers()
    }

    override fun onMapReady(naverMap: NaverMap) {
        this.naverMap = naverMap
        observeData()
    }
}
