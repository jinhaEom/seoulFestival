package com.example.seoulfestival.ui.home

import android.view.View
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.example.seoulfestival.R
import com.example.seoulfestival.base.BaseFragment
import com.example.seoulfestival.databinding.FragmentPlayDetailBinding
import com.example.seoulfestival.model.Event

class PlayDetailFragment(override val layoutResourceId: Int = R.layout.fragment_play_detail) : BaseFragment<FragmentPlayDetailBinding>() {
    override fun aboutBinding() {
        setupToolbar(
            appLogoVisible = false,
            leftTitleVisible = false,
            toolbarTitleVisible = true,
            toolbarTitleText = getString(R.string.information),
            toolbarBackClickListener = View.OnClickListener {
                findNavController().navigateUp()
            }
        )
        val event = navArgs<PlayDetailFragmentArgs>().value.event
        displayEventData(event)
        viewDataBinding.apply{
            btOk.setOnClickListener {
                findNavController().navigateUp()
            }
        }

    }

    override fun observeData() {
    }
    private fun displayEventData(event: Event) {
        viewDataBinding.apply {
            titleTv.text = (event.title ?: R.string.default_info_text).toString()
            periodTv.text = (event.date ?: R.string.default_info_text).toString()
            locationTv.text = "${event.guname} - ${event.place}"
            ageTv.text = (event.age ?: R.string.default_info_text).toString()
            feeTv.text = (event.fee ?: R.string.default_info_text).toString()
            orgLinkTv.text = (event.orgLink ?: R.string.default_info_text).toString()

            event.img?.let {
                Glide.with(requireContext()).load(it).into(posterImg)
            } ?: posterImg.setImageResource(R.drawable.drama)  // 기본 이미지
        }
    }
}