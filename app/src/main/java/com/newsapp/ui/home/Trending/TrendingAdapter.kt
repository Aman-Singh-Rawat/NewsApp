package com.newsapp.ui.home.Trending

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.newsapp.R


class TrendingAdapter:RecyclerView.Adapter<TrendingAdapter.ViewTrendingHolder>(){

    private var trendingList = mutableListOf<TrendingDataClass>()


     fun updateUi(trending: List<TrendingDataClass>){
        this.trendingList = trending.toMutableList()
        notifyDataSetChanged()
    }
    class ViewTrendingHolder(ItemView : View):RecyclerView.ViewHolder(ItemView) {
        fun onBind(itemView: View, trendingDataClass: TrendingDataClass) {

        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewTrendingHolder {
        val list = LayoutInflater.from(parent.context).inflate(R.layout.trending_recycle_item,parent,false)
        return ViewTrendingHolder(list)
    }

    override fun getItemCount(): Int {
        return trendingList.size
    }

    override fun onBindViewHolder(holder: ViewTrendingHolder, position: Int) {
        holder.onBind(holder.itemView,trendingList[position])
    }


}