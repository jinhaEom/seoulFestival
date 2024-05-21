package com.example.seoulfestival.ui

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.seoulfestival.databinding.ItemPlayListBinding
import com.example.seoulfestival.model.Event

class EventAdapter(
    private val context: Context,
    private var events: List<Event>,
    private val eventType: String,
    private val onItemClicked: (Event) -> Unit
) : RecyclerView.Adapter<EventAdapter.ViewHolder>() {

    // 초기 필터링된 리스트
    private var filteredEvents = events.filter { it.codename == eventType }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemPlayListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int = filteredEvents.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(filteredEvents[position])
    }

    inner class ViewHolder(private val binding: ItemPlayListBinding) : RecyclerView.ViewHolder(binding.root) {
        init {
            binding.root.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    onItemClicked(filteredEvents[position])
                }
            }
        }

        fun bind(event: Event) {
            binding.contentTitle.text = event.title
            binding.contentPlace.text = event.place
            Glide.with(context).load(event.img).into(binding.contentImageView)
        }
    }

    // 데이터 업데이트 및 필터링
    fun updateData(newEvents: List<Event>) {
        events = newEvents
        filteredEvents = events.filter { it.codename == eventType }
        notifyDataSetChanged()
    }
}