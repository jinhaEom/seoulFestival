package com.example.seoulfestival.ui.search.adapter

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.seoulfestival.R
import com.example.seoulfestival.databinding.FestivalCardLayoutBinding
import com.example.seoulfestival.response.Event


class FestivalInfoAdapter(
    private val context: Context,
    private val festivals: List<Event>,
    private val onHide: () -> Unit
) : RecyclerView.Adapter<FestivalInfoAdapter.FestivalViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FestivalViewHolder {
        val binding = FestivalCardLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return FestivalViewHolder(binding, onHide)
    }

    override fun onBindViewHolder(holder: FestivalViewHolder, position: Int) {
        val festival = festivals[position]
        holder.bind(festival)
    }
    override fun getItemCount(): Int = festivals.size

    class FestivalViewHolder(
        private val binding: FestivalCardLayoutBinding,
        private val onHide: () -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {

        init {
            binding.closeBtn.setOnClickListener {
                onHide()
            }
        }
        fun bind(event: Event) {
            binding.festivalTitle.text = event.title ?: "No title provided"
            binding.festivalLocation.text = event.place ?: "No location provided"
            Glide.with(binding.root.context).load(event.img).into(binding.festivalImage)

            binding.bookingTv.setOnClickListener {
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(event.orgLink))
                binding.root.context.startActivity(intent)
            }
        }
    }
}
