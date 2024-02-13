package com.newsapp.presenter.screen.auth.onboading

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import androidx.core.content.ContextCompat
import com.newsapp.R
import com.newsapp.databinding.ActivityWelcomeBinding

class WelcomeActivity : AppCompatActivity() {
    private lateinit var binding: ActivityWelcomeBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWelcomeBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        textColorChange() // Change the color of "Don't have an account?  Sign up"
    }
    val textColorChange = {
        val spannableString = SpannableString("Don't have an account?  Sign up")
        val signUpColor = ContextCompat.getColor(this, R.color.green)
        val startIndex = spannableString.indexOf("Sign up")
        spannableString.setSpan(
            ForegroundColorSpan(signUpColor),
            startIndex, startIndex + "Sign up".length,
            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        )
        binding.tvWelNotAccount.text = spannableString
    }
}