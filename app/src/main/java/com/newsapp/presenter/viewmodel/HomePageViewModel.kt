package com.newsapp.presenter.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore
import com.google.gson.Gson
import com.newsapp.data.models.Article
import com.newsapp.util.DatabaseCollection
import com.newsapp.util.SharedPrefsManager

class HomePageViewModel(private val application: Application) : AndroidViewModel(application)  {
    private val prefs by lazy { SharedPrefsManager.getInstance(application.applicationContext) }
    private val firestore by lazy { Firebase.firestore }
    private val gson by lazy { Gson() }

    fun getSelectedData(onSuccess: (List<Article>) -> Unit) {
        firestore.collection(DatabaseCollection.articles).get()
            .addOnSuccessListener {
                if (!it.isEmpty) {
                    val articles = it.documents.map {
                        val json = gson.toJson(it.data)
                        gson.fromJson(json, Article::class.java)
                    }.filter { it.topic == prefs.getUser()?.uid }
                    onSuccess(articles)
                }
            }
            .addOnFailureListener {
        }
    }
}