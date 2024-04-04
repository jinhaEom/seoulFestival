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

    private fun fetchCulturalEvents() {
        viewModelScope.launch {
            try {
                val service = RetrofitClient.instance.create(CulturalEventService::class.java)
                // suspend 함수를 호출하여 직접 응답 객체를 받습니다.
                val response = service.getCulturalEvents(
                    apiKey = BuildConfig.SEOUL_FESTIVAL_API_KEY,
                    type = "json",
                    service = "culturalEventInfo",
                    startIndex = 1,
                    endIndex = 5
                )
                _events.postValue(response.culturalEventInfo?.row)
            } catch (e: Exception) {
                // 네트워크 요청 실패, JSON 파싱 오류 등을 포함한 예외 처리
            }
        }
    }

}
