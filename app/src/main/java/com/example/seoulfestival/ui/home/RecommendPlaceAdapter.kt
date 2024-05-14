package com.example.seoulfestival.ui.home

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
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
        holder.bind(event, position)
        Log.d("RecommendPlaceAdapter", "Binding view holder for position $position: $event")
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val locationTx: TextView = itemView.findViewById(R.id.locationTx)
        val placeTx: TextView = itemView.findViewById(R.id.placeTx)
        val festivalName: TextView = itemView.findViewById(R.id.festivalName)
        val indexTx: TextView = itemView.findViewById(R.id.indexTx)
        val placeImageView: ImageView = itemView.findViewById(R.id.placeImageView)
        private val handler = Handler(Looper.getMainLooper())
        private val hideRunnable = Runnable { hideIndex() }

        fun bind(event: Event, position: Int) {
            locationTx.text = event.guname
            placeTx.text = event.place
            festivalName.text = event.title
            indexTx.text = "${position + 1} / ${events.size}"
            Glide.with(context)
                .load(event.img)
                .into(placeImageView)

            showIndex()

            placeImageView.setOnClickListener {
                val url = event.addr
                if (url != null && url.isNotEmpty()) {
                    val intent = Intent(Intent.ACTION_VIEW)
                    intent.data = Uri.parse(url)
                    context.startActivity(intent)
                }
            }
        }
        fun showIndex() {
            indexTx.visibility = View.VISIBLE
            indexTx.startAnimation(AnimationUtils.loadAnimation(context, R.anim.fade_in))
            handler.removeCallbacks(hideRunnable)
            handler.postDelayed(hideRunnable, 3000)
        }

        fun hideIndex() {
            indexTx.startAnimation(AnimationUtils.loadAnimation(context, R.anim.fade_out))
            indexTx.visibility = View.GONE
        }

        fun clear() {
            handler.removeCallbacks(hideRunnable)
        }
    }
    override fun onViewRecycled(holder: ViewHolder) {
        super.onViewRecycled(holder)
        holder.clear()
    }
}