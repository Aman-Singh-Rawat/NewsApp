package com.newsapp.util

import android.widget.ImageView
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide

fun Fragment.glideImage(imageView: ImageView, imageUrl: String) {
    Glide.with(requireActivity())
        .load(imageUrl)
        .into(imageView)
}