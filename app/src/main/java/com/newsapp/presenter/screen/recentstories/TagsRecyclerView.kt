package com.newsapp.presenter.screen.recentstories

import android.annotation.SuppressLint
import android.content.Context
import android.content.res.ColorStateList
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.newsapp.R
import com.newsapp.util.OnTextSelectedListener

class TagsRecyclerView(private val listener: OnTextSelectedListener):
    RecyclerView.Adapter<TagsRecyclerView.TagsRecyclerAdapter>() {
    private var selectedIndex = 0
    private var list: MutableList<String> = mutableListOf()
    inner class TagsRecyclerAdapter(itemView: View): RecyclerView.ViewHolder(itemView) {

        fun onBind(itemView: View, s: String, position: Int) {
            itemView.isSelected = selectedIndex == position
            val tvRecyclerAddTags: TextView = itemView.findViewById(R.id.tvRecyclerAddTags)
            tvRecyclerAddTags.text = s

            if (itemView.isSelected) {
                tvRecyclerAddTags.setTextColor(itemView.context.resources.getColor(R.color.white))
                listener.onTextSelected(s)
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
    fun updateUi(list: List<String>,) {
        this.list = list.toMutableList()
        notifyDataSetChanged()
    }
}