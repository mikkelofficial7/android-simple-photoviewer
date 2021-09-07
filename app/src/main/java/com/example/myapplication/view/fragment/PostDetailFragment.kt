package com.example.myapplication.view.fragment

import android.os.Bundle
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myapplication.R
import com.example.myapplication.model.Comment
import com.example.myapplication.model.Post
import com.example.myapplication.model.User
import com.example.myapplication.view.MainActivity
import com.example.myapplication.view.adapter.CommentListAdapter
import kotlinx.android.synthetic.main.adapter_post_list.*
import kotlinx.android.synthetic.main.fragment_post_detail.*

class PostDetailFragment(var post: Post, var user: User?) : Fragment() {

    private lateinit var presenter: PostDetailPresenter
    private lateinit var adapter: CommentListAdapter

    private var listComment = ArrayList<Comment>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        presenter = PostDetailPresenter(this)

        return inflater.inflate(R.layout.fragment_post_detail, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupView()
        setupClick()

        presenter.loadAllCommentByPostId(post.id)
    }

    private fun setupView() {
        changeTextSize(tv_post_title, resources.getDimension(R.dimen.text_16))
        tv_post_title.text = post.postTitle

        changeTextSize(tv_post_content, resources.getDimension(R.dimen.text_16))
        tv_post_content.text = post.postBody

        changeTextSize(tv_post_owner, resources.getDimension(R.dimen.text_14))
        tv_post_owner.text = "${user?.namePosted} (@${user?.username})"

        adapter = CommentListAdapter(listComment)

        rv_post_detail.layoutManager = LinearLayoutManager(context)
        rv_post_detail.adapter = adapter
    }

    private fun setupClick() {
        tv_post_owner.setOnClickListener {
            user?.let { user ->
                navigateToUserDetail(user)
            }
        }
    }

    fun setDataComment(list: List<Comment>) {
        listComment.clear()
        listComment.addAll(list)

        adapter.notifyDataSetChanged()

        showRv()
    }

    private fun showRv() {
        rv_post_detail.animate().alpha(1f).start()
    }

    private fun changeTextSize(textView: TextView, size: Float) {
        textView.setTextSize(TypedValue.COMPLEX_UNIT_PX, size)
    }

    private fun navigateToUserDetail(user: User) {
        (activity as MainActivity).showUserDetail(user)
    }
}