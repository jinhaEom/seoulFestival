package com.example.seoulfestival.ui

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.seoulfestival.R
import com.example.seoulfestival.response.Event

class EventAdapter(
    private val context: Context,
    private var events: List<Event>
) : RecyclerView.Adapter<EventAdapter.ViewHolder>() {

    // 필터링된 리스트
    private var filteredEvents = events.filter { it.codename == "뮤지컬/오페라" }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_play_list, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int = filteredEvents.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(filteredEvents[position])
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val locationTx: TextView = itemView.findViewById(R.id.contentPlace)
        private val titleTx: TextView = itemView.findViewById(R.id.contentTitle)
        private val imageView: ImageView = itemView.findViewById(R.id.contentImageView)

        fun bind(event: Event) {
            titleTx.text = event.title
            locationTx.text = event.place
            Glide.with(context).load(event.img).into(imageView)
        }
    }

    // 데이터 업데이트 함수
    fun updateData(newEvents: List<Event>) {
        events = newEvents
        filteredEvents = events.filter { it.codename == "뮤지컬/오페라" }
        notifyDataSetChanged()
    }
}
