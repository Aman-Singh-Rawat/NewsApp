package com.newsapp.presenter.screen.auth.newsfeedrecycler.datamodel

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.newsapp.R

class NewsFeedRecycler(private val context: Context ,private val list: List<NewsFeedClass>): RecyclerView.Adapter<NewsFeedRecycler.NewsFeedAdapter>() {
    private val selectedItems = mutableSetOf<Int>()
    class NewsFeedAdapter(itemView: View): RecyclerView.ViewHolder(itemView) {
        val imgNewsFeed: ImageView = itemView.findViewById(R.id.imgNewsFeed)
        val tvNewsFeed: TextView = itemView.findViewById(R.id.tvNewsFeed)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsFeedAdapter {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.recycler_news_feed,
                parent, false
            )
        return NewsFeedAdapter(view)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: NewsFeedAdapter, @SuppressLint("RecyclerView") position: Int) {
        holder.tvNewsFeed.text = list[position].communityName
        holder.imgNewsFeed.setImageResource(list[position].image)

        holder.itemView.isSelected = selectedItems.contains(position)

        // Handle item clicks
        holder.itemView.setOnClickListener {
            val currentPosition = holder.adapterPosition // Using adapterPosition to get current position

            if (selectedItems.contains(currentPosition)) {
                // Item is already selected, deselect it
                selectedItems.remove(currentPosition)
                holder.tvNewsFeed.setTextColor(ContextCompat.getColor(context, R.color.blue))
            } else {
                // Item is not selected, select it
                selectedItems.add(currentPosition)
                holder.tvNewsFeed.setTextColor(ContextCompat.getColor(context, R.color.black))
            }

            // Notify item change for clicked item to update its background
            notifyItemChanged(currentPosition)
        }

    }
}