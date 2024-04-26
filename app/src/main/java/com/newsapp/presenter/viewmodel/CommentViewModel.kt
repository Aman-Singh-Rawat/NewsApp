package com.newsapp.presenter.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore
import com.google.gson.Gson
import com.newsapp.data.models.User
import com.newsapp.presenter.screen.newsdetails.Comment
import com.newsapp.util.DatabaseCollection
import com.newsapp.util.SharedPrefsManager
class CommentViewModel(application: Application) : AndroidViewModel(application) {

    private val firestore by lazy { Firebase.firestore }
    private val prefs by lazy { SharedPrefsManager.getInstance(application.applicationContext) }
    var list: MutableList<Comment> = mutableListOf()
    private val gson by lazy { Gson() }
    fun setData(comment: String, articleId: String, onSuccess: () -> Unit) {
        prefs.getUser()?.let {user ->
            list.add(Comment(user.profile, user.userName, System.currentTimeMillis().toString(), comment))
        }
        firestore.collection(DatabaseCollection.COMMENTS).document(articleId)
            .set(mapOf("Comments" to list)).addOnSuccessListener {
                onSuccess.invoke()
            }
    }

    fun getComments(articleId: String, onSuccess: (List<Comment>) -> Unit) {
        firestore.collection(DatabaseCollection.COMMENTS).document(articleId)
            .get()
            .addOnSuccessListener { documentSnapshot ->
                if (documentSnapshot.exists()) {
                    val commentsMap = documentSnapshot.get("Comments") as? List<Map<String, Any>>
                    val comments = commentsMap?.map { commentMap ->
                        val profile = commentMap["authorProfile"] as? String ?: ""
                        val userName = commentMap["authorName"] as? String ?: ""
                        val timestamp = commentMap["postedAt"] as? String ?: ""
                        val commentText = commentMap["comment"] as? String ?: ""
                        Comment(profile, userName, timestamp, commentText)
                    } ?: emptyList()
                    list = comments.toMutableList()
                    onSuccess(comments)
                } else {
                    // Document doesn't exist, handle appropriately
                    onSuccess(emptyList())
                }
            }
            .addOnFailureListener { exception ->
                // Handle failure
            }
    }

}