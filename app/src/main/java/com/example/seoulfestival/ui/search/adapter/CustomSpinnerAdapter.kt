package com.example.seoulfestival.ui.search.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import com.example.seoulfestival.R

class CustomSpinnerAdapter(
    context: Context,
    private val resource: Int,
    private val dropDownResource: Int,
    private val items: List<String>
) : ArrayAdapter<String>(context, resource, items) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        return createViewFromResource(convertView, parent, position, resource)
    }

    override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup): View {
        return createViewFromResource(convertView, parent, position, dropDownResource)
    }

    private fun createViewFromResource(convertView: View?, parent: ViewGroup, position: Int, resource: Int): View {
        val view = convertView ?: LayoutInflater.from(context).inflate(resource, parent, false)
        val textView: TextView = view.findViewById(R.id.spinner_text)
        textView.text = getItem(position)
        return view
    }
}
