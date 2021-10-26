package com.example.myapplication.view.customView.imageLoader

import android.content.Context
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.RelativeLayout
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.load.model.GlideUrl
import com.bumptech.glide.load.model.LazyHeaders
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.Target
import com.example.myapplication.R
import kotlinx.android.synthetic.main.item_photo_with_loader.view.*
import java.lang.Exception
import java.net.MalformedURLException
import java.net.URL
import kotlin.math.roundToInt

class LoaderImageView : RelativeLayout {

    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        init(attrs)
    }
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ) {
        init(attrs)
    }

    private var currentImageUrl: String = ""
    private var currentHeight: Float? = null
    private var currentWidth: Float? = null
    private var canShowProgressLoading = false
    private var currentHeaders: Map<String, String>? = null
    private var currentRequestOptions: RequestOptions? = null
    private var progressColor = resources.getColor(R.color.teal_200, null)
    private var scaleType: ImageView.ScaleType? = null

    private var title: String = ""

    init {
        View.inflate(context, R.layout.item_photo_with_loader, this)
    }

    fun setTitle(newTitle: String) {
        title = newTitle
    }

    fun loadImage(imageUrl: String, requestOptions: RequestOptions? = null, headers: Map<String, String>? = null) {
        try {
            currentImageUrl = URL(imageUrl).toString()
            currentRequestOptions = requestOptions
            currentHeaders = headers

            loadGlide()

        } catch (e: MalformedURLException) {
            setPlaceholderImage()
            sendNotificationToFirebase(e)
        }
    }

    private fun loadGlide() {
        val lazyBuilder = LazyHeaders.Builder()
        currentHeaders?.let {
            for (header in it.entries) {
                lazyBuilder.addHeader(header.key, header.value)
            }
        }

        GlideImageModule.proceed(currentImageUrl, object: OnDownloadProgress {
            override fun onUpdate(progress: Int) {
                println("Updating progress download $progress .....")

                updateProgressView(progress)
            }
        })

        val lazyHeader = lazyBuilder.build()

        val glideUrl = GlideUrl(currentImageUrl, lazyHeader)

        val glideRequestManager = Glide.with(context)
        currentRequestOptions?.let { glideRequestManager.setDefaultRequestOptions(currentRequestOptions!!) }

        val glideRequestBuilder = glideRequestManager.load(glideUrl)

        currentRequestOptions?.let { glideRequestBuilder.apply(currentRequestOptions!!) }

        glideRequestBuilder.into(image_view)

        glideRequestBuilder.listener(object : RequestListener<Drawable> {
            override fun onLoadFailed(
                e: GlideException?,
                model: Any?,
                target: Target<Drawable>?,
                isFirstResource: Boolean
            ): Boolean {

                setPlaceholderImage()
                e?.let { sendNotificationToFirebase(it) }

                return false
            }

            override fun onResourceReady(
                resource: Drawable?,
                model: Any?,
                target: Target<Drawable>?,
                dataSource: DataSource?,
                isFirstResource: Boolean
            ): Boolean {
                return true
            }

        }).submit()
    }

    private fun init(attrs: AttributeSet?) {
        if (attrs != null) {
            val styled = context.obtainStyledAttributes(attrs, R.styleable.CustomImageView)
            currentHeight = styled.getFloat(R.styleable.CustomImageView_height, 0f)
            currentWidth = styled.getFloat(R.styleable.CustomImageView_width, 0f)

            val type = styled.getInt(R.styleable.CustomImageView_scaleType, -1)
            scaleType = if(type >= 0) ImageView.ScaleType.values().find { it.ordinal == type }!! else null

            canShowProgressLoading = styled.getBoolean(R.styleable.CustomImageView_showProgress, false)

            progressColor = styled.getColor(R.styleable.CustomImageView_progressColor, resources.getColor(R.color.teal_200, null))

            styled.recycle()

            setupView()
        }
    }

    private fun setupView() {
        setRootView()

        setImageScale()
        setProgressView()
    }

    private fun setRootView() {
        if (currentHeight == null || currentHeight!! < 1f|| currentWidth == null || currentWidth!! < 1f) return

        val layoutParams = this.layoutParams
        layoutParams.width = currentWidth!!.roundToInt()
        layoutParams.height = currentHeight!!.roundToInt()

        this.layoutParams = layoutParams
        this.requestLayout()
    }

    private fun setImageScale() {
        scaleType?.let {
            image_view.scaleType = it
        }
    }

    private fun setProgressView() {
        progress_bar.setIndicatorColor(progressColor)
    }

    private fun showDownloadProgressIfNeeded() {
        if(!canShowProgressLoading) return

        progress_bar.alpha = 1f
        tv_progress.alpha = 1f
    }

    private fun hideDownloadProgress() {
        progress_bar.alpha = 0f
        tv_progress.alpha = 0f
    }

    private fun updateProgressView(progress: Int) {
        println("Update progress view : $progress")

        if (progress >= 100) {
            println("Removing url image : $currentImageUrl")
            GlideImageModule.finished(currentImageUrl)
            hideDownloadProgress()
            return
        }

        showDownloadProgressIfNeeded()

        tv_progress.text = "$progress%"
        progress_bar.max = 100
        progress_bar.progress = progress
    }

    private fun setPlaceholderImage() {
        hideDownloadProgress()
        image_view.setImageResource(R.drawable.ic_launcher_background) //if image broken
    }

    private fun sendNotificationToFirebase(exception: Exception) {
        Log.e("Error Load Attachment", exception.message.toString())
    }
}