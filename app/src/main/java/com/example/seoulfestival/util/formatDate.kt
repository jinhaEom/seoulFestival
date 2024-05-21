package com.example.seoulfestival.util

import java.text.SimpleDateFormat
import java.util.Locale

fun formatDate(originalDate: String): String {
    val originalFormat = SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss Z", Locale.ENGLISH)
    val targetFormat = SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH)
    val date = originalFormat.parse(originalDate)
    return targetFormat.format(date)
}
