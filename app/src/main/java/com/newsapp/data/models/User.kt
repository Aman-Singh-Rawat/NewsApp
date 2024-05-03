package com.newsapp.data.models

data class User(
    val uid: String = "",
    val userName: String = "",
    val fullName: String = "",
    val email: String = "",
    val profile: String = "",
    val bio: String = "",
    val website: String = "",
    val bookmarkList: MutableList<String> = mutableListOf(),
    val bookmarkCategoryList: MutableList<String> = mutableListOf(),
    val followerList: MutableList<String> = mutableListOf(),
    val followingList: MutableList<String> = mutableListOf()
)
