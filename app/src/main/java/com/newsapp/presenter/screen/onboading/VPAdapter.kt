package com.newsapp.presenter.screen.onboading

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.newsapp.R

class VPAdapter(private val arrayList: ArrayList<ViewPagerItem>): RecyclerView.Adapter<VPAdapter.VPHolder>() {
    class VPHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val imgTrendingDemo: ImageView = itemView.findViewById(R.id.imgTrendingDemo)
        val tvFeatureTitle: TextView = itemView.findViewById(R.id.tvFeatureTitle)
        val tvFeatureDescription: TextView = itemView.findViewById(R.id.tvFeatureDescription)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VPHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.onboarding_viewpager_item, parent, false
            )

        return VPHolder(view)
    }

    override fun getItemCount(): Int {
        return arrayList.size
    }

    override fun onBindViewHolder(holder: VPHolder, position: Int) {
        holder.imgTrendingDemo.setImageResource(arrayList[position].img)
        holder.tvFeatureDescription.text = arrayList[position].featureDescription
        holder.tvFeatureTitle.text = arrayList[position].featureTitle
    }
}