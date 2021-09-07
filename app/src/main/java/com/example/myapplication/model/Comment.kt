package com.example.myapplication.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
class Comment (
    @SerializedName("postId") val postId: Int,
    @SerializedName("id") val id: Int,
    @SerializedName("name") val authorName: String,
    @SerializedName("email") val authorEmail: String,
    @SerializedName("body") val authorBody: String
) : Parcelable