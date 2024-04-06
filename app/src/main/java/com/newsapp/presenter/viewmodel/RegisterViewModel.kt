package com.newsapp.presenter.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.google.firebase.firestore.firestore
import com.newsapp.data.models.User
import com.newsapp.util.DatabaseCollection
import com.newsapp.util.SharedPrefsManager

class RegisterViewModel(private val application: Application) : AndroidViewModel(application) {

    private val auth: FirebaseAuth by lazy { Firebase.auth }
    private val firestore by lazy { Firebase.firestore }
    private val prefs by lazy { SharedPrefsManager.getInstance(application.applicationContext) }

    fun register(
        email: String, password: String, onSuccess: () -> Unit, onError: (String) -> Unit
    ) {
        if (email.isNotEmpty() && password.isNotEmpty()) {
            auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener {
                if (it.isSuccessful) {
                    it.result.user?.let { user ->
                        prefs.saveUser(User(uid = user.uid, email = user.email?:""))
                        firestore.collection(DatabaseCollection.USERS).document(user.uid).set(User(uid = user.uid, email = user.email?:""))
                    }
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