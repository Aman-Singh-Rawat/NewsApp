package com.newsapp.presenter.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore

class UserProfileViewModel(application: Application): AndroidViewModel(application) {
    private val firestore by lazy { Firebase.firestore }
    fun getUserDetails() {

    }
    fun getUserArticles() {

    }

}