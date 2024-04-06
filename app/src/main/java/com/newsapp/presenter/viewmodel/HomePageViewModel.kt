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
    private val firestore by lazy { Firebase.firestore }
    private val gson by lazy { Gson() }
    private val prefs by lazy { SharedPrefsManager.getInstance(application.applicationContext) }
    fun getSelectedData(topic: String, onSuccess: (List<Article>) -> Unit) {
        firestore.collection(DatabaseCollection.ARTICLES).get()
            .addOnSuccessListener {
                if (!it.isEmpty) {
                    if (topic != "All") {
                        val articles = it.documents.map {
                            val json = gson.toJson(it.data)
                            gson.fromJson(json, Article::class.java)
                        }.filter { it.topic == topic }
                        onSuccess(articles)
                    } else {
                        val article = it.documents.map {document ->
                            document.toObject(Article::class.java) ?: Article()
                        }
                        onSuccess(article)
                    }
                }
            }
            .addOnFailureListener {
        }
    }

    fun saveArticle(saveArticle: MutableList<String>) {
        prefs.getUser()?.let {
            firestore.collection(DatabaseCollection.BOOKMARK).document(it.uid).set(mapOf("bookmark" to saveArticle))
                .addOnSuccessListener {

                }
                .addOnFailureListener {

                }
        }

    }
}