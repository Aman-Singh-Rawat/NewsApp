package com.newsapp.presenter.screen.newsdetails

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.newsapp.R

class CommentAdapter() : RecyclerView.Adapter<CommentAdapter.ViewHolder>() {
    private var commentList = mutableListOf<CommentData>()

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun onBind(itemView: View, commentData: CommentData) {
            val cvProfile = itemView.findViewById<ImageView>(R.id.cvProfile)
            val tvUserName = itemView.findViewById<TextView>(R.id.tvUserName)
            val tvCommentDays = itemView.findViewById<TextView>(R.id.tvCommentDays)
            val tvFullComment = itemView.findViewById<TextView>(R.id.tvFullComment)

            cvProfile.setImageResource(commentData.cvProfile)
            tvUserName.text = commentData.tvUserName
            tvCommentDays.text = commentData.tvCommentDays
            tvFullComment.text = commentData.tvFullComment

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.comment_recycle_item, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return commentList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.onBind(holder.itemView, commentList[position])
    }

    @SuppressLint("NotifyDataSetChanged")
    fun updateUi(comment: List<CommentData>) {
        this.commentList = comment.toMutableList()
        notifyDataSetChanged()
    }
}