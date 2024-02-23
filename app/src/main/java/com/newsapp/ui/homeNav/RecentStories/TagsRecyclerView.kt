package com.newsapp.ui.homeNav.RecentStories

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.newsapp.R

class TagsRecyclerView(private val list: List<String>):
    RecyclerView.Adapter<TagsRecyclerView.TagsRecyclerAdapter>() {

    private var selectedIndex = 0

    inner class TagsRecyclerAdapter(itemView: View): RecyclerView.ViewHolder(itemView) {
        fun onBind(itemView: View, s: String, position: Int) {

            itemView.isSelected = selectedIndex == position
            val tvRecyclerAddTags: TextView = itemView.findViewById(R.id.tvRecyclerAddTags)
            tvRecyclerAddTags.text = s

            if (itemView.isSelected) {
                tvRecyclerAddTags.setTextColor(itemView.context.resources.getColor(R.color.white))
            } else {
                tvRecyclerAddTags.setTextColor(itemView.context.resources.getColor(R.color.black))
            }
            itemView.setOnClickListener {
                selectedIndex = position

                notifyDataSetChanged()
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TagsRecyclerAdapter {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.recent_stories, parent, false)

        return TagsRecyclerAdapter(view)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: TagsRecyclerAdapter, position: Int) {
        holder.onBind(holder.itemView, list[position], position)
    }
}