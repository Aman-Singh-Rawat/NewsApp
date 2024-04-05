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

class TagsRecyclerView(private val list: List<String>,
                       private val listener: OnTextSelectedListener):
    RecyclerView.Adapter<TagsRecyclerView.TagsRecyclerAdapter>() {
    private var selectedIndex = 0
    private var flag = false
    private lateinit var context: Context
    private lateinit var findNavController: NavController
    constructor(list: List<String>, flag:Boolean, context: Context,
                findNavController: NavController, listener: OnTextSelectedListener): this(list, listener) {
        this.flag = flag
        this.context = context
        this.findNavController = findNavController
    }

    inner class TagsRecyclerAdapter(itemView: View): RecyclerView.ViewHolder(itemView) {

        fun onBind(itemView: View, s: String, position: Int) {
            itemView.isSelected = selectedIndex == position
            val tvRecyclerAddTags: TextView = itemView.findViewById(R.id.tvRecyclerAddTags)
            val fabBtn: FloatingActionButton = itemView.findViewById(R.id.fabButton)
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
            if (flag && position == 0) {
                fabBtn.visibility = View.VISIBLE
                val color = ContextCompat.getColor(context, R.color.white)
                fabBtn.imageTintList = ColorStateList.valueOf(color)
            }
            fabBtn.setOnClickListener {
                findNavController.navigate(R.id.collectionBottomFragment)
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