package com.example.seoulfestival.viewmodel

import android.content.Context
import android.content.SharedPreferences
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.seoulfestival.BuildConfig
import com.example.seoulfestival.model.Event
import com.example.seoulfestival.service.FestivalApi
import com.example.seoulfestival.service.CulturalEventService
import kotlinx.coroutines.launch

class CulturalEventsViewModel(private val context: Context) : ViewModel() {
    private val _events = MutableLiveData<List<Event>?>()
    val events: LiveData<List<Event>?> = _events

    private val preferences: SharedPreferences = context.getSharedPreferences("UserPreferences", Context.MODE_PRIVATE)

    fun fetchCulturalEvents() {
        viewModelScope.launch {
            try {
                val service = FestivalApi.instance.create(CulturalEventService::class.java)
                val response = service.getCulturalEvents(
                    apiKey = BuildConfig.SEOUL_FESTIVAL_API_KEY,
                    type = "json",
                    service = "culturalEventInfo",
                    startIndex = 1,
                    endIndex = 300
                )
                if (response.isSuccessful) {
                    val events = response.body()?.culturalEventInfo?.row
                    events?.let {
                        val selectedCategories = mutableSetOf<String>()
                        if (preferences.getBoolean("opera", false)) selectedCategories.add("뮤지컬/오페라")
                        if (preferences.getBoolean("dance", false)) selectedCategories.add("무용")
                        if (preferences.getBoolean("classic", false)) selectedCategories.add("클래식")
                        if (preferences.getBoolean("gukak", false)) selectedCategories.add("국악")
                        if (preferences.getBoolean("drama", false)) selectedCategories.add("연극")

                        val filteredEvents = it.filter { event ->
                            selectedCategories.contains(event.codename)
                        }

                        val selectedEvents = if (filteredEvents.size > 4) filteredEvents.shuffled().take(4) else filteredEvents
                        _events.postValue(selectedEvents)
                    }
                }
            } catch (e: Exception) {
            }
        }
    }
    fun fetchMapsMarkersCulturalEvents() {
        viewModelScope.launch {
            try {
                val service = FestivalApi.instance.create(CulturalEventService::class.java)
                val response = service.getCulturalEvents(
                    apiKey = BuildConfig.SEOUL_FESTIVAL_API_KEY,
                    type = "json",
                    service = "culturalEventInfo",
                    startIndex = 1,
                    endIndex = 30
                )
                if (response.isSuccessful) {
                    val events = response.body()?.culturalEventInfo?.row
                    events?.let {
                        val selectedEvents = if (it.size > 30) it.shuffled().take(30) else it
                        _events.postValue(selectedEvents)
                    }
                }
            } catch (e: Exception) {
                // Handle the exception appropriately
            }
        }
    }

    fun fetchAllCulturalEvents() {
        viewModelScope.launch {
            try {
                val service = FestivalApi.instance.create(CulturalEventService::class.java)
                val response = service.getCulturalEvents(
                    apiKey = BuildConfig.SEOUL_FESTIVAL_API_KEY,
                    type = "json",
                    service = "culturalEventInfo",
                    startIndex = 1,
                    endIndex = 300
                )
                if (response.isSuccessful) {
                    val events = response.body()?.culturalEventInfo?.row
                    events?.let {
                        _events.postValue(it)
                    }
                }
            } catch (e: Exception) {
                // Handle the exception appropriately
            }
        }
    }
}
