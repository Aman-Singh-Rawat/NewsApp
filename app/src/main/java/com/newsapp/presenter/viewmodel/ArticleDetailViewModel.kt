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
    var followingList: MutableList<String> = mutableListOf()
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
        followingList.clear()
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
                                val userObject = currentUser.copy(followingList = followingList)
                                prefs.saveUser(userObject)
                                Log.d("debugging", "userObject is ${userObject.followingList}")
                                firestore.collection(DatabaseCollection.USERS).document(currentUser.uid)
                                    .update(mapOf("followingList" to followingList))
                                    .addOnSuccessListener {
                                        onSuccess(followingList.contains(article.authorId))
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
}