package com.example.seoulfestival.ui.home.dance

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.seoulfestival.R
import com.example.seoulfestival.base.BaseFragment
import com.example.seoulfestival.databinding.FragmentDanceBinding
import com.example.seoulfestival.databinding.FragmentOperaBinding
import com.example.seoulfestival.ui.EventAdapter
import com.example.seoulfestival.viewModel.CulturalEventsViewModelFactory
import com.example.seoulfestival.viewmodel.CulturalEventsViewModel

class DanceFragment : BaseFragment<FragmentDanceBinding>() {
    override val layoutResourceId: Int = R.layout.fragment_dance
    private lateinit var viewModel: CulturalEventsViewModel

    override fun aboutBinding() {
        viewDataBinding.lifecycleOwner = this

        viewModel = ViewModelProvider(this, CulturalEventsViewModelFactory(requireContext())).get(
            CulturalEventsViewModel::class.java)
        viewDataBinding.apply {
            danceToolbar.toolbarTitle.text = getString(R.string.dance)
            danceToolbar.toolbarBack.visibility = View.VISIBLE

            val layoutManager = LinearLayoutManager(requireContext())
            danceRecyclerView.apply {
                this.layoutManager = layoutManager
                adapter = EventAdapter(requireContext(), emptyList(),"무용")
            }
            danceToolbar.toolbarBack.setOnClickListener{
                findNavController().navigateUp()
            }



            viewModel.fetchAllCulturalEvents()
        }





    }

    override fun observeData() {
        viewModel.events.observe(viewLifecycleOwner, Observer { events ->
            events?.let {
                (viewDataBinding.danceRecyclerView.adapter as EventAdapter).updateData(it)
            }
        })
    }

}
