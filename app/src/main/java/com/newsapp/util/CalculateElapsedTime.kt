package com.newsapp.util

fun calculateElapsedTime(timestamp: Long): String {
    val currentTime = System.currentTimeMillis()
    val elapsedTimeMillis = currentTime - timestamp

    val seconds = elapsedTimeMillis / 1000
    val minutes = seconds / 60
    val hours = minutes / 60
    val day = hours / 24
    val week = day / 7

    //Add days Ago and months ago
    return when {
        week > 0 -> "$week week ago"
        day > 0 -> "$day day ago"
        hours > 0 -> "$hours hours ago"
        minutes > 0 -> "$minutes minutes ago"
        else -> "$seconds seconds ago"
    }
}