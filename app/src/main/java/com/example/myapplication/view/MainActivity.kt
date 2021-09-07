package com.example.myapplication.view

import android.os.Build
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.myapplication.R
import com.example.myapplication.model.Post
import com.example.myapplication.model.User
import com.example.myapplication.view.fragment.PostDetailFragment
import com.example.myapplication.view.fragment.PostListFragment
import com.example.myapplication.view.fragment.UserDetailFragment


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setupWindowColor(resources.getColor(R.color.teal_200))

        setupView()
        showPostList()
    }

    override fun onBackPressed() {
        if(supportFragmentManager.backStackEntryCount > 1) {
            supportFragmentManager.popBackStack()

        } else {
            finish()
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return if (item.itemId == android.R.id.home) {
            onBackPressed()
            true
        } else {
            super.onOptionsItemSelected(item)
        }
    }


    private fun setupWindowColor(color: Int) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
            window.statusBarColor = color
        }
    }

    private fun setupView() {
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    private fun showPostList() {
        val fragment = PostListFragment()
        addFragment(fragment, PostListFragment::class.java.simpleName)
    }

    fun showPostDetail(post: Post, user: User?) {
        val fragment = PostDetailFragment(post, user)
        addFragment(fragment, PostDetailFragment::class.java.simpleName)
    }

    fun showUserDetail(user: User) {
        val fragment = UserDetailFragment(user)
        addFragment(fragment, UserDetailFragment::class.java.simpleName)
    }

    private fun addFragment(fragment: Fragment, menuTag: String) {
        supportFragmentManager
            .beginTransaction()
            .add(R.id.frame_layout, fragment, menuTag)
            .addToBackStack(menuTag)
            .commit()
    }
}