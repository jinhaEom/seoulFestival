package com.example.seoulfestival.viewmodel
import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.seoulfestival.BuildConfig

import com.example.seoulfestival.response.Event
import com.example.seoulfestival.response.RetrofitClient
import com.example.seoulfestival.service.CulturalEventService
import kotlinx.coroutines.launch

class CulturalEventsViewModel(private val context: Context) : ViewModel() {
    private val _events = MutableLiveData<List<Event>?>()
    val events: MutableLiveData<List<Event>?> = _events

    init {
        fetchCulturalEvents()
    }

    fun fetchCulturalEvents() {
        viewModelScope.launch {
            try {
                val service = RetrofitClient.instance.create(CulturalEventService::class.java)
                val response = service.getCulturalEvents(
                    apiKey = BuildConfig.SEOUL_FESTIVAL_API_KEY,
                    type = "json",
                    service = "culturalEventInfo",
                    startIndex = 1,
                    endIndex = 100
                )
                val events = response.culturalEventInfo?.row
                events?.let {

                    val selectedEvents = if (it.size > 4) it.shuffled().take(4) else it
                    _events.postValue(selectedEvents)
                }
            } catch (e: Exception) {
                // 오류 처리
            }
        }
    }
}
