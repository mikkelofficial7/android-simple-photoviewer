package com.example.myapplication.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myapplication.R
import com.example.myapplication.model.Post
import com.example.myapplication.model.User
import com.example.myapplication.view.MainActivity
import com.example.myapplication.view.adapter.PhotoListAdapter
import kotlinx.android.synthetic.main.fragment_post_list.*

class PostListFragment : Fragment() {

    private lateinit var presenter: PostListPresenter
    private lateinit var adapter: PhotoListAdapter

    private var listPost = ArrayList<Post>()
    private var listUser = ArrayList<User>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        presenter = PostListPresenter(this)

        return inflater.inflate(R.layout.fragment_post_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupView()

        presenter.loadAllPost()
        presenter.loadAllUser()
    }

    private fun setupView() {
        adapter = PhotoListAdapter(listUser, listPost, onItemClick = ::navigateToDetailPost)

        rv_post.layoutManager = LinearLayoutManager(context)
        rv_post.adapter = adapter
    }

    fun setDataPost(posts: List<Post>) {
        listPost.clear()
        listPost.addAll(posts)

        adapter.notifyDataSetChanged()
    }

    fun setDataUser(users: List<User>) {
        listUser.clear()
        listUser.addAll(users)

        adapter.notifyDataSetChanged()
    }

    private fun navigateToDetailPost(post: Post, user: User?) {
        (activity as MainActivity).showPostDetail(post, user)
    }
}