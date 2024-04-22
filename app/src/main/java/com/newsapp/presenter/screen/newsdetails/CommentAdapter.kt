package com.newsapp.presenter.screen.newsdetails

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.newsapp.databinding.CommentRecycleItemBinding
import com.newsapp.util.calculateElapsedTime

class CommentAdapter() : RecyclerView.Adapter<CommentAdapter.ViewHolder>() {
    private var commentList = mutableListOf<Comment>()

    class ViewHolder(private val binding: CommentRecycleItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun onBind(comment: Comment) {
//            binding.cvProfile.setImageResource(comment.authorProfile)
            binding.tvUserName.text = comment.authorName
            binding.tvCommentDays.text = calculateElapsedTime(comment.postedAt.toLong())
            binding.tvFullComment.text = comment.comment
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = CommentRecycleItemBinding.inflate(inflater, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return commentList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.onBind(commentList[position])
    }

    @SuppressLint("NotifyDataSetChanged")
    fun updateUi(comment: List<Comment>) {
        commentList.clear()
        commentList.addAll(comment)
        notifyDataSetChanged()
    }
}