package com.newsapp.presenter.viewmodel

import android.app.Application
import android.net.Uri
import androidx.lifecycle.AndroidViewModel
import com.newsapp.data.models.Article

class CreateArticleViewModel(private val application: Application) : AndroidViewModel(application) {

    private var currentArticle: Article? = null

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
        //TODO upload uri to firebase and return url of the uploaded image
        return ""
    }

    fun clearArticleData() {
        currentArticle = null
    }
}