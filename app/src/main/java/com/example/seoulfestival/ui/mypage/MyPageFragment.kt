package com.example.seoulfestival.ui.mypage

import com.example.seoulfestival.R
import com.example.seoulfestival.base.BaseFragment
import com.example.seoulfestival.databinding.FragmentHomeBinding

class MyPageFragment : BaseFragment<FragmentHomeBinding>() {
    override val layoutResourceId: Int = R.layout.fragment_mypage
    override fun aboutBinding() {
        setupToolbar("page", false)

    }

    override fun observeData() {

    }
}