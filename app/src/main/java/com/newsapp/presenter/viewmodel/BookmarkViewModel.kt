package com.newsapp.presenter.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore
import com.google.firebase.firestore.toObject
import com.newsapp.data.models.Article
import com.newsapp.util.DatabaseCollection
import com.newsapp.util.SharedPrefsManager

class BookmarkViewModel(application: Application): AndroidViewModel(application) {
    private val bookmarkList: MutableList<String> = mutableListOf()
    private val prefs by lazy { SharedPrefsManager.getInstance(application.applicationContext) }
    private val firestore by lazy { Firebase.firestore }
    fun saveBookmarkList(item: String, onSuccess: () -> Unit) {
        bookmarkList.add(item)
        prefs.getUser()?.let { user ->
            firestore.collection(DatabaseCollection.BOOKMARK).document(user.uid)
                .set(mapOf("bookmarkList" to bookmarkList))
                .addOnSuccessListener {
                    onSuccess.invoke()
                }
        }
    }

    fun getBookmarkList(onSuccess: (MutableList<String>) -> Unit) {
        bookmarkList.clear()
        prefs.getUser()?.let { user ->
            firestore.collection(DatabaseCollection.BOOKMARK).document(user.uid)
                .get().addOnSuccessListener {documentSnap ->
                    if (documentSnap.exists()) {
                        val bookmarkData = documentSnap.data
                        val bookmarkStringList = bookmarkData?.get("bookmarkList") as? List<String>

                        bookmarkStringList?.let {
                            bookmarkList.addAll(it)
                            onSuccess(bookmarkList)
                        }
                    }
                }
        }
    }

    fun doArticleSave(articleId: String, list: MutableList<String>, flag: Boolean, onSuccess: () -> Unit) {
        firestore.collection(DatabaseCollection.ARTICLES).document(articleId).get()
            .addOnSuccessListener {
                if (it.exists()) {
                    val article = it.toObject(Article::class.java)
                    article?.let {
                        val mapData: Map<String, Any>
                        if (flag) {
                            mapData = mapOf(
                                "topicList" to list,
                                "articleSaved" to true
                            )
                        } else {
                            mapData = mapOf(
                                "topicList" to emptyList<String>(),
                                "articleSaved" to false
                            )
                        }
                        firestore.collection(DatabaseCollection.ARTICLES).document(articleId).update(mapData)
                            .addOnSuccessListener {
                                Log.d("debugging", "done")
                                onSuccess()
                            }
                            .addOnFailureListener {
                                Log.d("debugging", it.message.toString())
                            }
                    }
                }
            }
    }

    fun isArticleSavedOrNot(articleId: String, onSuccess: (Boolean) -> Unit) {
        firestore.collection(DatabaseCollection.ARTICLES).document(articleId).get()
            .addOnSuccessListener {docSnapshot ->
                if (docSnapshot.exists()) {
                    val article = docSnapshot.toObject(Article::class.java)
                    article?.let {
                        onSuccess(article.articleSaved)
                    }
                }
            }
    }

}