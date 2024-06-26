package com.newsapp.presenter.viewmodel

import android.app.Application
import android.net.Uri
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore
import com.google.firebase.firestore.toObject
import com.google.firebase.storage.FirebaseStorage
import com.google.gson.Gson
import com.newsapp.data.models.Article
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
            currentArticle = currentArticle?.copy(articleId = articleId, topic = topic, tags = tags, authorId = user.uid, authorName = user.userName, authorProfile = user.profile, authorDescription = user.bio)
            currentArticle?.let { article ->
                firestore.collection(DatabaseCollection.ARTICLES).document(articleId).set(article)
                    .addOnSuccessListener { documentReference ->

                    }.addOnFailureListener { e ->

                    }
            }
        }
    }
    fun updateArticle(topic: String, tags: List<String>, articleId: String, onSuccess: () -> Unit, onError: () -> Unit) {
        currentArticle = currentArticle?.copy(topic = topic, tags = tags)
        currentArticle?.let { article ->
            val userMap = mapOf(
                "image" to article.image,
                "story" to article.story,
                "tags" to article.tags,
                "topic" to article.topic,
                "title" to article.title
            )
            firestore.collection(DatabaseCollection.ARTICLES).document(articleId)
                .update(userMap).addOnSuccessListener {
                    onSuccess.invoke()
                }
                .addOnFailureListener {
                    onError.invoke()
                }
        }

    }

    fun uploadImageToFirebase(flag: Boolean, onSuccess: () -> Unit) {
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
                currentArticle = if(flag)
                    currentArticle?.copy(image = downloadUri)
                else
                    currentArticle?.copy(image = downloadUri, time = System.currentTimeMillis())

                onSuccess()
            } else {
            }
        }.addOnFailureListener {
        }
    }
    fun getArticleData(onSuccess: (List<Article>) -> Unit) {
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

    fun articleUpdate(articleId: String) {

    }
    fun deleteArticle(articleId: String, onSuccess: () -> Unit) {
        firestore.collection(DatabaseCollection.ARTICLES).document(articleId).get()
            .addOnSuccessListener {
                val article = it.toObject(Article::class.java)
                if (article != null) {
                    firestore.collection(DatabaseCollection.ARTICLES).document(articleId).delete()
                        .addOnSuccessListener {
                            storageRef.child("Images/${article.image}")
                                .delete()
                                .addOnSuccessListener {
                                    onSuccess.invoke()
                                }
                                .addOnFailureListener {
                                }
                        }
                }
            }
    }
    fun getParticularArticle(articleId: String, onSuccess: (Article) -> Unit) {
        firestore.collection(DatabaseCollection.ARTICLES).document(articleId)
            .get()
            .addOnSuccessListener {
                val article = it.toObject(Article::class.java)
                if (article != null) {
                    onSuccess(article)
                }
            }
    }
    fun clearArticleData() {
        currentArticle = null
    }
}