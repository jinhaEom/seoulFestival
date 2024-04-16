package com.example.seoulfestival.ui.home.opera

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.seoulfestival.R
import com.example.seoulfestival.base.BaseFragment
import com.example.seoulfestival.databinding.FragmentOperaBinding
import com.example.seoulfestival.ui.EventAdapter
import com.example.seoulfestival.viewmodel.CulturalEventsViewModel
import com.example.seoulfestival.viewModel.CulturalEventsViewModelFactory

class OperaFragment : BaseFragment<FragmentOperaBinding>() {
    override val layoutResourceId: Int = R.layout.fragment_opera
    private lateinit var viewModel: CulturalEventsViewModel

    override fun aboutBinding() {
        viewDataBinding.lifecycleOwner = this

        viewModel = ViewModelProvider(this, CulturalEventsViewModelFactory(requireContext())).get(CulturalEventsViewModel::class.java)
        viewDataBinding.apply {
            val vt = operaToolbar
            vt.toolbarTitle.text = getString(R.string.opera)
            vt.toolbarBack.visibility = View.VISIBLE

            val layoutManager = LinearLayoutManager(requireContext())
            operaRecyclerView.apply {
                this.layoutManager = layoutManager
                adapter = EventAdapter(requireContext(), emptyList())
            }
            vt.toolbarBack.setOnClickListener{
                findNavController().navigateUp()
            }



            viewModel.fetchAllCulturalEvents()
        }





    }

    override fun observeData() {
        viewModel.events.observe(viewLifecycleOwner, Observer { events ->
            events?.let {
                val musicalsAndOperas = it.filter { event -> event.codename == "뮤지컬/오페라" }
                (viewDataBinding.operaRecyclerView.adapter as EventAdapter).updateData(musicalsAndOperas)
            }
        })
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupToolbar("Musicals and Operas", true)
    }
}
