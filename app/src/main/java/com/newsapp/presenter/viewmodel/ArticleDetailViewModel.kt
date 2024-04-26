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
import com.newsapp.util.SharedPrefsManager

class ArticleDetailViewModel(application: Application) : AndroidViewModel(application) {
    private val firestore by lazy { Firebase.firestore }
    private val gson by lazy { Gson() }
    private var followingList: MutableList<String> = mutableListOf()

    private val prefs by lazy { SharedPrefsManager.getInstance(application.applicationContext) }
    fun getArticleData(articleId: String, onSuccess: (Article) -> Unit) {

        firestore.collection(DatabaseCollection.ARTICLES).document(articleId).get()
            .addOnSuccessListener { document ->
                if (document != null) {
                    val article = document.toObject(Article::class.java)
                    article?.let(onSuccess)
                }
            }.addOnFailureListener { e ->

            }
    }

    fun articleVisited(article: Article) {
        prefs.getUser()?.let { user ->
            val visitedUsers = article.userViewed.toMutableList()
            if (!visitedUsers.contains(user.uid)) {
                visitedUsers.add(user.uid)
                firestore.collection(DatabaseCollection.ARTICLES).document(article.articleId)
                    .set(article.copy(userViewed = visitedUsers))
            }
        }
    }

    fun followedOrNot(articleId: String, flag: Boolean, onSuccess: (Boolean) -> Unit) {
        var followingList: MutableList<String> = mutableListOf()

        getArticleData(articleId) {article ->
            prefs.getUser()?.let {user ->
                firestore.collection(DatabaseCollection.USERS).document(user.uid).get()
                    .addOnSuccessListener {document ->
                        if (document != null) {
                            val userData = document.toObject(User::class.java)
                            userData?.let { currentUser->
                                followingList = currentUser.followingList

                                if (flag && !followingList.contains(article.authorId)) {
                                    followingList.add(article.authorId)
                                } else if(!flag && followingList.contains(article.authorId)) {
                                    followingList.remove(article.authorId)
                                }
                                firestore.collection(DatabaseCollection.USERS).document(currentUser.uid)
                                    .update(mapOf("followingList" to followingList))
                                    .addOnSuccessListener {
                                        prefs.saveUser(currentUser.copy(followingList = followingList))
                                        totalFollowers() {followerList ->
                                            firestore.collection(DatabaseCollection.USERS).document(article.authorId)
                                                .set(mapOf("followerList" to followerList))
                                                .addOnSuccessListener {
                                                    Log.d("debugging", "success")
                                                    onSuccess(followingList.contains(article.authorId))
                                                }
                                                .addOnFailureListener {
                                                    Log.d("debugging", it.message.toString())
                                                }
                                        }
                                    }
                            }
                        }
                    }
            }
        }
    }

    fun getFollowing(articleId: String, onSuccess: (Boolean) -> Unit) {
        getArticleData(articleId) {article ->
            prefs.getUser()?.let {currentUser ->
                firestore.collection(DatabaseCollection.USERS).document(currentUser.uid).get()
                    .addOnSuccessListener { document ->
                        if (document != null) {
                            val user = document.toObject(User::class.java)
                            user?.let {
                                Log.d("debugging", "getFollowing is:: ${user.followingList.toString()}")
                                if (it.followingList.contains(article.authorId)) {
                                    Log.d("debugging", "getFollowing is:: ${user.followingList.contains(article.authorId)}")
                                    onSuccess(true)
                                } else {
                                    onSuccess(false)
                                }
                            }
                        }
                    }
            }
        }
    }

    fun saveCommentsSize(articleId: String, commentsSize: Int) {
        firestore.collection(DatabaseCollection.ARTICLES).document(articleId).get()
            .addOnSuccessListener { document ->
                if (document != null) {
                    val article = document.toObject(Article::class.java)
                    article?.let{
                        firestore.collection(DatabaseCollection.ARTICLES).document(articleId)
                            .update(mapOf("comments" to commentsSize.toLong()))
                            .addOnSuccessListener {}
                    }
                }
            }.addOnFailureListener { e ->

            }
    }

    private fun totalFollowers(onSuccess: (MutableList<String>) -> Unit) {
        val followersList: MutableList<String> = mutableListOf()
        prefs.getUser()?.let {currentUser ->
            firestore.collection(DatabaseCollection.USERS).get()
                .addOnSuccessListener {
                    if (!it.isEmpty) {
                        val users = it.documents.map {
                            val json = gson.toJson(it.data)
                            gson.fromJson(json, User::class.java)
                        }
                        for (user in users) {
                            if (user.followingList.contains(currentUser.uid)) {
                                followersList.add(user.uid)
                            }
                        }
                        onSuccess(followersList)
                    }
                }
                .addOnFailureListener {
                }
        }
    }

    fun getAllArticles(articleId: String, onSuccess: (List<Article>) -> Unit) {
        firestore.collection(DatabaseCollection.ARTICLES).document(articleId)
            .get()
            .addOnSuccessListener { documentSnapshot ->
                if (documentSnapshot.exists()) {
                    val article = documentSnapshot.toObject(Article::class.java)
                    article?.authorId?.let {authorId ->
                        firestore.collection(DatabaseCollection.ARTICLES).get()
                            .addOnSuccessListener {
                                if (!it.isEmpty) {
                                val articles = it.documents.map {
                                    val json = gson.toJson(it.data)
                                    gson.fromJson(json, Article::class.java)
                                }.filter { it.authorId == authorId }
                                onSuccess(articles)
                }
                            }
                            .addOnFailureListener {
                            }

                    }
                }
            }
            .addOnFailureListener {
            }
    }
}