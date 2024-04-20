package com.newsapp.presenter.screen.profile

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.newsapp.R
import com.newsapp.data.models.Article
import com.newsapp.databinding.RecentRecycleItemBinding
import com.newsapp.util.OnItemClickListener
import com.newsapp.util.calculateElapsedTime

class ProfileAdapter(private var list: List<Article>, val context: Context, private val listener:
OnItemClickListener): RecyclerView.Adapter<ProfileAdapter.NewsArticlesAdapter>() {

    private val selectedItems = mutableListOf<String>()
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
    @SuppressLint("NotifyDataSetChanged")
    private fun bindTheViews(holder: NewsArticlesAdapter, position: Int) {
        val isSelected = selectedItems.contains(list[position].articleId)
        holder.itemView.isSelected = isSelected

        holder.binding.tvHeadline.setOnClickListener {
            listener.onItemClick(list[position].articleId)
        }
        holder.binding.cvNewsImg.setOnClickListener {
            listener.onItemClick(list[position].articleId)
        }
        holder.binding.includeRecentItem.icSave.setOnClickListener {
            if (selectedItems.contains(list[position].articleId)) {
                selectedItems.remove(list[position].articleId)
                listener.onArticleSaveListener(selectedItems)
            } else {
                selectedItems.add(list[position].articleId)
                listener.onArticleSaveListener(selectedItems)
            }
            notifyDataSetChanged()
        }
        holder.binding.tvHeadline.text = list[position].title
        glideImage(list[position].image, holder.binding.ivNewsImg)
        glideImage(list[position].authorProfile, holder.binding.includeRecentItem.imgChannelLogo)
        holder.binding.includeRecentItem.tvTotalViews.text = list[position].userViewed.size.toString()
        holder.binding.includeRecentItem.tvChannelName.text = list[position].authorName
        holder.binding.includeRecentItem.tvDaysAgo.text = calculateElapsedTime(list[position].time)
    }
    private fun glideImage(image: String, imageView: ImageView ) {
        Glide.with(context)
            .load(image)
            .centerCrop()
            .placeholder(R.drawable.ic_image)
            .into(imageView)
    }
}
