package com.newsapp.ui.home.RecentStories

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.view.menu.MenuView.ItemView
import androidx.recyclerview.widget.RecyclerView
import com.google.android.gms.common.Feature
import com.newsapp.R

class FeatureAdapter : RecyclerView.Adapter<FeatureAdapter.ViewHolder>() {
    private var featureList = mutableListOf<String>()

    fun updateUi(feature: List<String>){
        this.featureList = feature.toMutableList()
    }

    inner class ViewHolder(ItemView: View) : RecyclerView.ViewHolder(ItemView) {
        fun onBind(itemView: View, s: String) {
            val tvRecyclerAddTags = itemView.findViewById<TextView>(R.id.tvRecyclerAddTags)
            tvRecyclerAddTags.text = s
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val list =
            LayoutInflater.from(parent.context).inflate(R.layout.recent_stories, parent, false)
        return ViewHolder(list)
    }

    override fun getItemCount(): Int {
        return featureList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.onBind(holder.itemView, featureList[position])
    }

}