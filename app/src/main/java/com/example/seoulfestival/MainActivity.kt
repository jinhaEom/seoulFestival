package com.example.seoulfestival

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.seoulfestival.helper.CulturalEventServiceHelper

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val culturalEventServiceHelper = CulturalEventServiceHelper(this)
        culturalEventServiceHelper.fetchCulturalEvents()
    }
}
