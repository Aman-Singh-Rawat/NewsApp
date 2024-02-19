package com.newsapp.presenter.screen.auth.newsfeedrecycler.datamodel

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.newsapp.R

class NewsFeedRecycler(private val list: List<NewsFeedClass>): RecyclerView.Adapter<NewsFeedRecycler.NewsFeedAdapter>() {
    private var currentPosition = -1
    private var flag = false
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

        holder.itemView.isSelected = position == currentPosition
        holder.itemView.setOnClickListener {
            currentPosition = position
            notifyDataSetChanged()
        }
    }
}