package com.newsapp.presenter.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore
import com.google.firebase.firestore.toObject
import com.newsapp.data.models.Article
import com.newsapp.util.DatabaseCollection
import com.newsapp.util.SharedPrefsManager

class BookmarkViewModel(application: Application): AndroidViewModel(application) {
    private val bookmarkList: MutableList<String> = mutableListOf()
    val bookmarkLiveData: MutableLiveData<List<String>> = MutableLiveData()
    val articleLiveData: MutableLiveData<List<String>> = MutableLiveData()
    var articleList: MutableList<String> = mutableListOf()

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
        bookmarkLiveData.value = bookmarkList
    }

    fun getBookmarkList() {
        bookmarkList.clear()
        prefs.getUser()?.let { user ->
            firestore.collection(DatabaseCollection.BOOKMARK).document(user.uid)
                .get().addOnSuccessListener {documentSnap ->
                    if (documentSnap.exists()) {
                        val bookmarkData = documentSnap.data
                        val bookmarkStringList = bookmarkData?.get("bookmarkList") as? List<String>

                        bookmarkStringList?.let {
                            bookmarkList.addAll(it)
                            bookmarkLiveData.value = it
                        }
                    }
                }
        }
    }

    fun doArticleSave(articleId: String, list: MutableList<String>, onSuccess: (Boolean) -> Unit) {
        var flag = false
        articleList.clear()
        getArticleSaved(articleId) { listOfArticles ->
            listOfArticles.value?.let {
                articleList = it.toMutableList()
                prefs.getUser()?.let {
                    if (articleList.contains(it.uid)) {
                        Log.d("debugging", "contains")
                        articleList.remove(it.uid)
                    }
                    else {
                        Log.d("debugging", "not contains")
                        articleList.add(it.uid)
                        flag = true
                    }
                    articleLiveData.value = articleList
                    Log.d("debugging", "live data is:: ${articleLiveData.value}")
                    firestore.collection(DatabaseCollection.ARTICLES).document(articleId)
                        .update(mapOf("topicList" to list, "savedArticleList" to articleList))
                        .addOnSuccessListener {
                            onSuccess(flag)
                        }
                }
            }
        }
    }

    fun getArticleSaved(articleId: String, onSuccess: (MutableLiveData<List<String>>) -> Unit) {
        articleList.clear()
        firestore.collection(DatabaseCollection.ARTICLES).document(articleId).get()
            .addOnSuccessListener {docSnapshot ->
                val article = docSnapshot.toObject(Article::class.java)
                if (article != null) {
                    articleList = article.savedArticleList.toMutableList()

                    articleLiveData.value = articleList
                    onSuccess(articleLiveData)
                }

            }
    }
}