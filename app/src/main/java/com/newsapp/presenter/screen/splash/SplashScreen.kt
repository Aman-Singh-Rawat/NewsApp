package com.newsapp.presenter.screen.splash

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.newsapp.R
import com.newsapp.presenter.screen.onboading.TrendingDemo
import com.newsapp.presenter.screen.onboading.WelcomeActivity

class SplashScreen : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        Handler(mainLooper).postDelayed({
            startActivity(Intent(
                this@SplashScreen,
                TrendingDemo::class.java
            ))
            finish()
        },3000)
    }
}