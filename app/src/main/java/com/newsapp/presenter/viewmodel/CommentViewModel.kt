package com.newsapp.presenter.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore
import com.google.firebase.firestore.toObject
import com.newsapp.presenter.screen.newsdetails.Comment
import com.newsapp.util.DatabaseCollection
import com.newsapp.util.SharedPrefsManager

class CommentViewModel(application: Application) : AndroidViewModel(application) {

    private val firestore by lazy { Firebase.firestore }
    private val prefs by lazy { SharedPrefsManager.getInstance(application.applicationContext) }
    private val comments = MutableLiveData<List<Comment>>(emptyList())

    fun postComment(comment: String, articleId: String) {
        if (comment.isNotBlank() && comment.isNotEmpty()) {
            comments.value?.toMutableList().let { comments ->
                prefs.getUser()?.let { user ->
                    comments?.add(
                        Comment(
                            authorName = user.fullName,
                            authorProfile = user.profile,
                            postedAt = System.currentTimeMillis().toString(),
                            comment = comment
                        )
                    )
                    comments?.let { firestore.collection(DatabaseCollection.COMMENTS).document(articleId).set(it) }
                }
            }
        }
    }

    fun getComments(articleId: String) {
        firestore.collection(DatabaseCollection.COMMENTS).document(articleId).get()
            .addOnCompleteListener {
                try {
                    comments.postValue(it.result.toObject())
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
    }


}