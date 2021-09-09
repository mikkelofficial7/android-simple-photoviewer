package com.example.myapplication.view.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.myapplication.R
import com.example.myapplication.model.Album
import com.example.myapplication.model.Comment
import com.example.myapplication.model.Photo
import com.example.myapplication.service.NetworkCall
import com.example.myapplication.service.NetworkInterface
import com.example.myapplication.service.NetworkRequest
import com.example.myapplication.service.ResponseCallback
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.adapter_album.view.*
import kotlinx.android.synthetic.main.adapter_comment.view.*
import kotlinx.android.synthetic.main.item_photo.view.*

class AlbumListAdapter(
    var listAlbum: List<Album>,
    var onPhotoClick: (Photo) -> Unit
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return AlbumViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.adapter_album, parent, false)
        )
    }

    override fun getItemCount(): Int {
        return listAlbum.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is AlbumViewHolder -> {
                holder.bindData(listAlbum[position])
            }
        }
    }

    inner class AlbumViewHolder(override val containerView: View) : RecyclerView.ViewHolder(containerView),
        LayoutContainer {

        fun bindData(album: Album) {
            containerView.tv_album.text = album.albumTitle

            getAlbumPhoto(albumId = album.id, onAlbumLoad = {
                it.forEach { photo ->
                    containerView.layout_photo_album.addView(photoView(photo))
                }
            })
        }

        private fun photoView(photo: Photo) : View {
            val view = LayoutInflater.from(containerView.context).inflate(R.layout.item_photo, containerView.layout_photo_album, false)
            Glide.with(containerView.context).load(photo.photoWithExt).into(view.iv_photo)

            view.setOnClickListener {
                onPhotoClick(photo)
            }

            return view
        }

        private fun getAlbumPhoto(albumId: Int, onAlbumLoad: (List<Photo>) -> Unit) {
            val request = NetworkRequest.create(NetworkInterface::class.java).getPhotoAlbum(albumId)
            NetworkCall.process(request, object: ResponseCallback<List<Photo>> {
                override fun onSuccess(responseBody: List<Photo>) {
                    onAlbumLoad(responseBody)
                }

            })
        }
    }
}