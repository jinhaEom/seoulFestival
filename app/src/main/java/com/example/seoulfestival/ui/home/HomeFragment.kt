package com.example.seoulfestival.ui.home

import android.util.Log
import android.view.View
import com.example.seoulfestival.R
import com.example.seoulfestival.base.BaseFragment
import com.example.seoulfestival.databinding.FragmentHomeBinding
import com.example.seoulfestival.toolbar.ToolbarActivity

class HomeFragment : BaseFragment<FragmentHomeBinding>() {
    override val layoutResourceId: Int = R.layout.fragment_home

    override fun aboutBinding() {
        Log.d("MyFragment", "Calling setupToolbar in onViewCreated")

        viewDataBinding.homeToolbar.toolbarTitle.text="Seoul Festival"
        viewDataBinding.homeToolbar.toolbarBack.visibility = View.GONE



    }

    override fun observeData() {
    }

    override fun onResume() {
        super.onResume()
    }
}
