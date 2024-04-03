package com.example.seoulfestival.base


import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.lifecycle.LifecycleOwner
import com.example.seoulfestival.toolbar.ToolbarActivity

abstract class BaseFragment<T : ViewDataBinding> : Fragment(), LifecycleOwner {


    lateinit var viewDataBinding: T
    abstract val layoutResourceId: Int
    var onBackPressedCallback : OnBackPressedCallback? = null


    abstract fun aboutBinding()
    abstract fun observeData()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewDataBinding = DataBindingUtil.inflate(inflater, layoutResourceId, container, false)
        return viewDataBinding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        aboutBinding()
        observeData()

    }


    fun viewDataBindingIsInitialized() : Boolean {
        return ::viewDataBinding.isInitialized
    }
    fun setupToolbar(title: String, showBackButton: Boolean) {
        Log.d("BaseFragment", "setupToolbar called with title: $title and backButtonVisibility: $showBackButton")
        (requireActivity() as? ToolbarActivity)?.apply {
            setToolbarTitle(title)
            showBackButton(showBackButton)
        }
    }


}