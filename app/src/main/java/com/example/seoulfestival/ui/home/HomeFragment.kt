package com.example.seoulfestival.ui.home

import android.util.Log
import android.view.View
import android.widget.TextView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.seoulfestival.R
import com.example.seoulfestival.base.BaseFragment
import com.example.seoulfestival.databinding.FragmentHomeBinding
import com.example.seoulfestival.toolbar.ToolbarActivity
import com.example.seoulfestival.viewModel.CulturalEventsViewModelFactory
import com.example.seoulfestival.viewmodel.CulturalEventsViewModel

class HomeFragment : BaseFragment<FragmentHomeBinding>() {
    override val layoutResourceId: Int = R.layout.fragment_home
    private lateinit var viewModel: CulturalEventsViewModel

    override fun aboutBinding() {
        viewModel = ViewModelProvider(this, CulturalEventsViewModelFactory(requireContext())).get(CulturalEventsViewModel::class.java)

        val vt= viewDataBinding.homeToolbar
        vt.toolbarTitle.text=getString(R.string.app_name)
        vt.toolbarBack.visibility = View.GONE


        viewModel.events.observe(viewLifecycleOwner, Observer { events ->
            // 여기에서 데이터가 업데이트될 때마다 UI를 업데이트합니다.
            // 예를 들어, 첫 번째 이벤트의 제목을 TextView에 표시한다고 가정합니다.
            val firstEventTitle = events?.firstOrNull()?.title ?: "이벤트가 없습니다."
            viewDataBinding.testText.text = firstEventTitle
        })


    }

    override fun observeData() {
    }

    override fun onResume() {
        super.onResume()
    }
}
