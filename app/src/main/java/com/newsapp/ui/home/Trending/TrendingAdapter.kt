package com.newsapp.ui.home.Trending

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.newsapp.R


class TrendingAdapter : RecyclerView.Adapter<TrendingAdapter.ViewTrendingHolder>() {

    private var trendingList = mutableListOf<TrendingData>()


    fun updateUi(trending: List<TrendingData>) {
        this.trendingList = trending.toMutableList()
        notifyDataSetChanged()
    }

    class ViewTrendingHolder(private val ItemView: View) : RecyclerView.ViewHolder(ItemView) {

        fun onBind(itemView: View, trendingData: TrendingData) {
            val tv_News_Line = itemView.findViewById<TextView>(R.id.tv_News_Line)
            val iv_Trending_New = itemView.findViewById<ImageView>(R.id.iv_Trending_New)
            val tv_Channel_name = itemView.findViewById<TextView>(R.id.tv_Channel_name)
            val iv_Channel_Profile = itemView.findViewById<ImageView>(R.id.iv_Channel_Profile)
            val tv_Days_ago = itemView.findViewById<TextView>(R.id.tv_Days_ago)
            val tvSeens = itemView.findViewById<TextView>(R.id.tvSeens)
            val tv_Comment = itemView.findViewById<TextView>(R.id.tv_Comment)

            val context = ItemView.context

            tv_News_Line.text = trendingData.tv_News_Line
            iv_Trending_New.setImageDrawable(
                ContextCompat.getDrawable(
                    context,
                    trendingData.imageNews
                )
            )
            tv_Channel_name.text = trendingData.tv_Channel_name
            iv_Channel_Profile.setImageDrawable(
                ContextCompat.getDrawable(
                    context,
                    trendingData.imageProfile
                )
            )
            tv_Days_ago.text = trendingData.tv_Days_ago
            tvSeens.text = trendingData.tvSeens
            tv_Comment.text = trendingData.tv_Comment


        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewTrendingHolder {
        val list = LayoutInflater.from(parent.context)
            .inflate(R.layout.trending_recycle_item, parent, false)
        return ViewTrendingHolder(list)
    }

    override fun onBindViewHolder(holder: TrendingAdapter.ViewTrendingHolder, position: Int) {
        holder.onBind(holder.itemView, trendingList[position])
    }

    override fun getItemCount(): Int {
        return trendingList.size
    }




}