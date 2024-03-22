package com.newsapp.models

data class User(
    val uid: String = "",
    val userName: String? = "",
    val fullName: String? = "",
    val email: String? = "",
    val profile: String? = "",
    val bio: String? = "",
    val website: String? = "",
)
