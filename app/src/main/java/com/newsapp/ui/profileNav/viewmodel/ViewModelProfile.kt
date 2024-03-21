package com.newsapp.ui.profileNav.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.newsapp.util.SharedPrefsManager

class ViewModelProfile(private val application: Application): AndroidViewModel(application) {
    private val prefs by lazy { SharedPrefsManager.getInstance(application.applicationContext) }

    fun updateUserProfile(fullName: String, userName: String, bio: String, website: String) {
        val currentUser = prefs.getUser()
        if (currentUser != null) {
            prefs.saveUser(
                currentUser.copy(
                    fullName = fullName,
                    userName = userName,
                    bio = bio,
                    website = website
                )
            )
        }
    }

}