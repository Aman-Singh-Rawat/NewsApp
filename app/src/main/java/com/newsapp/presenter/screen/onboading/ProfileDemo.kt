package com.newsapp.presenter.screen.onboading

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.newsapp.R
import com.newsapp.databinding.ActivityProfileDemoBinding
import com.newsapp.databinding.ActivityTrendingDemoBinding

class ProfileDemo : AppCompatActivity() {
    private lateinit var binding: ActivityProfileDemoBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProfileDemoBinding
            .inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnProfileContinue.setOnClickListener {
            openElevateActivity()
        }

        binding.btnProfileSkip.setOnClickListener {
            openWelcomeActivity()
        }
    }

    private val openElevateActivity = {
        startActivity(Intent(
            this@ProfileDemo,
            ElevateDemo::class.java
        ))
    }
    private val openWelcomeActivity = {
        startActivity(Intent(
            this@ProfileDemo,
            WelcomeActivity::class.java
        ))
    }
}