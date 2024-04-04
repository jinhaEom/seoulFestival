package com.example.seoulfestival.viewModel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.seoulfestival.viewmodel.CulturalEventsViewModel

class CulturalEventsViewModelFactory(private val context: Context) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T
    {
        if (modelClass.isAssignableFrom(CulturalEventsViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return CulturalEventsViewModel(context) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}

