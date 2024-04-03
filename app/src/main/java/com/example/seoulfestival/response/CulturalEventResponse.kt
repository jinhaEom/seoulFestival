package com.example.seoulfestival.response

import com.google.gson.annotations.SerializedName

data class CulturalEventInfoResponse(
    @SerializedName("culturalEventInfo")
    val culturalEventInfo: CulturalEventResponse?
)


data class CulturalEventResponse(
    @SerializedName("list_total_count")
    val listTotalCount: Int?,
    @SerializedName("RESULT")
    val result: Result?,
    @SerializedName("row")
    val row: List<Event>?
)

data class Result(
    @SerializedName("CODE")
    val code: String?,
    @SerializedName("MESSAGE")
    val message: String?
)

data class Event(
    @SerializedName("CODENAME")
    val codename: String?,
    @SerializedName("GUNAME")
    val guname: String?,
    @SerializedName("TITLE")
    val title: String?,
    @SerializedName("DATE")
    val date: String?,
    @SerializedName("PLACE")
    val place: String?
    // 필드 추가해야함
)