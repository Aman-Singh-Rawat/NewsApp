package com.newsapp.data.models

data class Article(
    val image: String = "",
    val title: String = "",
    val story: String = "",
    val topic: String = "",
    val tags: List<String> = emptyList(),
)