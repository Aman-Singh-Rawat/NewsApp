package com.newsapp.presenter.screen.onboading

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.newsapp.R
import com.newsapp.databinding.ActivityTrendingDemoBinding

class TrendingDemo : AppCompatActivity() {
    lateinit var binding: ActivityTrendingDemoBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTrendingDemoBinding
            .inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnTrendingContinue.setOnClickListener {
            openWelcomeActivity()
        }

        binding.btnTrendingSkip.setOnClickListener {

        }
    }

    val openWelcomeActivity = {
        startActivity(Intent(
            this@TrendingDemo,
            WelcomeActivity::class.java
        ))
    }
}