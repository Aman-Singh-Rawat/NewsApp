package com.newsapp.presenter.screen.newsdetails

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.newsapp.databinding.RecentStoriesBinding

class TagsAdapter(private val list: List<String>): RecyclerView.Adapter<TagsAdapter.TagsViewHolder>() {
    inner class TagsViewHolder(val binding: RecentStoriesBinding): RecyclerView.ViewHolder(binding.root) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TagsViewHolder {
        val binding = RecentStoriesBinding.inflate(LayoutInflater.from(parent.context),
            parent, false)

        return TagsViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: TagsViewHolder, position: Int) {
       holder.binding.tvRecyclerAddTags.text = list[position]
    }
}