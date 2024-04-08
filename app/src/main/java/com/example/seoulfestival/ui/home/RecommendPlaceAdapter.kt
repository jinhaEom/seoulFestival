package com.example.seoulfestival.ui.home

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
import com.example.seoulfestival.response.Event
class RecommendPlaceAdapter(private val context: Context, private val events: List<Event>) : RecyclerView.Adapter<RecommendPlaceAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_recommand_place, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int = events.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val event = events[position]
        holder.bind(event)
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val locationTx: TextView = itemView.findViewById(R.id.locationTx)
        val placeTx: TextView = itemView.findViewById(R.id.placeTx)
        val festivalName : TextView = itemView.findViewById(R.id.festivalName)
        val placeImageView : ImageView = itemView.findViewById(R.id.placeImageView)
        fun bind(event: Event) {
            locationTx.text = event.guname
            placeTx.text= event.place
            festivalName.text = event.title
            Glide.with(context)
                .load(event.img)
                .into(placeImageView) // 이미지 로딩에 Glide 사용
            placeImageView.setOnClickListener {
                val url = event.addr
                if (url != null && url.isNotEmpty()) {
                    val intent = Intent(Intent.ACTION_VIEW)
                    intent.data = Uri.parse(url)
                    context.startActivity(intent)
                }
            }
        }

    }
}