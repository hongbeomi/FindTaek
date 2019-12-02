package com.hongbeomi.findtaek.api

import okhttp3.ResponseBody
import retrofit2.Response

/**
 * @author hongbeomi
 */
sealed class ApiResponse<out T> {

    class Success<T>(response: Response<T>) : ApiResponse<T>() {
        val data: T? = response.body()
        override fun toString() = "[Api.Success]: $data"
    }

    sealed class Failure<out T> {
        class Error<out T>(response: Response<out T>) : ApiResponse<T>() {
            val errorBody: ResponseBody? = response.errorBody()
            val code: Int = response.code()
            override fun toString(): String = "[Api.Failure $code]: $errorBody"
        }

        class Exception<out T>(exception: Throwable) : ApiResponse<T>() {
            val message: String? = exception.localizedMessage
            override fun toString(): String = "[Api.Failure]: $message"
        }
    }

    companion object {
        fun <T> error(ex: Throwable) = Failure.Exception<T>(ex)

        fun <T> of(f: () -> Response<T>): ApiResponse<T> = try {
            val response = f()
            if (response.isSuccessful) {
                Success(response)
            } else {
                Failure.Error(response)
            }
        } catch (ex: Exception) {
            Failure.Exception(ex)
        }
    }

}