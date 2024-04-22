package com.newsapp.presenter.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore
import com.newsapp.util.DatabaseCollection
import com.newsapp.util.SharedPrefsManager

class BookmarkViewModel(application: Application): AndroidViewModel(application) {
    private val bookmarkList: MutableList<String> = mutableListOf()
    private val prefs by lazy { SharedPrefsManager.getInstance(application.applicationContext) }
    private val firestore by lazy { Firebase.firestore }
    fun saveBookmarkList(item: String, onSuccess: () -> Unit) {
        bookmarkList.add("All")
        bookmarkList.add(item)
        prefs.getUser()?.let { user ->
            firestore.collection(DatabaseCollection.BOOKMARK).document(user.uid)
                .set(mapOf("bookmark" to bookmarkList))
                .addOnSuccessListener {
                    onSuccess.invoke()
                }
        }
    }

    fun getBookmarkList(onSuccess: (MutableList<String>) -> Unit) {
        prefs.getUser()?.let { user ->
            firestore.collection(DatabaseCollection.BOOKMARK).document(user.uid)
                .get().addOnSuccessListener {documentSnap ->
                    if (documentSnap.exists()) {
                        val bookmarkData = documentSnap.data
                        val bookmarkStringList = bookmarkData?.get("bookmark") as? List<String>

                        bookmarkStringList?.let {
                            bookmarkList.clear()
                            bookmarkList.addAll(it)
                            onSuccess(bookmarkList)
                        }
                    }
                }
        }
    }
}