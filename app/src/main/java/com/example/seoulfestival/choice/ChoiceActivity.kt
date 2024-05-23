package com.example.seoulfestival.choice

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.seoulfestival.MainActivity
import com.example.seoulfestival.R
import com.example.seoulfestival.databinding.ActivityChoiceBinding

class ChoiceActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var binding: ActivityChoiceBinding
    private var selectedMenuView: View? = null
    private var selectedCheckView: View? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityChoiceBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.apply {
            operaMenu.setOnClickListener(this@ChoiceActivity)
            danceMenu.setOnClickListener(this@ChoiceActivity)
            classicMenu.setOnClickListener(this@ChoiceActivity)
            gukakMenu.setOnClickListener(this@ChoiceActivity)
            dramaMenu.setOnClickListener(this@ChoiceActivity)

            saveBtn.setOnClickListener {
                savePreferences()
            }
        }
    }

    override fun onClick(v: View) {
        binding.apply {
            when (v.id) {
                R.id.operaMenu -> selectMenu(operaMenu, checkingOpera)
                R.id.danceMenu -> selectMenu(danceMenu, checkingDance)
                R.id.classicMenu -> selectMenu(classicMenu, checkingClassic)
                R.id.gukakMenu -> selectMenu(gukakMenu, checkingGukak)
                R.id.dramaMenu -> selectMenu(dramaMenu, checkingDrama)
            }
        }
    }

    private fun selectMenu(menuView: View, checkView: View) {
        selectedMenuView?.isSelected = false
        selectedCheckView?.visibility = View.GONE

        menuView.isSelected = true
        checkView.visibility = View.VISIBLE

        selectedMenuView = menuView
        selectedCheckView = checkView
    }

    private fun savePreferences() {
        val preferences: SharedPreferences = getSharedPreferences("UserPreferences", MODE_PRIVATE)
        val editor: SharedPreferences.Editor = preferences.edit()
        if (selectedMenuView == null) {
            Toast.makeText(this, "공연을 선택해주세요", Toast.LENGTH_SHORT).show()
            return
        }
        binding.apply {
            editor.putBoolean("opera", operaMenu.isSelected)
            editor.putBoolean("dance", danceMenu.isSelected)
            editor.putBoolean("classic", classicMenu.isSelected)
            editor.putBoolean("gukak", gukakMenu.isSelected)
            editor.putBoolean("drama", dramaMenu.isSelected)
            editor.putBoolean("firstRun", false)
            editor.apply()

            val intent = Intent(this@ChoiceActivity, MainActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}
