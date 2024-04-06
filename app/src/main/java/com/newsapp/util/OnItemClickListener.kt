package com.newsapp.util

interface OnItemClickListener {
    fun onItemClick(articleId: String)
    fun onArticleSaveListener(selectedItems: MutableList<String>)
}

interface OnTextSelectedListener {
    fun onTextSelected(topic: String)
}