package com.newsapp.presenter.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore
import com.google.gson.Gson
import com.newsapp.data.models.Article
import com.newsapp.data.models.User
import com.newsapp.util.DatabaseCollection
import com.newsapp.util.SharedPrefsManager

class ArticleDetailViewModel(application: Application) : AndroidViewModel(application) {
    private val firestore by lazy { Firebase.firestore }
    private val gson by lazy { Gson() }
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

    fun followedOrNot(flag: Boolean, onSuccess: (Boolean) -> Unit) {
        var followingList: MutableList<String>
        prefs.getUser()?.let {currentUser ->
            followingList = currentUser.followingList
            if (flag && !followingList.contains(currentUser.uid)) {
                followingList.add(currentUser.uid)
            } else {
                Log.d("working", "remove")
                followingList.remove(currentUser.uid)
            }
            val user = currentUser.copy(followingList = followingList)
            prefs.saveUser(user)
            firestore.collection(DatabaseCollection.USERS).document(currentUser.uid)
                .update(mapOf("followingList" to followingList))
                .addOnSuccessListener {
                    Log.d("working", "${followingList.contains(currentUser.uid)}")
                    onSuccess(followingList.contains(currentUser.uid))
                }
        }
    }

    fun getFollowing(onSuccess: (Boolean) -> Unit) {
        prefs.getUser()?.let {user ->
            firestore.collection(DatabaseCollection.USERS).document(user.uid).get()
                .addOnCompleteListener {
                    if (it.result.data != null) {
                        val json = gson.toJson(it.result.data)
                        val data = gson.fromJson(json, User::class.java)
                        if (data.followingList.contains(user.uid))
                            onSuccess(true)
                        else
                            onSuccess(false)
                    }
                }
        }

    }

}