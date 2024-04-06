package com.newsapp.presenter.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore
import com.google.gson.Gson
import com.newsapp.R
import com.newsapp.data.models.NewsInterest
import com.newsapp.util.DatabaseCollection
import com.newsapp.util.SharedPrefsManager

class NewsInterestViewModel(private val application: Application) : AndroidViewModel(application) {

    private val prefs by lazy { SharedPrefsManager.getInstance(application.applicationContext) }
    private val firestore by lazy { Firebase.firestore }
    private val gson by lazy { Gson() }

    fun saveUserInterests(selectedInterest: MutableList<NewsInterest>) {
        val interest = selectedInterest.map { it.interestName }
        prefs.saveUserInterest(interest.toSet())
//        val json = gson.toJson(interest)
        prefs.getUser()?.let {
            firestore.collection(DatabaseCollection.USER_INTEREST).document(it.uid)
                .set(mapOf("interest" to interest))
        }
    }

    fun getInterests(): List<NewsInterest> {
        return listOf(
            NewsInterest(R.drawable.img_politics, "Politics"),
            NewsInterest(R.drawable.img_football, "Sport"),
            NewsInterest(R.drawable.img_technology, "Technology"),
            NewsInterest(R.drawable.img_science, "Science"),
            NewsInterest(R.drawable.img_business, "Business"),
            NewsInterest(R.drawable.img_fashion, "Fashion"),
            NewsInterest(R.drawable.img_health, "Health"),
            NewsInterest(R.drawable.img_entertainment, "Entertainment"),
            NewsInterest(R.drawable.img_finance, "Finance")
        )
    }

}