package com.example.myapplication.view.fragment

import com.example.myapplication.model.Post
import com.example.myapplication.model.User
import com.example.myapplication.service.NetworkCall
import com.example.myapplication.service.NetworkInterface
import com.example.myapplication.service.NetworkRequest
import com.example.myapplication.service.ResponseCallback

class PostListPresenter(val fragment: PostListFragment) {
    fun loadAllPost() {
        val request = NetworkRequest.create(NetworkInterface::class.java).getAllPost()
        NetworkCall.process(request, object: ResponseCallback<List<Post>> {
            override fun onSuccess(responseBody: List<Post>) {
                fragment.setDataPost(responseBody)
            }

            override fun onFailed(errorCode: String, errorMessage: String) {
            }

        })
    }

    fun loadAllUser() {
        val request = NetworkRequest.create(NetworkInterface::class.java).getAllUser()
        NetworkCall.process(request, object: ResponseCallback<List<User>> {
            override fun onSuccess(responseBody: List<User>) {
                fragment.setDataUser(responseBody)
            }

            override fun onFailed(errorCode: String, errorMessage: String) {
            }

        })
    }
}