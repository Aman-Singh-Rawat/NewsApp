package com.newsapp.util

import android.widget.ImageView
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.newsapp.R

fun Fragment.glideImage(imageView: ImageView, imageUrl: String) {
    Glide.with(requireContext())
        .load(imageUrl)
        .centerCrop()
        .placeholder(R.drawable.ic_image)
        .into(imageView)
}