package com.newsapp.presenter.screen.auth.interest

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
import com.newsapp.data.models.NewsInterest

class NewsInterestAdapter(private val context: Context, private val itemList: List<NewsInterest>) :
    RecyclerView.Adapter<NewsInterestAdapter.NewsInterestViewHolder>() {

    private val selectedItems = mutableListOf<NewsInterest>()

    inner class NewsInterestViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val imgNewsFeed: ImageView = itemView.findViewById(R.id.imgNewsFeed)
        private val tvNewsFeed: TextView = itemView.findViewById(R.id.tvNewsFeed)

        @SuppressLint("NotifyDataSetChanged")
        fun bind(newsInterest: NewsInterest, currentPosition: Int) {
            tvNewsFeed.text = newsInterest.interestName
            imgNewsFeed.setImageResource(newsInterest.image)
            val isSelected = selectedItems.contains(newsInterest)
            itemView.isSelected = isSelected
            if (isSelected) {
                tvNewsFeed.setTextColor(ContextCompat.getColor(context, R.color.blue))
            } else {
                tvNewsFeed.setTextColor(ContextCompat.getColor(context, R.color.black))
            }
            itemView.setOnClickListener {
                if (selectedItems.contains(newsInterest)) {
                    selectedItems.remove(newsInterest)
                } else {
                    selectedItems.add(newsInterest)
                }
                notifyDataSetChanged()
            }
        }
    }

    fun getSelectedInterest(): MutableList<NewsInterest> {
        return selectedItems
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsInterestViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(
            R.layout.recycler_news_feed, parent, false
        )
        return NewsInterestViewHolder(view)
    }

    override fun getItemCount(): Int {
        return itemList.size
    }

    override fun onBindViewHolder(
        holder: NewsInterestViewHolder, position: Int
    ) {
        holder.bind(itemList[position], position)
    }

    fun setSelectedInterest(interests: List<NewsInterest>) {
        selectedItems.clear()
        selectedItems.addAll(interests)
        notifyDataSetChanged()
    }
}