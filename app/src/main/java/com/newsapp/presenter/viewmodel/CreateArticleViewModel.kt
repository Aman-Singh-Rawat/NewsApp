package com.newsapp.presenter.viewmodel

import android.app.Application
import android.net.Uri
import androidx.lifecycle.AndroidViewModel
import com.google.firebase.Firebase
import com.google.firebase.storage.storage
import com.newsapp.data.models.Article

class CreateArticleViewModel(private val application: Application) : AndroidViewModel(application) {

    private var currentArticle: Article? = null
    private var storageRef = Firebase.storage
    private var imageUri: Uri? = null
    fun getArticle(): Article? {
        return currentArticle
    }

    fun addArticle(imagePath: String, title: String, story: String) {
        currentArticle = Article(image = imagePath, title = title, story = story)
    }

    fun publishArticle(topic: String, tags: List<String>) {
        currentArticle = currentArticle?.copy(topic = topic, tags = tags)
        //TODO upload all data to firebase store
        //TODO use articles collection
    }

    fun uploadImageToFirebase(uri: Uri):String{
        storageRef.getReference("images").child(System.currentTimeMillis().toString())
            .putFile(uri)
            .addOnSuccessListener { task ->
                task.metadata!!.reference!!.downloadUrl
                    .addOnSuccessListener {
                        imageUri = it
                    }
            }
        return imageUri.toString()
    }

    fun clearArticleData() {
        currentArticle = null
    }
}