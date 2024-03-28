package com.newsapp.presenter.viewmodel

import android.app.Application
import android.net.Uri
import androidx.lifecycle.AndroidViewModel
import com.google.firebase.storage.FirebaseStorage
import com.newsapp.data.models.Article

class CreateArticleViewModel(private val application: Application) : AndroidViewModel(application) {

    private var currentArticle: Article? = null
    private var storageRef = FirebaseStorage.getInstance().reference.child("Images")
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

    fun uploadImageToFirebase(uri: Uri, callback: (String?) -> Unit) {
        val storageRef = storageRef.child(System.currentTimeMillis().toString())
        val uploadTask = storageRef.putFile(uri)

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
                callback(downloadUri)
            } else {
                callback(null)
            }
        }
    }

    fun clearArticleData() {
        currentArticle = null
    }
}