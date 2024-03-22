package com.newsapp.presenter.screen.onboading

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.newsapp.R

class OnboardingAdapter(private val arrayList: List<OnboardingItem>): RecyclerView.Adapter<OnboardingAdapter.OnboardingViewHolder>() {
    class OnboardingViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val imgTrendingDemo: ImageView = itemView.findViewById(R.id.imgTrendingDemo)
        val tvFeatureTitle: TextView = itemView.findViewById(R.id.tvFeatureTitle)
        val tvFeatureDescription: TextView = itemView.findViewById(R.id.tvFeatureDescription)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OnboardingViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.onboarding_viewpager_item, parent, false
            )

        return OnboardingViewHolder(view)
    }

    override fun getItemCount(): Int {
        return arrayList.size
    }

    override fun onBindViewHolder(holder: OnboardingViewHolder, position: Int) {
        holder.imgTrendingDemo.setImageResource(arrayList[position].img)
        holder.tvFeatureDescription.text = arrayList[position].featureDescription
        holder.tvFeatureTitle.text = arrayList[position].featureTitle
    }
}