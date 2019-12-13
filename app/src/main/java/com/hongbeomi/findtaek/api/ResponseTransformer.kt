package com.hongbeomi.findtaek.api

import retrofit2.Call
import retrofit2.Callback

/**
 * @author hongbeomi
 */

fun <T> Call<T>.async(onResult: (response: ApiResponse<T>) -> Unit) {
    enqueue(object : Callback<T> {
        override fun onResponse(call: Call<T>, response: retrofit2.Response<T>) {
            onResult(ApiResponse.of { response })
        }

        override fun onFailure(call: Call<T>, throwable: Throwable) {
            onResult(ApiResponse.error(throwable))
        }
    })
}

fun <T> ApiResponse.Failure.Error<T>.message() = errorMessage

fun <T> ApiResponse.Failure.Exception<T>.message() = "$message"