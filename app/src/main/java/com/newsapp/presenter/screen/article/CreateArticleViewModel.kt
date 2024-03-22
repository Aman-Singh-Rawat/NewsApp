package com.newsapp.presenter.screen.article

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.newsapp.models.Article

class CreateArticleViewModel(private val application: Application) : AndroidViewModel(application) {

    private var currentArticle: Article = Article()

    fun getArticle(): Article {
        return currentArticle
    }

    fun addArticle(imagePath: String, title: String, story: String) {
        currentArticle = currentArticle.copy(image = imagePath, title = title, story = story)
    }


}