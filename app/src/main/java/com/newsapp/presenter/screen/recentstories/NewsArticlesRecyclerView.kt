package com.newsapp.presenter.screen.recentstories

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.navigation.NavController
import androidx.recyclerview.widget.RecyclerView
import com.newsapp.R
import com.newsapp.databinding.RecentRecycleItemBinding

class NewsArticlesRecyclerView(private var list: List<RecentDataClass>):
    RecyclerView.Adapter<NewsArticlesRecyclerView.NewsArticlesAdapter>() {
        private var navController: NavController? = null
    inner class NewsArticlesAdapter(val binding: RecentRecycleItemBinding): RecyclerView.ViewHolder(binding.root) {

    }
    constructor(navController: NavController, list: List<RecentDataClass>) : this(list) {
        this.navController = navController
        this.list = list
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsArticlesAdapter {
        val binding = RecentRecycleItemBinding.inflate(
            LayoutInflater.from(parent.context), parent, false)

        return NewsArticlesAdapter(binding)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: NewsArticlesAdapter, position: Int) {
        bindTheViews(holder,position)
    }
    private fun bindTheViews(holder: NewsArticlesAdapter, position: Int) {
        holder.itemView.setOnClickListener {
            navController?.navigate(R.id.fullDeatilsFragment)
        }
        holder.binding.tvHeadline.text = list[position].tvHeadline
        holder.binding.ivNewsImg.setImageResource(list[position].ivNewsImg)

        holder.binding.includeRecentItem.apply {
            imgChannelLogo.setImageResource(list[position].imgChannelLogo)
            tvDaysAgo.text = list[position].tvDaysAgo
            tvTotalViews.text = list[position].tvTotalViews
            tvTotalComments.text = list[position].tvTotalComments
        }
    }
}
