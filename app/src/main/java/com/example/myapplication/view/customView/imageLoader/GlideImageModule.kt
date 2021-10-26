package com.example.myapplication.view.customView.imageLoader

import android.content.Context
import com.bumptech.glide.Glide
import com.bumptech.glide.Registry
import com.bumptech.glide.annotation.GlideModule
import com.bumptech.glide.integration.okhttp3.OkHttpUrlLoader
import com.bumptech.glide.load.model.GlideUrl
import com.bumptech.glide.module.AppGlideModule
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import java.io.InputStream
import java.util.*

@GlideModule(glideName = "GlideImageCustom")
class GlideImageModule: AppGlideModule() {

    companion object {
        fun proceed(url: String, downloadProgress: OnDownloadProgress) {
            AttachmentDownloadListener.add(url, downloadProgress)
        }

        fun finished(url: String) {
            AttachmentDownloadListener.remove(url)
        }
    }

    override fun registerComponents(context: Context, glide: Glide, registry: Registry) {
        super.registerComponents(context, glide, registry)

        val client = OkHttpClient.Builder()
            .addNetworkInterceptor { chain ->
                val request: Request = chain.request()
                val response: Response = chain.proceed(request)

                response.newBuilder()
                    .body(ProgressResponseBody(response.body()) { progressUpdate ->
                        println("Request : ${request.url()}")
                        println("Updating progress $progressUpdate")

                        val attachmentDownloadListener = AttachmentDownloadListener()
                        attachmentDownloadListener.onUpdate(request.url().toString(), progressUpdate)
                    })
                    .build()
            }
            .build()

        registry.replace(GlideUrl::class.java, InputStream::class.java, OkHttpUrlLoader.Factory(client))
    }
}

private class AttachmentDownloadListener: Downloadlistener {
    companion object {
        private val downloadProgressListeners = WeakHashMap<String, OnDownloadProgress>()

        fun add(url: String, downloadProgress: OnDownloadProgress) {
            downloadProgressListeners[url] = downloadProgress
        }

        fun remove(url: String) {
            downloadProgressListeners.remove(url)
        }
    }

    override fun onUpdate(url: String, progress: Int) {
        val currentDownloadProgress = downloadProgressListeners[url] ?: return

        CoroutineScope(Dispatchers.Main).launch {
            currentDownloadProgress.onUpdate(progress)
        }
    }
}

interface OnDownloadProgress {
    fun onUpdate(progress: Int)
}

private interface Downloadlistener {
    fun onUpdate(url: String, progress: Int)
}