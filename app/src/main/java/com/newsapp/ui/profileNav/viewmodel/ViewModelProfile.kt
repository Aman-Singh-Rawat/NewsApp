package com.newsapp.ui.profileNav.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore
import com.newsapp.util.DatabaseCollection
import com.newsapp.util.SharedPrefsManager

class ViewModelProfile(private val application: Application): AndroidViewModel(application) {
    private val prefs by lazy { SharedPrefsManager.getInstance(application.applicationContext) }
    private val firestore by lazy { Firebase.firestore }

    fun updateUserProfile(fullName: String, userName: String, bio: String, website: String) {
        val currentUser = prefs.getUser()
        if (currentUser != null) {
            val user = currentUser.copy(fullName = fullName, userName = userName, bio = bio, website = website)
            prefs.saveUser(user)
            firestore.collection(DatabaseCollection.users).document(user.uid).set(user)
        }
    }

}