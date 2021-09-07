package com.example.myapplication.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myapplication.R
import com.example.myapplication.model.Album
import com.example.myapplication.model.Photo
import com.example.myapplication.model.User
import com.example.myapplication.view.adapter.AlbumListAdapter
import com.example.myapplication.view.dialog.PhotoDialog
import kotlinx.android.synthetic.main.fragment_user_detail.*

class UserDetailFragment(var user: User) : Fragment() {

    private lateinit var presenter: UserDetailPresenter
    private lateinit var adapter: AlbumListAdapter

    private var listAlbum = ArrayList<Album>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        presenter = UserDetailPresenter(this)

        return inflater.inflate(R.layout.fragment_user_detail, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupView()
        presenter.loadUserAlbum(user.id)
    }

    private fun setupView() {
        tv_name.text = user.fullName
        tv_email.text = user.email
        tv_address.text = user.address.fullAddress
        tv_company.text = user.company.companyFull

        adapter = AlbumListAdapter(listAlbum, onPhotoClick = ::showPhotoDetail)
        rv_photo.layoutManager = LinearLayoutManager(context)
        rv_photo.adapter = adapter
    }

    fun setDataAlbum(list: List<Album>) {
        listAlbum.clear()
        listAlbum.addAll(list)

        adapter.notifyDataSetChanged()
    }

    private fun showPhotoDetail(photo: Photo) {
        var dialog = childFragmentManager.findFragmentByTag(PhotoDialog::class.java.simpleName) as? PhotoDialog

        if(dialog != null) return

        dialog = PhotoDialog(photo)
        dialog.showNow(childFragmentManager, PhotoDialog::class.java.simpleName)
    }
}