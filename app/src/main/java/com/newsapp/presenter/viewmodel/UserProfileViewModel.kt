package com.newsapp.presenter.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore
import com.google.firebase.firestore.toObject
import com.google.gson.Gson
import com.newsapp.data.models.Article
import com.newsapp.data.models.User
import com.newsapp.util.DatabaseCollection

class UserProfileViewModel(application: Application): AndroidViewModel(application) {
    private val firestore by lazy { Firebase.firestore }
    private val gson by lazy { Gson() }
    fun getUserDetails(articleId: String, onSuccess: (User) -> Unit) {
        firestore.collection(DatabaseCollection.ARTICLES).get()
            .addOnSuccessListener {
                val article = it.documents.map {document ->
                    document.toObject(Article::class.java) ?: Article()
                }.filter {articles ->
                    articles.articleId == articleId
                }

                firestore.collection(DatabaseCollection.USERS).document(article[0].authorId).get()
                    .addOnSuccessListener { user ->
                        val user = user.toObject(User::class.java)
                        if (user != null)
                            onSuccess(user)
                    }
            }
            .addOnFailureListener {

            }

    }
    fun getUserArticles(userId: String, onSuccess: (List<Article>) -> Unit) {
        firestore.collection(DatabaseCollection.ARTICLES).get()
            .addOnSuccessListener {
                val article = it.documents.map { document ->
                    document.toObject(Article::class.java) ?: Article()
                }.filter {
                    userId == it.authorId
                }
                onSuccess(article)
            }
    }
}