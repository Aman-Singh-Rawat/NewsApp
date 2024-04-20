package com.newsapp.presenter.viewmodel

import android.app.Application
import android.net.Uri
import androidx.lifecycle.AndroidViewModel
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore
import com.google.firebase.storage.FirebaseStorage
import com.google.gson.Gson
import com.newsapp.data.models.Article
import com.newsapp.util.DatabaseCollection
import com.newsapp.util.SharedPrefsManager

class ViewModelProfile(private val application: Application): AndroidViewModel(application) {
    private val prefs by lazy { SharedPrefsManager.getInstance(application.applicationContext) }
    private var storageRef = FirebaseStorage.getInstance().reference.child("Images")
    private val firestore by lazy { Firebase.firestore }
    private val gson by lazy { Gson() }

    fun updateUserProfile(fullName: String, userName: String, bio: String, website: String) {
        val currentUser = prefs.getUser()
        if (currentUser != null) {
            val user = currentUser.copy(fullName = fullName, userName = userName, bio = bio, website = website)
            prefs.saveUser(user)
            val updateMap = mapOf(
                "bio" to user.bio,
                "email" to user.email,
                "fullName" to user.fullName,
                "profile" to user.profile,
                "userName" to user.userName,
                "website" to user.website
            )
            firestore.collection(DatabaseCollection.USERS).document(user.uid).update(updateMap).addOnSuccessListener {
                articleDataUpdate()
            }
        }
    }

    fun articleDataUpdate(onSuccess: (List<Article>) -> Unit) {
        firestore.collection(DatabaseCollection.ARTICLES).get()
            .addOnSuccessListener {
                if (!it.isEmpty) {
                    val articles = it.documents.map {
                        val json = gson.toJson(it.data)
                        gson.fromJson(json, Article::class.java)
                    }.filter { it.authorId == prefs.getUser()?.uid }
                    onSuccess(articles)
                }
            }
            .addOnFailureListener {
            }
    }

    private fun articleDataUpdate() {
        val batch = firestore.batch()
        firestore.collection(DatabaseCollection.ARTICLES)
            .whereEqualTo("authorId", prefs.getUser()?.uid)
            .get()
            .addOnSuccessListener { documents ->
                for (document in documents) {
                    val articleRef = document.reference
                    batch.update(articleRef, mapOf(
                        "authorName" to prefs.getUser()?.userName,
                        "authorProfile" to prefs.getUser()?.profile

                    ))
                }
                batch.commit().addOnSuccessListener {
                    // Articles updated successfully
                }
            }
    }

    fun updateUserProfile(imageUri: Uri) {
        /*var currentUser = prefs.getUser()
        if (currentUser != null) {
            val user = currentUser.copy(profile = imageUri)
        }
        prefs.saveUser(user)
        firestore.collection(DatabaseCollection.users).document()*/
    }
    fun updateUserProfile(imageUri: Uri, onSuccess: () -> Unit) {
        val storageRef = storageRef.child(System.currentTimeMillis().toString())
        val uploadTask = storageRef.putFile(imageUri)

        uploadTask.continueWithTask { task ->
            if (!task.isSuccessful) {
                task.exception?.let {
                    throw it
                }
            }
            storageRef.downloadUrl
        }.addOnCompleteListener { task ->
            if (task.isSuccessful) {
                val downloadUri = task.result.toString()
                val currentUser = prefs.getUser()
                if (currentUser != null) {
                    val user = currentUser.copy(profile = downloadUri)
                    prefs.saveUser(user)
                    onSuccess()
                }

            } else {
                //callback(null)
            }
        }
    }


}
