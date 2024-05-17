package com.example.seoulfestival.toolbar

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import com.example.seoulfestival.R
import android.widget.TextView
import androidx.appcompat.widget.AppCompatImageView

open class ToolbarActivity : AppCompatActivity() {

    protected lateinit var _binding: ViewDataBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    protected fun setContentViewWithBinding(layoutResID: Int) {
        _binding = DataBindingUtil.setContentView(this, layoutResID)
    }

     fun setupToolbar(
        appLogoVisible: Boolean,
        leftTitleVisible: Boolean,
        toolbarTitleVisible: Boolean,
        leftTitleText: String? = null,
        toolbarTitleText: String? = null,
        appLogoClickListener: View.OnClickListener? = null,
        toolbarBackClickListener: View.OnClickListener? = null
    ) {
        val toolbar = findViewById<View>(R.id.homeToolbar)
        val appLogo: AppCompatImageView = toolbar.findViewById(R.id.appLogo)
        val leftTitle: TextView = toolbar.findViewById(R.id.leftTitle)
        val toolbarTitle: TextView = toolbar.findViewById(R.id.toolbarTitle)
        val toolbarBack: AppCompatImageView = toolbar.findViewById(R.id.toolbarBack)

        appLogo.visibility = if (appLogoVisible) View.VISIBLE else View.GONE
        leftTitle.visibility = if (leftTitleVisible) View.VISIBLE else View.GONE
        toolbarTitle.visibility = if (toolbarTitleVisible) View.VISIBLE else View.GONE

        leftTitleText?.let {
            leftTitle.text = it
        }

        toolbarTitleText?.let {
            toolbarTitle.text = it
        }

        appLogoClickListener?.let {
            appLogo.setOnClickListener(it)
        }

        toolbarBackClickListener?.let {
            toolbarBack.visibility = View.VISIBLE
            toolbarBack.setOnClickListener(it)
        } ?: run {
            toolbarBack.visibility = View.GONE
        }
    }
}
