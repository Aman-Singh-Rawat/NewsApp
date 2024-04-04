package com.newsapp.presenter.viewmodel

import android.app.Application
import android.net.Uri
import androidx.lifecycle.AndroidViewModel
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore
import com.google.firebase.storage.FirebaseStorage
import com.google.gson.Gson
import com.newsapp.data.models.Article
import com.newsapp.data.models.User
import com.newsapp.util.DatabaseCollection
import com.newsapp.util.SharedPrefsManager
import java.util.UUID

class CreateArticleViewModel(private val application: Application) : AndroidViewModel(application) {

    private var currentArticle: Article? = null
    private var storageRef = FirebaseStorage.getInstance().reference.child("Images")
    var imageUri: Uri? = null
    private val prefs by lazy { SharedPrefsManager.getInstance(application.applicationContext) }
    private val firestore by lazy { Firebase.firestore }
    private val gson by lazy { Gson() }

    fun getArticle(): Article? {
        return currentArticle
    }

    fun addArticle(imagePath: String, title: String, story: String) {
        currentArticle = Article(image = imagePath, title = title, story = story)
    }

    fun publishArticle(topic: String, tags: List<String>) {
        prefs.getUser()?.let { user ->
            val articleId = UUID.randomUUID().toString()
            currentArticle = currentArticle?.copy(articleId = articleId, topic = topic, tags = tags, authorId = user.uid, authorName = user.userName, authorProfile = user.profile)
            currentArticle?.let { article ->
                firestore.collection(DatabaseCollection.articles).document(articleId).set(article)
                    .addOnSuccessListener { documentReference ->

                    }.addOnFailureListener { e ->

                    }
            }
        }
    }

    fun uploadImageToFirebase(onSuccess: () -> Unit) {
        val storageRef = storageRef.child(System.currentTimeMillis().toString())
        val uploadTask = storageRef.putFile(imageUri!!)

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
                currentArticle = currentArticle?.copy(image = downloadUri, time = System.currentTimeMillis())
                onSuccess()
            } else {
                //callback(null)
            }
        }
    }
    fun getArticleData(onSuccess: (List<Article>) -> Unit) {
        firestore.collection(DatabaseCollection.articles).get()
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
    fun getSelectedData(onSuccess: (List<Article>) -> Unit) {
        firestore.collection(DatabaseCollection.articles).get()
            .addOnSuccessListener {
                if (!it.isEmpty) {
                    val articles = it.documents.map {
                        val json = gson.toJson(it.data)
                        gson.fromJson(json, Article::class.java)
                    }.filter { it.topic == prefs.getUser()?.uid }
                    onSuccess(articles)
                }
            }
            .addOnFailureListener {
            }
    }
    fun clearArticleData() {
        currentArticle = null
    }
}