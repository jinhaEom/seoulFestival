package com.example.seoulfestival.ui.home

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavDirections
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.PagerSnapHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.seoulfestival.R
import com.example.seoulfestival.base.BaseFragment
import com.example.seoulfestival.choice.ChoiceActivity
import com.example.seoulfestival.databinding.FragmentHomeBinding
import com.example.seoulfestival.util.getNavOptions
import com.example.seoulfestival.viewModel.CulturalEventsViewModelFactory
import com.example.seoulfestival.viewmodel.CulturalEventsViewModel

class HomeFragment : BaseFragment<FragmentHomeBinding>() {
    override val layoutResourceId: Int = R.layout.fragment_home
    private lateinit var viewModel: CulturalEventsViewModel
    private lateinit var layoutManager: LinearLayoutManager
    private lateinit var adapter: RecommendPlaceAdapter

    override fun aboutBinding() {
        setupToolbar(
            true, true, false, getString(R.string.app_name),
            appLogoClickListener = { viewDataBinding.homeScrollView.smoothScrollTo(0, 0) }
        )
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
        setupChangeRecommendClick()
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
    private fun setupChangeRecommendClick() {
        viewDataBinding.changeRecommendTx.setOnClickListener {
            val intent = Intent(requireContext(), ChoiceActivity::class.java)
            startActivity(intent)
        }
    }
    override fun observeData() {
        viewModel.events.observe(viewLifecycleOwner, Observer { events ->
            events?.let {
                val preferences: SharedPreferences = requireActivity().getSharedPreferences("UserPreferences", Context.MODE_PRIVATE)
                val selectedCategories = mutableSetOf<String>()

                if (preferences.getBoolean("opera", false)) selectedCategories.add("뮤지컬/오페라")
                if (preferences.getBoolean("dance", false)) selectedCategories.add("무용")
                if (preferences.getBoolean("classic", false)) selectedCategories.add("클래식")
                if (preferences.getBoolean("gukak", false)) selectedCategories.add("국악")
                if (preferences.getBoolean("drama", false)) selectedCategories.add("연극")

                val recommendedEvents = it.filter { event ->
                    selectedCategories.contains(event.codename)
                }

                adapter = RecommendPlaceAdapter(requireContext(), recommendedEvents)
                viewDataBinding.recommendRecyclerView.adapter = adapter
            }
        })
    }
}
