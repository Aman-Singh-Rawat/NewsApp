package com.newsapp.presenter.screen.auth.login

import android.app.Application
import android.provider.Settings.Global.getString
import androidx.lifecycle.AndroidViewModel
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.newsapp.R

class GoogleViewModel(private val application: Application) : AndroidViewModel(application) {

    private val auth: FirebaseAuth by lazy { Firebase.auth }
    private lateinit var googleSignInClient: GoogleSignInClient
    fun googleLogIn() {
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.defult_web_client_id)).requestEmail().build()
        googleSignInClient = GoogleSignIn.getClient(this, gso)
    }

}