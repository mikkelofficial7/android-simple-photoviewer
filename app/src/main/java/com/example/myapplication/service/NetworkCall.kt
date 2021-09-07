package com.example.myapplication.service

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

object NetworkCall {
    fun <T>process(call: Call<T>, responseCallback: ResponseCallback<T>) {

        val callback = object : Callback<T> {
            override fun onResponse(call: Call<T>, response: Response<T>) {
                val responseBody = response.body() as T

                responseCallback.onSuccess(responseBody)
            }

            override fun onFailure(call: Call<T>, t: Throwable) {
                responseCallback.onFailed(t.message.toString(), t.localizedMessage.toString())
            }
        }

        call.enqueue(callback)
    }
}