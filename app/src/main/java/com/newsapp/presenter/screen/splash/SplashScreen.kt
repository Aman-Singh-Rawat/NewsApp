package com.newsapp.presenter.screen.splash

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.activityViewModels
import com.newsapp.MainActivity
import com.newsapp.databinding.ActivitySplashScreenBinding
import com.newsapp.presenter.screen.onboading.OnboardingActivity
import com.newsapp.presenter.viewmodel.BookmarkViewModel
import com.newsapp.util.PrefKeys
import com.newsapp.util.SharedPrefsManager

class SplashScreen : AppCompatActivity() {
    private lateinit var binding: ActivitySplashScreenBinding
    private val prefs by lazy { SharedPrefsManager.getInstance(this) }
    private val viewModel by viewModels<BookmarkViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)
        Handler(mainLooper).postDelayed({
            checkUserSession()
            finish()
        }, 2500)
    }
    private fun checkUserSession() {
        val isLoggedIn = prefs.getBoolean(PrefKeys.IS_LOGGED_IN, false)
        val intent: Intent = if (isLoggedIn) {
            val user = prefs.getUser()
            if(user?.userName.isNullOrEmpty()){
                Intent(this,OnboardingActivity ::class.java)
            }else{
                viewModel.getBookmarkCategory()
                Intent(this, MainActivity::class.java)
            }
        } else {
            Intent(this,OnboardingActivity ::class.java)
        }
        startActivity(intent)
    }
}