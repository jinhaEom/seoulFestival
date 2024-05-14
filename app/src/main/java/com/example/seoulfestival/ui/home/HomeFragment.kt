package com.example.seoulfestival.ui.home

import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavDirections
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.PagerSnapHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.seoulfestival.R
import com.example.seoulfestival.util.getNavOptions
import com.example.seoulfestival.base.BaseFragment
import com.example.seoulfestival.databinding.FragmentHomeBinding
import com.example.seoulfestival.viewModel.CulturalEventsViewModelFactory
import com.example.seoulfestival.viewmodel.CulturalEventsViewModel

class HomeFragment : BaseFragment<FragmentHomeBinding>() {
    override val layoutResourceId: Int = R.layout.fragment_home
    private lateinit var viewModel: CulturalEventsViewModel
    private lateinit var layoutManager: LinearLayoutManager
    private lateinit var adapter: RecommendPlaceAdapter

    override fun aboutBinding() {
        val vt = viewDataBinding.homeToolbar
        vt.appLogo.visibility = View.VISIBLE
        vt.leftTitle.visibility = View.VISIBLE
        vt.leftTitle.text = getString(R.string.app_name)
        vt.appLogo.setOnClickListener {
            viewDataBinding.homeScrollView.smoothScrollTo(0, 0)
        }

        viewModel = ViewModelProvider(this, CulturalEventsViewModelFactory(requireContext())).get(CulturalEventsViewModel::class.java)
        layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        val recyclerView = viewDataBinding.root.findViewById<RecyclerView>(R.id.recommendRecyclerView)
        recyclerView.layoutManager = layoutManager

        val snapHelper = PagerSnapHelper()
        snapHelper.attachToRecyclerView(recyclerView)

        recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    // 스크롤이 멈췄을 때
                    val visiblePosition = layoutManager.findFirstCompletelyVisibleItemPosition()
                    if (visiblePosition != RecyclerView.NO_POSITION) {
                        val viewHolder = recyclerView.findViewHolderForAdapterPosition(visiblePosition) as? RecommendPlaceAdapter.ViewHolder
                        viewHolder?.showIndex()
                    }
                }
            }
        })

        viewModel.fetchCulturalEvents()
        mainMenuClick()
    }

    private fun mainMenuClick() {
        viewDataBinding.apply {
            setupMenuClickListener(operaMenu, HomeFragmentDirections.actionOperaFragment())
            setupMenuClickListener(danceMenu, HomeFragmentDirections.actionDanceFragment())
            setupMenuClickListener(classicMenu, HomeFragmentDirections.actionClassicFragment())
            setupMenuClickListener(gukakMenu, HomeFragmentDirections.actionGukakFragment())
            setupMenuClickListener(dramaMenu, HomeFragmentDirections.actionDramaFragment())
        }
    }

    private fun setupMenuClickListener(menuItem: View, navDirections: NavDirections) {
        menuItem.setOnClickListener {
            findNavController().navigate(navDirections, getNavOptions)
        }
    }

    override fun observeData() {
        viewModel.events.observe(viewLifecycleOwner, Observer { events ->
            events?.let {
                adapter = RecommendPlaceAdapter(requireContext(), it)
                viewDataBinding.recommendRecyclerView.adapter = adapter
            }
        })
    }
}
