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
    val place: String?,
    @SerializedName("MAIN_IMG")
    val img : String?,
    @SerializedName("HMPG_ADDR") //홈페이지 주소
    val addr : String?,
    @SerializedName("LOT")
    val lot : Double,
    @SerializedName("LAT")
    val lat : Double,
    @SerializedName("USE_TRGT")
    val age : String,
    @SerializedName("USE_FEE")
    val fee : String,
    @SerializedName("ORG_LINK")
    val orgLink : String,
)
