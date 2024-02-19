package com.newsapp.ui.home.RecentStories

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.newsapp.R

class RecentAdapter:RecyclerView.Adapter<RecentAdapter.RecentViewHolder>() {
    private var recentList = mutableListOf<RecentData>()

    fun update(recent : List<RecentData>){
        this.recentList = recent.toMutableList()
    }
    class RecentViewHolder(itemView: View):RecyclerView.ViewHolder(itemView) {
        fun onBind(itemView: View, recentData: RecentData) {
            val tvHeadline = itemView.findViewById<TextView>(R.id.tvHeadline)
            val ivNewsImg = itemView.findViewById<ImageView>(R.id.ivNewsImg)
            val tvNameChannel = itemView.findViewById<TextView>(R.id.tvNameChannel)
            val imgProfile = itemView.findViewById<ImageView>(R.id.imgProfile)
            val tvTime = itemView.findViewById<TextView>(R.id.tvTime)
            val tvEyes = itemView.findViewById<TextView>(R.id.tvEyes)
            val tvComment = itemView.findViewById<TextView>(R.id.tvComment)

            val context = itemView.context

            tvHeadline.text = recentData.tvHeadline
            ivNewsImg.setImageDrawable(
                ContextCompat.getDrawable(
                    context,
                    recentData.ivNewsImg
                )
            )
            tvNameChannel.text = recentData.tvNameChannel
            imgProfile.setImageDrawable(
                ContextCompat.getDrawable(
                    context,
                    recentData.imgProfile
                )
            )
            tvTime.text = recentData.tvTime
            tvEyes.text = recentData.tvEyes
            tvComment.text = recentData.tvComment


        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecentViewHolder {
       val list = LayoutInflater.from(parent.context).inflate(R.layout.recent_recycle_item,parent,false)
        return RecentViewHolder(list)
    }

    override fun getItemCount(): Int {
       return recentList.size
    }

    override fun onBindViewHolder(holder: RecentViewHolder, position: Int) {
       holder.onBind(holder.itemView,recentList[position])
    }
}