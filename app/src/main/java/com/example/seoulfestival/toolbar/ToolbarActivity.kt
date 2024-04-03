package com.example.seoulfestival.toolbar

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.seoulfestival.R
import com.example.seoulfestival.databinding.ToolbarLayoutBinding

abstract class ToolbarActivity : AppCompatActivity() {

    private lateinit var binding: ToolbarLayoutBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ToolbarLayoutBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)

        // 뒤로 가기 버튼의 기본 동작 설정
          setSupportActionBar(binding.toolbar) // Toolbar를 액션바로 설정
        binding.toolbar.title = "제목" // 제목 설정
        binding.toolbar.setNavigationIcon(R.drawable.ic_back) // 뒤로 가기 버튼 설정
        binding.toolbar.setNavigationOnClickListener {
            onBackPressed() // 뒤로 가기 버튼 클릭 이벤트 처리
        }
    }

    fun setToolbarTitle(title: String) {
        Log.d("ToolbarActivity", "Setting toolbar title: $title")
        binding.toolbarTitle.text = title
    }

    fun showBackButton(show: Boolean) {
        Log.d("ToolbarActivity", "Show back button: $show")
        binding.toolbarBack.visibility = if (show) View.VISIBLE else View.GONE
    }

}
