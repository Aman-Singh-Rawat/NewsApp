package com.newsapp.presenter.screen.bookmark

import android.annotation.SuppressLint
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.newsapp.R
import com.newsapp.util.OnTextSelectedListener

class BottomSheetAdapter(private val listener: OnTextSelectedListener) : RecyclerView.Adapter<BottomSheetAdapter.ViewHolder>() {

    private var bottomSheet = mutableListOf<String>()

    @SuppressLint("NotifyDataSetChanged")
    fun update(bottomSheet: List<String>) {
        this.bottomSheet = bottomSheet.toMutableList()
        notifyDataSetChanged()
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvSaveName = itemView.findViewById<CheckBox>(R.id.tvSaveName)
        fun onBind(s: String) {
            tvSaveName.text = s
            itemView.setOnClickListener {
                if (tvSaveName.isChecked) {
                    listener.onTextSelected(s+"true")
                } else {
                    listener.onTextSelected(s+"false")
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val list = LayoutInflater.from(parent.context).inflate(R.layout.bottomsheet_recycle, parent, false)
        return ViewHolder(list)
    }

    override fun getItemCount(): Int {
        return bottomSheet.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.onBind(bottomSheet[position])
    }
}


