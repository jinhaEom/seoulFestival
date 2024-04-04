package com.example.seoulfestival.service

import com.example.seoulfestival.response.CulturalEventInfoResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface CulturalEventService {
    @GET("{apiKey}/{type}/{service}/{startIndex}/{endIndex}")
    suspend fun getCulturalEvents(
        @Path("apiKey") apiKey: String,
        @Path("type") type: String,
        @Path("service") service: String,
        @Path("startIndex") startIndex: Int,
        @Path("endIndex") endIndex: Int
    ): CulturalEventInfoResponse // Call 래퍼 대신 직접 반환 타입 사용
}
