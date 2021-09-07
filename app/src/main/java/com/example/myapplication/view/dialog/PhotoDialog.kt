package com.example.myapplication.view.dialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.bumptech.glide.Glide
import com.example.myapplication.R
import com.example.myapplication.model.Photo
import kotlinx.android.synthetic.main.dialog_photo.*

class PhotoDialog(val photo: Photo) : DialogFragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        isCancelable = true

        return inflater.inflate(R.layout.dialog_photo, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        context?.let {
            Glide.with(it).load(photo.photoWithExt).into(iv_photo)
            tv_photo.text = photo.photoName
        }

        tv_close.setOnClickListener {
            dismissAllowingStateLoss()
        }
    }

    override fun getTheme(): Int = R.style.Dialog
}