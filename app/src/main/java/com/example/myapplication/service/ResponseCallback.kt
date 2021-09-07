package com.example.myapplication.service

interface ResponseCallback<T> {

    fun onSuccess(responseBody: T)

    fun onFailed(errorCode: String, errorMessage: String)
}