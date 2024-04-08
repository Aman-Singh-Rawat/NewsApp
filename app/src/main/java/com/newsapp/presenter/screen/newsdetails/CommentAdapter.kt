package com.newsapp.presenter.screen.newsdetails

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.newsapp.databinding.CommentRecycleItemBinding

class CommentAdapter() : RecyclerView.Adapter<CommentAdapter.ViewHolder>() {
    private var commentList = mutableListOf<Comment>()

    class ViewHolder(private val binding: CommentRecycleItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun onBind(comment: Comment) {
//            binding.cvProfile.setImageResource(comment.authorProfile)
            binding.tvUserName.text = comment.authorName
            binding.tvCommentDays.text = comment.postedAt
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
        this.commentList = comment.toMutableList()
        notifyDataSetChanged()
    }
}