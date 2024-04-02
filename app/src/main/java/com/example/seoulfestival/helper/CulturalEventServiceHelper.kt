package com.example.seoulfestival.helper

import android.content.Context
import android.util.Log
import com.example.seoulfestival.BuildConfig
import com.example.seoulfestival.response.CulturalEventInfoResponse
import com.example.seoulfestival.response.RetrofitClient
import com.example.seoulfestival.service.CulturalEventService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response



class CulturalEventServiceHelper(private val context: Context) {
    fun fetchCulturalEvents() {
        val service = RetrofitClient.instance.create(CulturalEventService::class.java)
        val call = service.getCulturalEvents(apiKey = BuildConfig.SEOUL_FESTIVAL_API_KEY,"json","culturalEventInfo",1,5)

        call.enqueue(object : Callback<CulturalEventInfoResponse> {
            override fun onResponse(call: Call<CulturalEventInfoResponse>, response: Response<CulturalEventInfoResponse>) {
                if (response.isSuccessful) {
                    Log.d("CulturalEventLog", "Response: ${response.body()}")
                    response.body()?.culturalEventInfo?.row?.forEach { event ->
                        Log.d("CulturalEventLog", "Title: ${event.title}, Place: ${event.place}")
                    }
                } else {
                    Log.e("CulturalEventError", "Error: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<CulturalEventInfoResponse>, t: Throwable) {
                Log.e("CulturalEventError", "Network Error", t)
            }
        })

    }
}
