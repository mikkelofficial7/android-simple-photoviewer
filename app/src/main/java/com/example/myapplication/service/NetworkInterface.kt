package com.example.myapplication.service

import com.example.myapplication.model.*
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query


interface NetworkInterface {
    @GET("posts")
    fun getAllPost() : Call<List<Post>>

    @GET("users")
    fun getAllUser() : Call<List<User>>

    @GET("posts/{postId}/comments")
    fun getAllCommentByPostId(@Path(value = "postId", encoded = true) id: Int) : Call<List<Comment>>

    @GET("albums")
    fun getUserAlbum(@Query("userId") userId: Int) : Call<List<Album>>

    @GET("photos")
    fun getPhotoAlbum(@Query("albumId") albumId: Int) : Call<List<Photo>>
}