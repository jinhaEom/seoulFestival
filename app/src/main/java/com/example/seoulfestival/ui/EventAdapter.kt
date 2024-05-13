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
    private var events: List<Event>,
    private val eventType: String,
    private val onItemClicked: (Event) -> Unit
) : RecyclerView.Adapter<EventAdapter.ViewHolder>() {

    // 초기 필터링된 리스트
    private var filteredEvents = events.filter { it.codename == eventType }

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
        init {
            itemView.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    onItemClicked(filteredEvents[position])
                }
            }
        }

        fun bind(event: Event) {
            titleTx.text = event.title
            locationTx.text = event.place
            Glide.with(context).load(event.img).into(imageView)
        }
    }

    // 데이터 업데이트 및 필터링
    fun updateData(newEvents: List<Event>) {
        events = newEvents
        filteredEvents = events.filter { it.codename == eventType }
        notifyDataSetChanged()
    }
}

