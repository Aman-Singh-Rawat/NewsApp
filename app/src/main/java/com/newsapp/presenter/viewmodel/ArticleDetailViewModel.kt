package com.newsapp.presenter.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore
import com.google.gson.Gson
import com.newsapp.data.models.Article
import com.newsapp.util.DatabaseCollection
import com.newsapp.util.SharedPrefsManager

class ArticleDetailViewModel(application: Application): AndroidViewModel(application) {
    private val firestore by lazy { Firebase.firestore }
    private val gson by lazy { Gson() }
    private val prefs by lazy { SharedPrefsManager.getInstance(application.applicationContext) }
    fun getArticleData(articleId: String, onSuccess: (Article) -> Unit) {

        firestore.collection(DatabaseCollection.articles).document(articleId).get()
            .addOnSuccessListener {document ->
                if (document != null) {
                    val article = document.toObject(Article::class.java)
                    onSuccess(article!!)
                }
            }.addOnFailureListener { e ->

            }
    }
}
/*
* firestore.collection(DatabaseCollection.articles).get()
            .addOnSuccessListener {
                if (!it.isEmpty) {
                    val articles = it.documents.map {
                        val json = gson.toJson(it.data)
                        gson.fromJson(json, Article::class.java)
                    }.filter { it.articleId == articleId }
                    onSuccess(articles)
                }
            }
            .addOnFailureListener {
            }*/