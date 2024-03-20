package com.newsapp.presenter.screen.splash

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import com.newsapp.MainActivity
import com.newsapp.databinding.ActivitySplashScreenBinding
import com.newsapp.presenter.screen.auth.register.SignUp
import com.newsapp.util.PrefKeys
import com.newsapp.util.SharedPrefsManager

class SplashScreen : AppCompatActivity() {
    private lateinit var binding: ActivitySplashScreenBinding
    private val prefs by lazy { SharedPrefsManager.getInstance(this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)
        Handler(mainLooper).postDelayed({
            checkUserSession()
            finish()
        }, 3000)
    }
    private fun checkUserSession() {
        val isLoggedIn = prefs.getBoolean(PrefKeys.IS_LOGGED_IN, false)
        val intent: Intent = if (isLoggedIn) {
            Intent(this, MainActivity::class.java)
        } else {
            Intent(this,SignUp ::class.java)
        }
        startActivity(intent)
    }
}