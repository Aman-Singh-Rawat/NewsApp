package com.newsapp.presenter.screen.homepage

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.newsapp.R
import com.newsapp.presenter.screen.recentstories.RecentDataClass

class HpTrendRecycler(private val list: List<RecentDataClass>): RecyclerView.Adapter<HpTrendRecycler.HpTrendAdapter>() {
    inner class HpTrendAdapter(itemView: View): RecyclerView.ViewHolder(itemView) {
        val recyclerImage: ImageView = itemView.findViewById(R.id.imgHpNews)
        val tvNewsHeading: TextView = itemView.findViewById(R.id.tvNewsHeading)
        val view: View = itemView.findViewById(R.id.includeHpItem)
        val imgChannelLogo: ImageView = view.findViewById(R.id.imgChannelLogo)
        val tvChannelName: TextView = view.findViewById(R.id.tvChannelName)
        val tvDaysAgo: TextView = view.findViewById(R.id.tvDaysAgo)
        val tvTotalViews: TextView = view.findViewById(R.id.tvTotalViews)
        val tvTotalComments: TextView = view.findViewById(R.id.tvTotalComments)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HpTrendAdapter {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.layout_home_page_recycler, parent, false)
        return HpTrendAdapter(view)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: HpTrendAdapter, position: Int) {
        holder.recyclerImage.setImageResource(list[position].ivNewsImg)
        holder.imgChannelLogo.setImageResource(list[position].imgChannelLogo)
        holder.tvChannelName.text = list[position].tvChannelName
        holder.tvDaysAgo.text = list[position].tvDaysAgo
        holder.tvNewsHeading.text = list[position].tvHeadline
        holder.tvTotalViews.text = list[position].tvTotalViews
        holder.tvTotalComments.text = list[position].tvTotalComments
    }
}