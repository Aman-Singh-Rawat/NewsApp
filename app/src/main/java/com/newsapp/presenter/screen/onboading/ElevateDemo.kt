package com.newsapp.presenter.screen.onboading

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.newsapp.R
import com.newsapp.databinding.ActivityElevateDemoBinding
import com.newsapp.databinding.ActivityProfileDemoBinding
import com.newsapp.databinding.ActivityTrendingDemoBinding

class ElevateDemo : AppCompatActivity() {
    private lateinit var binding: ActivityElevateDemoBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityElevateDemoBinding
            .inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnElevateContinue.setOnClickListener {
            openWelcomeActivity()
        }
    }

    private val openWelcomeActivity = {
        startActivity(Intent(
            this@ElevateDemo,
            WelcomeActivity::class.java
        ))
    }
}