package com.example.seoulfestival.ui.home.dance

import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.seoulfestival.R
import com.example.seoulfestival.base.BaseFragment
import com.example.seoulfestival.databinding.FragmentPlayBinding
import com.example.seoulfestival.ui.EventAdapter
import com.example.seoulfestival.util.getNavOptions
import com.example.seoulfestival.viewModel.CulturalEventsViewModelFactory
import com.example.seoulfestival.viewmodel.CulturalEventsViewModel

class DanceFragment : BaseFragment<FragmentPlayBinding>() {
    override val layoutResourceId: Int = R.layout.fragment_play
    private lateinit var viewModel: CulturalEventsViewModel

    override fun aboutBinding() {
        viewDataBinding.lifecycleOwner = this

        viewModel = ViewModelProvider(this, CulturalEventsViewModelFactory(requireContext())).get(
            CulturalEventsViewModel::class.java)
        viewDataBinding.apply {
            setupToolbar(
                appLogoVisible = false,
                leftTitleVisible = false,
                toolbarTitleVisible = true,
                toolbarTitleText = getString(R.string.dance),
                toolbarBackClickListener = View.OnClickListener {
                    findNavController().navigateUp()
                }
            )
            val layoutManager = LinearLayoutManager(requireContext())
            playRecyclerView.apply {
                this.layoutManager = layoutManager
                adapter = EventAdapter(requireContext(), emptyList(), "무용") { event ->
                    val action = DanceFragmentDirections.actionDetailFragment(event)
                    findNavController().navigate(action, getNavOptions)
                }
            }

            viewModel.fetchAllCulturalEvents()
        }
    }

    override fun observeData() {
        viewModel.events.observe(viewLifecycleOwner, Observer { events ->
            events?.let {
                (viewDataBinding.playRecyclerView.adapter as EventAdapter).updateData(it)
            }
        })
    }

}
