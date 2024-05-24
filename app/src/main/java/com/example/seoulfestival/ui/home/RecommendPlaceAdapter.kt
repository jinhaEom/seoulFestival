package com.example.seoulfestival.ui.home

import android.content.Context
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.seoulfestival.R
import com.example.seoulfestival.databinding.ItemRecommandPlaceBinding
import com.example.seoulfestival.model.Event
import com.example.seoulfestival.ui.home.HomeFragmentDirections

class RecommendPlaceAdapter(private val context: Context, private val events: List<Event>) : RecyclerView.Adapter<RecommendPlaceAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemRecommandPlaceBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int = events.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val event = events[position]
        holder.bind(event, position)
        Log.d("RecommendPlaceAdapter", "Binding view holder for position $position: $event")
    }

    inner class ViewHolder(private val binding: ItemRecommandPlaceBinding) : RecyclerView.ViewHolder(binding.root) {
        private val handler = Handler(Looper.getMainLooper())
        private val hideRunnable = Runnable { hideIndex() }

        init {
            binding.placeImageView.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    val event = events[position]
                    val action = HomeFragmentDirections.actionPlayDetailFragment(event)
                    binding.root.findNavController().navigate(action)
                }
            }
        }

        fun bind(event: Event, position: Int) {
            binding.locationTx.text = event.guname
            binding.placeTx.text = event.place
            binding.festivalName.text = event.title
            binding.indexTx.text = "${position + 1} / ${events.size}"
            Glide.with(context)
                .load(event.img)
                .into(binding.placeImageView)

            showIndex()
        }

        fun showIndex() {
            binding.indexTx.visibility = View.VISIBLE
            binding.indexTx.startAnimation(AnimationUtils.loadAnimation(context, R.anim.fade_in))
            handler.removeCallbacks(hideRunnable)
            handler.postDelayed(hideRunnable, 3000)
        }

        fun hideIndex() {
            binding.indexTx.startAnimation(AnimationUtils.loadAnimation(context, R.anim.fade_out))
            binding.indexTx.visibility = View.GONE
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
