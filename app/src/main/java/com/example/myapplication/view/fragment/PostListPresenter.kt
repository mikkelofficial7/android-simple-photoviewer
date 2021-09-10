package com.example.myapplication.view.fragment

import com.example.myapplication.model.Post
import com.example.myapplication.model.User
import com.example.myapplication.service.*

class PostListPresenter(val fragment: PostListFragment) {
    fun loadAllPost() {
        val request = NetworkCall.provideRequest(NetworkInterface::class.java).getAllPost()
        NetworkCall.process(request, object: ResponseCallback<List<Post>> {
            override fun onSuccess(responseBody: List<Post>) {
                fragment.setDataPost(responseBody)
            }

        })
    }

    fun loadAllUser() {
        val request = NetworkCall.provideRequest(NetworkInterface::class.java).getAllUser()
        NetworkCall.process(request, object: ResponseCallback<List<User>> {
            override fun onSuccess(responseBody: List<User>) {
                fragment.setDataUser(responseBody)
            }

        })
    }
}