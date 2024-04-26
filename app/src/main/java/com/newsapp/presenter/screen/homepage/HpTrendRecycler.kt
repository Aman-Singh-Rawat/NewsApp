package com.newsapp.presenter.screen.homepage

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.newsapp.R
import com.newsapp.data.models.Article
import com.newsapp.databinding.LayoutHomePageRecyclerBinding
import com.newsapp.presenter.screen.recentstories.RecentDataClass
import com.newsapp.util.calculateElapsedTime
import com.newsapp.util.glideImage

class HpTrendRecycler(): RecyclerView.Adapter<HpTrendRecycler.HpTrendAdapter>() {
    private var list: List<Article> = emptyList()
    private lateinit var context: Context
    inner class HpTrendAdapter(val binding: LayoutHomePageRecyclerBinding): RecyclerView.ViewHolder(binding.root) {}

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HpTrendAdapter {
        val binding = LayoutHomePageRecyclerBinding.inflate(
            LayoutInflater.from(parent.context), parent, false)
        return HpTrendAdapter(binding)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: HpTrendAdapter, position: Int) {
        holder.apply {
            glideImage(context, binding.imgHpNews, list[position].image)
            binding.tvNewsHeading.text = list[position].title
            binding.includeHpItem.apply {
                glideImage(context, imgChannelLogo, list[position].authorProfile, true)
                tvChannelName.text = list[position].authorName
                tvDaysAgo.text = calculateElapsedTime(list[position].time)
                tvTotalViews.text = list[position].userViewed.size.toString()
                tvTotalComments.text = list[position].comments.toString()
            }
        }
    }
    fun updateUi(list: List<Article>, context: Context) {
        this.list = list
        this.context = context
        notifyDataSetChanged()
    }
}