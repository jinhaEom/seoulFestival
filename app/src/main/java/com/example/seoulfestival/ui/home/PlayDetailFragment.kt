package com.example.seoulfestival.ui.home

import android.content.Intent
import android.net.Uri
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.example.seoulfestival.R
import com.example.seoulfestival.base.BaseFragment
import com.example.seoulfestival.databinding.FragmentPlayDetailBinding
import com.example.seoulfestival.response.Event

class PlayDetailFragment(override val layoutResourceId: Int = R.layout.fragment_play_detail) : BaseFragment<FragmentPlayDetailBinding>() {
    override fun aboutBinding() {
        val event = navArgs<PlayDetailFragmentArgs>().value.event
        displayEventData(event)
        viewDataBinding.apply{
            btOk.setOnClickListener {
                findNavController().navigateUp()
            }
            orgLinkTv.setOnClickListener {
                val url = orgLinkTv.text.toString()
                val intent = Intent(Intent.ACTION_VIEW)
                intent.data = Uri.parse(url)
                startActivity(intent)
            }
        }
    }

    override fun observeData() {
    }
    private fun displayEventData(event: Event) {
        viewDataBinding.apply{
          titleTv.text = event.title
            periodTv.text = event.date
            ageTv.text = event.age
            feeTv.text = event.fee
            orgLinkTv.text = event.orgLink
            Glide.with(requireContext()).load(event.img).into(posterImg)
        }
    }
}