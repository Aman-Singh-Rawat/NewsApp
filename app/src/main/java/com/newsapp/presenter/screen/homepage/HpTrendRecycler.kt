package com.newsapp.presenter.screen.homepage

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.newsapp.R
import com.newsapp.databinding.LayoutHomePageRecyclerBinding
import com.newsapp.presenter.screen.recentstories.RecentDataClass

class HpTrendRecycler(private val list: List<RecentDataClass>): RecyclerView.Adapter<HpTrendRecycler.HpTrendAdapter>() {
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
        holder.binding.imgHpNews.setImageResource(list[position].ivNewsImg)
        holder.binding.tvNewsHeading.text = list[position].tvHeadline
        holder.binding.includeHpItem.apply {
            imgChannelLogo.setImageResource(list[position].imgChannelLogo)
            tvChannelName.text = list[position].tvChannelName
            tvDaysAgo.text = list[position].tvDaysAgo
            tvTotalViews.text = list[position].tvTotalViews
            tvTotalComments.text = list[position].tvTotalComments
        }
    }
}