package com.example.myapplication.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
class Photo (
    @SerializedName("albumId") val albumId: Int,
    @SerializedName("id") val id: Int,
    @SerializedName("title") val photoName: String,
    @SerializedName("url") val photoImageUrl: String,
    @SerializedName("thumbnailUrl") val photoImageThumb: String
) : Parcelable {
    val photoWithExt: String
    get() {
        return "${photoImageThumb}.jpg"
    }
}