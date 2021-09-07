package com.example.myapplication.view.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R
import com.example.myapplication.model.Comment
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.adapter_comment.view.*

class CommentListAdapter(
    var listComment: List<Comment>
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return CommentViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.adapter_comment, parent, false)
        )
    }

    override fun getItemCount(): Int {
        return listComment.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is CommentViewHolder -> {
                holder.bindData(listComment[position])
            }
        }
    }

    inner class CommentViewHolder(override val containerView: View) : RecyclerView.ViewHolder(containerView),
        LayoutContainer {

        fun bindData(comment: Comment) {
            containerView.tv_comment_name.text = comment.authorEmail
            containerView.tv_comment_body.text = comment.authorBody
            containerView.tv_comment_title.text = comment.authorName
        }
    }
}