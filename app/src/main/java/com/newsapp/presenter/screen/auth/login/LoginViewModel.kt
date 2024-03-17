package com.newsapp.presenter.screen.auth.login


import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth

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
}