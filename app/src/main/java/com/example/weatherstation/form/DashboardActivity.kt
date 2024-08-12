package com.example.weatherstation.form

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.weatherstation.databinding.DashboardActivityBinding

class DashboardActivity : AppCompatActivity() {
    private lateinit var binding: DashboardActivityBinding
    companion object {
        private const val TAG = "DashboardActivity"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DashboardActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()
    }
}