package com.example.seoulfestival.ui.search.adapter

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

class FestivalInfoAdapter(
    private val context: Context,
    private val festivals: List<Event>,
    private val onHide: () -> Unit
) : RecyclerView.Adapter<FestivalInfoAdapter.FestivalViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FestivalViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.festival_card_layout, parent, false)
        return FestivalViewHolder(view, onHide)
    }

    override fun onBindViewHolder(holder: FestivalViewHolder, position: Int) {
        val festival = festivals[position]
        holder.bind(festival)
    }
    override fun getItemCount(): Int = festivals.size

    class FestivalViewHolder(itemView: View, private val onHide: () -> Unit) : RecyclerView.ViewHolder(itemView) {
        private val closeButton: ImageView = itemView.findViewById(R.id.closeBtn)

        init {
            closeButton.setOnClickListener {
                onHide()
            }
        }
        fun bind(event: Event) {
            itemView.findViewById<TextView>(R.id.festivalTitle).text = event.title ?: "No title provided"
            itemView.findViewById<TextView>(R.id.festivalLocation).text = event.place ?: "No location provided"
            Glide.with(itemView.context).load(event.img).into(itemView.findViewById<ImageView>(R.id.festivalImage))
        }
    }
}
