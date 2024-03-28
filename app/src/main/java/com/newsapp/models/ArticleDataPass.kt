package com.newsapp.models

import android.net.Uri
import java.io.Serializable

data class ArticleDataPass(
    val imageView: Uri,
    val title: String,
    val story: String

): Serializable
