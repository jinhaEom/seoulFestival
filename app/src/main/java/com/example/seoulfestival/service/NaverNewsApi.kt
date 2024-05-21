package com.example.seoulfestival.service

import okhttp3.OkHttpClient
import okhttp3.Request
import org.json.JSONObject
import com.example.seoulfestival.BuildConfig

class NaverNewsApi {
    private val clientId = BuildConfig.NAVER_CLIENT_ID
    private val clientSecret = BuildConfig.NAVER_CLIENT_SECRET

    fun searchNews(query: String): JSONObject? {
        val client = OkHttpClient()
        val request = Request.Builder()
            .url("https://openapi.naver.com/v1/search/news.json?query=$query")
            .addHeader("X-Naver-Client-Id", clientId)
            .addHeader("X-Naver-Client-Secret", clientSecret)
            .build()

        client.newCall(request).execute().use { response ->
            return if (response.isSuccessful) {
                response.body()?.string()?.let { JSONObject(it) }
            } else {
                null
            }
        }
    }
}
