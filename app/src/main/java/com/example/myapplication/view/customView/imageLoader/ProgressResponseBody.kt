package com.example.myapplication.view.customView.imageLoader

import okhttp3.MediaType
import okhttp3.ResponseBody
import okio.*

class ProgressResponseBody(
    private val responseBody: ResponseBody?,
    private val onUpdate: (Int) -> Unit
) : ResponseBody() {
    private var bufferedSource: BufferedSource? = null

    override fun contentType(): MediaType? {
        return responseBody?.contentType()
    }

    override fun contentLength(): Long {
        return responseBody?.contentLength() ?: 0L
    }

    override fun source(): BufferedSource? {
        if (responseBody == null) return null

        if (bufferedSource == null) {
            bufferedSource = Okio.buffer(newSource(responseBody.source()))
        }
        return bufferedSource!!
    }

    private fun newSource(source: BufferedSource): Source {
        return object: ForwardingSource(source) {
            var totalBytesRead = 0L

            override fun read(sink: Buffer, byteCount: Long): Long {
                val bytesRead = super.read(sink, byteCount)
                val fullLength = responseBody?.contentLength() ?: 0L

                if (bytesRead == -1L) { // this source is exhausted
                    totalBytesRead = fullLength
                } else {
                    totalBytesRead += bytesRead
                }

                val percentage = ((totalBytesRead.toDouble() / fullLength.toDouble()) * 100).toInt()

                println("Download Percentage : $percentage% - Total Bytes Read : $totalBytesRead - Full length : $fullLength")

                onUpdate(percentage)

                return bytesRead
            }
        }
    }
}