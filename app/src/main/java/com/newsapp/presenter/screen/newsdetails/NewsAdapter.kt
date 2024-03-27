package com.newsapp.presenter.screen.newsdetails

import android.annotation.SuppressLint
import android.graphics.Insets.add
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.newsapp.R

class NewsAdapter() : RecyclerView.Adapter<NewsAdapter.ViewHolder>() {
    private var newsList = mutableListOf<String>()

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        @SuppressLint("NotifyDataSetChanged")
        fun onBind(itemView: View, s: String, position: Int) {

            val tvRecyclerTags = itemView.findViewById<TextView>(R.id.tvRecyclerTags)
            tvRecyclerTags.text = s
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val list = LayoutInflater.from(parent.context).inflate(R.layout.tag_item, parent, false)
        return ViewHolder(list)
    }

    override fun getItemCount(): Int {
        return newsList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.onBind(holder.itemView, newsList[position], position)
    }

    @SuppressLint("NotifyDataSetChanged")
    fun update(getdata: List<String>) {
        this.newsList = getdata.toMutableList()
        notifyDataSetChanged()
    }
}