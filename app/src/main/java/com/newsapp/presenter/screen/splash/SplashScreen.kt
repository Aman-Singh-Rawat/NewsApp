package com.newsapp.presenter.screen.splash

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.newsapp.databinding.ActivitySplashScreenBinding
import com.newsapp.presenter.screen.onboading.OnboardingActivity

class SplashScreen : AppCompatActivity() {
    private lateinit var binding: ActivitySplashScreenBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)


        Handler(mainLooper).postDelayed({
            startActivity(Intent(
                this@SplashScreen,
                OnboardingActivity::class.java
            ))
            finish()
        },3000)
    }
}