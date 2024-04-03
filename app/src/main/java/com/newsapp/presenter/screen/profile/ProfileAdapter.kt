package com.newsapp.presenter.screen.profile

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.net.toUri
import androidx.navigation.NavController
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.newsapp.R
import com.newsapp.data.models.Article
import com.newsapp.databinding.RecentRecycleItemBinding
import com.newsapp.util.SharedPrefsManager

class ProfileAdapter(private var list: List<Article>, val context: Context):
    RecyclerView.Adapter<ProfileAdapter.NewsArticlesAdapter>() {
    private val prefs by lazy { SharedPrefsManager.getInstance(context) }
        private var navController: NavController? = null
    inner class NewsArticlesAdapter(val binding: RecentRecycleItemBinding): RecyclerView.ViewHolder(binding.root) {

    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsArticlesAdapter {
        val binding = RecentRecycleItemBinding.inflate(
            LayoutInflater.from(parent.context), parent, false)

        return NewsArticlesAdapter(binding)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: NewsArticlesAdapter, position: Int) {
        bindTheViews(holder,position)
    }
    private fun bindTheViews(holder: NewsArticlesAdapter, position: Int) {
        holder.itemView.setOnClickListener {
            navController?.navigate(R.id.fullDeatilsFragment)
        }
        holder.binding.tvHeadline.text = list[position].title
        glideImage(list[position].image, holder.binding.ivNewsImg)
        holder.binding.includeRecentItem.tvDaysAgo.text = calculateElapsedTime(list[position].time)
        Log.d("Timing", calculateElapsedTime(list[position].time))
//        holder.binding.ivNewsImg.setImageResource(list[position].ivNewsImg)
//
//        holder.binding.includeRecentItem.apply {
//            imgChannelLogo.setImageResource(list[position].imgChannelLogo)
//            tvDaysAgo.text = list[position].tvDaysAgo
//            tvTotalViews.text = list[position].tvTotalViews
//            tvTotalComments.text = list[position].tvTotalComments
//        }
    }
    private fun glideImage(image: String, imageView: ImageView ) {
        Glide.with(context)
            .load(image)
            .into(imageView)
    }
    private fun calculateElapsedTime(timestamp: Long): String {
        val currentTime = System.currentTimeMillis()
        val elapsedTimeMillis = currentTime - timestamp

        val seconds = elapsedTimeMillis / 1000
        val minutes = seconds / 60
        val hours = minutes / 60

        return when {
            hours > 0 -> "$hours hours ago"
            minutes > 0 -> "$minutes minutes ago"
            else -> "$seconds seconds ago"
        }
    }
}
