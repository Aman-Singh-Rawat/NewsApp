package com.newsapp.ui.home.RecentStories

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.newsapp.R

class NewsArticlesRecyclerView(private val list: List<RecentDataClass>):
    RecyclerView.Adapter<NewsArticlesRecyclerView.NewsArticlesAdapter>() {
    class NewsArticlesAdapter(itemView: View): RecyclerView.ViewHolder(itemView) {
        val tvHeadline: TextView = itemView.findViewById(R.id.tvHeadline)
        val ivNewsImg: ImageView = itemView.findViewById(R.id.ivNewsImg)
        val view: View = itemView.findViewById(R.id.includeRecentItem)
        val imgChannelLogo: ImageView = view.findViewById(R.id.imgChannelLogo)
        val tvChannelName: TextView = view.findViewById(R.id.tvChannelName)
        val tvDaysAgo: TextView = view.findViewById(R.id.tvDaysAgo)
        val tvTotalViews: TextView = view.findViewById(R.id.tvTotalViews)
        val tvTotalComments: TextView = view.findViewById(R.id.tvTotalComments)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsArticlesAdapter {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.recent_recycle_item, parent, false)

        return NewsArticlesAdapter(view)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: NewsArticlesAdapter, position: Int) {
        bindTheViews(holder,position)
    }
    private fun bindTheViews(holder: NewsArticlesAdapter, position: Int) {
        holder.tvHeadline.text = list[position].tvHeadline
        holder.ivNewsImg.setImageResource(list[position].ivNewsImg)
        holder.imgChannelLogo.setImageResource(list[position].imgChannelLogo)
        holder.tvChannelName.text = list[position].tvChannelName
        holder.tvDaysAgo.text = list[position].tvDaysAgo
        holder.tvTotalViews.text = list[position].tvTotalViews
        holder.tvTotalComments.text = list[position].tvTotalComments
    }
}
