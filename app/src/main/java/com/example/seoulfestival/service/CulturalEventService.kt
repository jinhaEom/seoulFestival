package com.example.seoulfestival.service

import com.example.seoulfestival.response.CulturalEventInfoResponse
import retrofit2.Call
import retrofit2.Response
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
    ): Response<CulturalEventInfoResponse>
}

