package com.newsapp.presenter.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore
import com.google.gson.Gson
import com.newsapp.data.models.Article
import com.newsapp.util.DatabaseCollection
import com.newsapp.util.DatabaseEntity
import com.newsapp.util.SharedPrefsManager

class BookmarkViewModel(application: Application) : AndroidViewModel(application) {

    private val gson by lazy { Gson() }
    private val bookmarkCategoryList: MutableList<String> = mutableListOf()
    val bookmarkCategory: MutableLiveData<List<String>> = MutableLiveData()
    val bookmarkArticles: MutableLiveData<List<Article>> = MutableLiveData()

    private val prefs by lazy { SharedPrefsManager.getInstance(application.applicationContext) }
    private val firestore by lazy { Firebase.firestore }

    fun saveBookmarkCategory(item: String, onSuccess: () -> Unit, onFailure: (String) -> Unit) {
        if (bookmarkCategoryList.contains(item)) {
            onFailure("Bookmark category already exist.")
        } else {
            bookmarkCategoryList.add(item)
            bookmarkCategory.postValue(bookmarkCategoryList)
            prefs.getUser()?.let { user ->
                firestore.collection(DatabaseCollection.BOOKMARK_CATEGORY).document(user.uid)
                    .set(mapOf(DatabaseEntity.CATEGORY to bookmarkCategoryList))
                    .addOnSuccessListener {
                        onSuccess.invoke()
                    }.addOnFailureListener {
                        onFailure(it.message.toString())
                    }
            }
        }
    }

    fun getBookmarkCategory() {
        prefs.getUser()?.let { user ->
            firestore.collection(DatabaseCollection.BOOKMARK_CATEGORY).document(user.uid)
                .get().addOnSuccessListener { documentSnap ->
                    try {
                        val bookmarkCategory =
                            (documentSnap.data?.get(DatabaseEntity.CATEGORY) as? List<String>)
                                ?: emptyList()
                        bookmarkCategoryList.addAll(bookmarkCategory)
                        this.bookmarkCategory.postValue(bookmarkCategory)
                        prefs.saveUser(user.copy(bookmarkCategoryList = bookmarkCategory.toMutableList()))
                    } catch (e: Exception) {
                        bookmarkCategoryList.addAll(emptyList())
                        bookmarkCategory.postValue(emptyList())
                    }
                }
        }
    }

    fun isBookmarked(articleId: String): Boolean {
        return prefs.getUser()?.bookmarkList?.contains(articleId) ?: false
    }

    fun getBookmarkArticles() {
        prefs.getUser()?.let { user ->
            firestore.collection(DatabaseCollection.ARTICLES)
                .get()
                .addOnSuccessListener { querySnapshot ->
                    val articles =
                        querySnapshot.documents.filter { user.bookmarkList.contains(it.id) }
                            .map { it.toObject(Article::class.java) }

                    bookmarkArticles.postValue(articles as List<Article>? ?: emptyList())
                }.addOnFailureListener {
                    bookmarkArticles.postValue(emptyList())
                }
        }
    }

    fun checkBookmark(
        articleId: String,
        onSuccess: (Boolean) -> Unit,
        onFailure: (String) -> Unit
    ) {
        prefs.getUser()?.let { user ->
            val bookmarks = if (user.bookmarkList.contains(articleId)) {
                user.bookmarkList.apply { remove(articleId) }
            } else {
                user.bookmarkList.apply { add(articleId) }
            }
            firestore.collection(DatabaseCollection.USERS).document(user.uid)
                .set(user.copy(bookmarkList = bookmarks)).addOnSuccessListener {
                    prefs.saveUser(user.copy(bookmarkList = bookmarks))
                    onSuccess(bookmarks.contains(articleId))
                }.addOnFailureListener {
                    onFailure(it.message.toString())
                }
        }
    }

//    fun bookmarkArticle(
//        articleId: String,
//        bookmarkCategory: String,
//        onSuccess: (Boolean) -> Unit,
//        onFailure: (String) -> Unit
//    ) {
//        prefs.getUser()?.let { user ->
//            val bookmarks = if (user.bookmarkList.contains(articleId)) {
//                user.bookmarkList.apply { remove(articleId) }
//            } else {
//                user.bookmarkList.apply { add(articleId) }
//            }
//            onSuccess(bookmarks.contains(articleId))
//            firestore.collection(DatabaseCollection.BOOKMARKS).document(user.uid)
//                .set(user.copy(bookmarkList = bookmarks)).addOnSuccessListener {
//                    prefs.saveUser(user.copy(bookmarkList = bookmarks))
//                    onSuccess(bookmarks.contains(articleId))
//                }.addOnFailureListener {
//                    onFailure(it.message.toString())
//                }
//        }
//
//    }
}