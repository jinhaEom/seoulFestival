package com.example.seoulfestival.ui.news


import android.view.View
import android.webkit.WebViewClient
import androidx.navigation.fragment.findNavController
import com.example.seoulfestival.R
import com.example.seoulfestival.base.BaseFragment
import com.example.seoulfestival.databinding.FragmentNewsDetailBinding


class DetailNewsFragment : BaseFragment<FragmentNewsDetailBinding>() {
    override val layoutResourceId: Int = R.layout.fragment_news_detail

    override fun aboutBinding() {
        setupToolbar(
            appLogoVisible = false,
            leftTitleVisible = false,
            toolbarTitleVisible = true,
            toolbarTitleText = getString(R.string.news),
            toolbarBackClickListener = View.OnClickListener {
                findNavController().navigateUp()
            }
        )
        val link = arguments?.getString("link") ?: return


        viewDataBinding.apply{

            viewDataBinding.webView.webViewClient = WebViewClient()
            viewDataBinding.webView.settings.javaScriptEnabled = true
            viewDataBinding.webView.loadUrl(link)
        }

    }

    override fun observeData() {

    }
}
