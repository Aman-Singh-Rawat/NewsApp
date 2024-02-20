package com.newsapp.ui.home.Bookmark

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.newsapp.R

class BottomSheetAdapter : RecyclerView.Adapter<BottomSheetAdapter.ViewHolder>() {

    private var bottomSheet = mutableListOf<String>()
    @SuppressLint("NotifyDataSetChanged")
    fun update(bottomSheet: List<String>) {
        this.bottomSheet = bottomSheet.toMutableList()
        notifyDataSetChanged()
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun onBind(itemView: View,s: String) {
            val tvSaveName = itemView.findViewById<TextView>(R.id.tvSaveName)

            tvSaveName.text = s

        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val list =
            LayoutInflater.from(parent.context).inflate(R.layout.bottomsheet_recycle, parent, false)
        return ViewHolder(list)
    }

    override fun getItemCount(): Int {
        return bottomSheet.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.onBind(holder.itemView, bottomSheet[position])
    }
}

