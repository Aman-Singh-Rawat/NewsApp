package com.newsapp.presenter.screen.profile

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.PopupMenu
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.newsapp.R
import com.newsapp.data.models.Article
import com.newsapp.databinding.RecentRecycleItemBinding
import com.newsapp.util.OnItemClickListener
import com.newsapp.util.calculateElapsedTime
import com.newsapp.util.glideImage

class ProfileAdapter(private val listener:
OnItemClickListener): RecyclerView.Adapter<ProfileAdapter.NewsArticlesAdapter>() {
    private var list: List<Article> = emptyList()
    private var flag = false
    private val selectedItems = mutableListOf<String>()
    private lateinit var context: Context

    inner class NewsArticlesAdapter(val binding: RecentRecycleItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsArticlesAdapter {
        val binding = RecentRecycleItemBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )

        return NewsArticlesAdapter(binding)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: NewsArticlesAdapter, position: Int) {
        bindTheViews(holder, position)
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun bindTheViews(holder: NewsArticlesAdapter, position: Int) {
        val isSelected = selectedItems.contains(list[position].articleId)

        holder.apply {
            itemView.isSelected = isSelected

            binding.apply {
                tvHeadline.setOnClickListener {
                    listener.onItemClick(list[position].articleId)
                }
                cvNewsImg.setOnClickListener {
                    listener.onItemClick(list[position].articleId)
                }

                includeRecentItem.apply {
                    icSave.setOnClickListener {
                        if (selectedItems.contains(list[position].articleId)) {
                            selectedItems.remove(list[position].articleId)
                            listener.onArticleSaveListener(selectedItems)
                        } else {
                            selectedItems.add(list[position].articleId)
                            listener.onArticleSaveListener(selectedItems)
                        }
                        notifyDataSetChanged()
                    }
                    if (flag) {
                        setUpMenu(binding, position)
                    }
                    tvHeadline.text = list[position].title
                    glideImage(context, binding.ivNewsImg, list[position].image)
                    glideImage(
                        context,
                        imgChannelLogo,
                        list[position].authorProfile,
                        true
                    )
                    tvTotalViews.text = list[position].userViewed.size.toString()
                    tvChannelName.text = list[position].authorName
                    tvDaysAgo.text = calculateElapsedTime(list[position].time)
                    tvTotalComments.text = "${list[position].comments} comments"

                    icShare.setOnClickListener {
                        shareArticle(context, list[position])
                    }
                }
            }
        }

    }

    private fun setUpMenu(binding: RecentRecycleItemBinding, position: Int) {
        binding.includeRecentItem.icSave.setImageResource(R.drawable.ic_three_dot_chat)
        binding.includeRecentItem.icSave.setOnClickListener {
            val popupMenu = PopupMenu(context, it)
            popupMenu.inflate(R.menu.profile_menu)
            popupMenu.setOnMenuItemClickListener { menuItem ->
                when (menuItem.itemId) {
                    R.id.editStory -> {
                        listener.onItemClick(list[position].articleId + "editStory")
                        true
                    }

                    R.id.deleteStory -> {
                        listener.onItemClick(list[position].articleId + "deleteStory")
                        true
                    }

                    else -> true
                }
            }
            popupMenu.show()
            val popup = PopupMenu::class.java.getDeclaredField("mPopup")
            popup.isAccessible = true
            val menu = popup.get(popupMenu)
            menu.javaClass.getDeclaredMethod("setForceShowIcon", Boolean::class.java)
                .invoke(menu, true)
        }
    }

    fun updateUi(list: List<Article>, flag: Boolean, context: Context) {
        this.flag = flag
        this.context = context
        this.list = list
    }

    private fun shareArticle(context: Context, article: Article) {
        val shareIntent = Intent(Intent.ACTION_SEND)
        shareIntent.type = "text/plain"
        shareIntent.putExtra(Intent.EXTRA_SUBJECT, "Shared Article")
        shareIntent.putExtra(Intent.EXTRA_TEXT, "Check out this article: ${article.title}\n${article.story}")
        context.startActivity(Intent.createChooser(shareIntent, "Share via"))
    }
}
