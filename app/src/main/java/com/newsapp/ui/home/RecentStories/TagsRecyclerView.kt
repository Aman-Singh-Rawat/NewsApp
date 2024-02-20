package com.newsapp.ui.home.RecentStories

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.newsapp.R

class TagsRecyclerView(private val list: List<String>):
    RecyclerView.Adapter<TagsRecyclerView.TagsRecyclerAdapter>() {
    class TagsRecyclerAdapter(itemView: View): RecyclerView.ViewHolder(itemView) {
        val tvRecyclerAddTags: TextView =
            itemView.findViewById(R.id.tvRecyclerAddTags)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TagsRecyclerAdapter {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.recent_stories, parent, false)

        return TagsRecyclerAdapter(view)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: TagsRecyclerAdapter, position: Int) {
        holder.tvRecyclerAddTags.text = list[position]
    }
}