package com.newsapp.presenter.screen.bookmark

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.newsapp.databinding.RecentStoriesBinding

class BookmarkTagsAdapter(private val list: List<String>): RecyclerView.Adapter<BookmarkTagsAdapter.BookmarkViewHolder>() {
    inner class BookmarkViewHolder(val binding: RecentStoriesBinding): RecyclerView.ViewHolder(binding.root) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookmarkViewHolder {
        val binding = RecentStoriesBinding.inflate(LayoutInflater.from(parent.context),
            parent, false)

        return BookmarkViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: BookmarkViewHolder, position: Int) {
        TODO("Not yet implemented")
    }
}