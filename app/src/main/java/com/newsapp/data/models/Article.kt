package com.newsapp.data.models

data class Article(
    val articleId: String = "",
    val image: String = "",
    val title: String = "",
    val story: String = "",
    val topic: String = "",
    val tags: List<String> = emptyList(),
    val time: Long = 0L,
    val userViewed: List<String> = emptyList(),
    val comments: Long = 0L,
    val authorId: String = "",
    val authorName: String = "",
    val authorProfile: String = "",
    val saveArticleList: String = ""
)