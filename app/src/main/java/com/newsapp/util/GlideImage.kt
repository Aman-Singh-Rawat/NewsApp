package com.newsapp.util

import android.content.Context
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.newsapp.R

fun glideImage(context: Context, imageView: ImageView, imageUrl: String, flag: Boolean  = false) {
    val image: Int = if (flag)
        R.drawable.ic_profile_signin
    else
        R.drawable.ic_image

    Glide.with(context)
        .load(imageUrl)
        .centerCrop()
        .placeholder(image)
        .into(imageView)
}