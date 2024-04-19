package com.example.seoulfestival.ui.home

import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.seoulfestival.R
import com.example.seoulfestival.Util.getNavOptions
import com.example.seoulfestival.base.BaseFragment
import com.example.seoulfestival.databinding.FragmentHomeBinding
import com.example.seoulfestival.viewModel.CulturalEventsViewModelFactory
import com.example.seoulfestival.viewmodel.CulturalEventsViewModel


class HomeFragment : BaseFragment<FragmentHomeBinding>() {
    override val layoutResourceId: Int = R.layout.fragment_home
    private lateinit var viewModel: CulturalEventsViewModel

    override fun aboutBinding() {
        val vt = viewDataBinding.homeToolbar
        vt.toolbarTitle.text = getString(R.string.app_name)
        vt.toolbarBack.visibility = View.GONE

        viewModel = ViewModelProvider(this, CulturalEventsViewModelFactory(requireContext())).get(CulturalEventsViewModel::class.java)
        val layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        val recyclerView = viewDataBinding.root.findViewById<RecyclerView>(R.id.recommendRecyclerView)
        recyclerView.layoutManager = layoutManager

        viewModel.fetchCulturalEvents()
        viewDataBinding.apply{
            operaMenu.setOnClickListener {
                findNavController().navigate(
                    HomeFragmentDirections.actionOperaFragment(),
                    getNavOptions
                )
            }
            danceMenu.setOnClickListener {
                findNavController().navigate(
                    HomeFragmentDirections.actionDanceFragment(),
                    getNavOptions
                )
            }
        }



    }

    override fun observeData() {
        viewModel.events.observe(viewLifecycleOwner, Observer { events ->
            events?.let {
                val adapter = RecommendPlaceAdapter(requireContext(), it)
                viewDataBinding.recommendRecyclerView.adapter = adapter
            }
        })
    }


}
