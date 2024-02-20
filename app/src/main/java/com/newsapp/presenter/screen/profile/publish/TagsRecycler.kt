package com.newsapp.presenter.screen.profile.publish

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.newsapp.R

class TagsRecycler(private val list: List<String>): RecyclerView.Adapter<TagsRecycler.TagsAdapter>() {
    class TagsAdapter(itemView: View): RecyclerView.ViewHolder(itemView) {
        val tvRecyclerAddTags: TextView = itemView.
        findViewById(R.id.tvRecyclerAddTags)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TagsAdapter {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.recent_stories, parent, false)

        return TagsAdapter(view)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: TagsAdapter, position: Int) {
        holder.tvRecyclerAddTags.text = list[position]
    }
}