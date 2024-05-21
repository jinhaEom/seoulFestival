package com.example.seoulfestival.viewmodel
import android.content.Context
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



     fun fetchCulturalEvents() {
        viewModelScope.launch {
            try {
                val service = FestivalApi.instance.create(CulturalEventService::class.java)
                val response = service.getCulturalEvents(
                    apiKey = BuildConfig.SEOUL_FESTIVAL_API_KEY,
                    type = "json",
                    service = "culturalEventInfo",
                    startIndex = 1,
                    endIndex = 100
                )
                if (response.isSuccessful) {
                    val events = response.body()?.culturalEventInfo?.row
                    events?.let {
                        val selectedEvents = if (it.size > 4) it.shuffled().take(4) else it
                        _events.postValue(selectedEvents)
                    }
                } else {
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
                } else {

                }
            } catch (e: Exception) {
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
                } else {
                }
            } catch (e: Exception) {
            }
        }
    }
}
