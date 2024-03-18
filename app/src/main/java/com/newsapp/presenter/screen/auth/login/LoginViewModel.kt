package com.newsapp.presenter.screen.auth.login


import android.app.Application
import android.content.Intent
import androidx.lifecycle.AndroidViewModel
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.Firebase
import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.newsapp.R

class LoginViewModel(private val application: Application) : AndroidViewModel(application) {

    private val auth: FirebaseAuth by lazy { Firebase.auth }

    fun login(email: String, password: String, onSuccess: () -> Unit, onError: (String) -> Unit) {
        if (email.isNotEmpty() && password.isNotEmpty()) {
            auth.signInWithEmailAndPassword(email, password).addOnCompleteListener {
                if (it.isSuccessful) {
                    onSuccess.invoke()
                } else {
                    onError(it.exception?.message ?: "Something went wrong..")
                }
            }
        } else {
            onError("Please enter valid email & password.")
        }
    }

    fun requestGoogleLogin(): Intent? {
        return try {
            val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(application.getString(R.string.cloud_client_id)).requestEmail()
                .build()
            GoogleSignIn.getClient(application.applicationContext, gso).signInIntent
        } catch (e: Exception) {
            null
        }
    }

    fun authenticateGoogleLogin(
        credential: AuthCredential, onSuccess: () -> Unit, onError: (String) -> Unit
    ) {
        auth.signOut()
        auth.signInWithCredential(credential).addOnCompleteListener {
            if (it.isSuccessful) {
                onSuccess.invoke()
            } else {
                onError(it.exception?.message ?: "Something went wrong..")
            }
        }
    }


}