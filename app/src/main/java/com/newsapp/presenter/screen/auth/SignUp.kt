package com.newsapp.presenter.screen.auth

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.newsapp.R

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