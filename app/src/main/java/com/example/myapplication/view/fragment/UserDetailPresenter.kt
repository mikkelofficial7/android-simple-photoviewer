package com.example.myapplication.view.fragment

import com.example.myapplication.model.Album
import com.example.myapplication.model.Comment
import com.example.myapplication.service.NetworkCall
import com.example.myapplication.service.NetworkInterface
import com.example.myapplication.service.NetworkRequest
import com.example.myapplication.service.ResponseCallback

class UserDetailPresenter(val fragment: UserDetailFragment) {

    fun loadUserAlbum(userId: Int) {
        val request = NetworkRequest.create(NetworkInterface::class.java).getUserAlbum(userId)
        NetworkCall.process(request, object: ResponseCallback<List<Album>> {
            override fun onSuccess(responseBody: List<Album>) {
                fragment.setDataAlbum(responseBody)
            }

            override fun onFailed(errorCode: String, errorMessage: String) {
            }

        })
    }
}