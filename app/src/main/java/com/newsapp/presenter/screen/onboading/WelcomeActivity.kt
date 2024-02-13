package com.newsapp.presenter.screen.onboading

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import androidx.core.content.ContextCompat
import com.newsapp.R
import com.newsapp.databinding.ActivityWelcomeBinding
import com.newsapp.presenter.screen.auth.SignUp

class WelcomeActivity : AppCompatActivity() {
    private lateinit var binding: ActivityWelcomeBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWelcomeBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        textColorChange() // Change the color of "Don't have an account?  Sign up"

        binding.tvWelNotAccount.setOnClickListener {
            openSignUpActivity() // opening sign up activity
        }
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

    val openSignUpActivity = {
        startActivity(Intent(
            this@WelcomeActivity,
            SignUp::class.java
        ))
    }
}