package com.newsapp.util

interface OnItemClickListener {
    fun onItemClick(articleId: String)
}

interface OnTextSelectedListener {
    fun onTextSelected(topic: String)
}