package com.newsapp.presenter.viewmodel

import android.app.Application
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

    fun addTopicAndTag(topic: String, tags: List<String>) {
        currentArticle = currentArticle?.copy(topic = topic, tags = tags)
    }

    fun publishArticle(){
        //TODO : STORE DATA IN FIREBASE
    }

    fun clearArticleData() {
        currentArticle = null
    }
}