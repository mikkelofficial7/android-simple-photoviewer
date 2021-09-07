package com.example.myapplication.view.fragment

import com.example.myapplication.model.Comment
import com.example.myapplication.service.NetworkCall
import com.example.myapplication.service.NetworkInterface
import com.example.myapplication.service.NetworkRequest
import com.example.myapplication.service.ResponseCallback

class PostDetailPresenter(val fragment: PostDetailFragment) {

    fun loadAllCommentByPostId(postId: Int) {
        val request = NetworkRequest.create(NetworkInterface::class.java).getAllCommentByPostId(postId)
        NetworkCall.process(request, object: ResponseCallback<List<Comment>> {
            override fun onSuccess(responseBody: List<Comment>) {
                fragment.setDataComment(responseBody)
            }

            override fun onFailed(errorCode: String, errorMessage: String) {
            }

        })
    }
}