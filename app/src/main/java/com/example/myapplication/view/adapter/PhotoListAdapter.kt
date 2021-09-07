package com.example.myapplication.view.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R
import com.example.myapplication.model.Post
import com.example.myapplication.model.User
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.adapter_post_list.view.*

class PhotoListAdapter(var listUser: List<User>,
                       var listPost: List<Post>,
                       var onItemClick: (Post, User?) -> Unit
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return PhotoListViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.adapter_post_list, parent, false)
        )
    }

    override fun getItemCount(): Int {
        return listPost.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is PhotoListViewHolder -> {
                holder.bindData(listPost[position], onItemClick)
            }
        }
    }

    inner class PhotoListViewHolder(override val containerView: View) : RecyclerView.ViewHolder(containerView),
        LayoutContainer {

        fun bindData(post: Post, onItemClick: (Post, User?) -> Unit) {
            val user = listUser.find { it.id == post.userId }
            
            containerView.tv_post_title.text = post.postTitle
            containerView.tv_post_content.text = post.postBody

            containerView.tv_post_owner.text = user?.namePosted
            containerView.tv_post_company.text = user?.company?.companyName

            containerView.setOnClickListener {
                onItemClick(post, user)
            }
        }
    }
}