package com.newsapp.presenter.screen.auth

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import com.newsapp.R
import com.newsapp.R.id.ivBackArrowCreate
import com.newsapp.presenter.screen.onboading.WelcomeFragment

class SignUp : AppCompatActivity() {

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)
//        val ivBackArrowCreate = findViewById<ImageView>(ivBackArrowCreate)
//        ivBackArrowCreate.setOnClickListener {
//            val intent = Intent(this, WelcomeFragment::class.java)
//            startActivity(intent)
//        }
    }
}