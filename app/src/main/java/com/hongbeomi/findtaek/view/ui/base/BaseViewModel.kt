package com.hongbeomi.findtaek.view.base

import android.util.Log
import android.view.View
import androidx.lifecycle.*
import com.google.gson.Gson
import com.hongbeomi.findtaek.view.util.network.NoConnectionInterceptor
import com.hongbeomi.findtaek.models.Result
import com.hongbeomi.findtaek.models.entity.ErrorResponse
import com.hongbeomi.findtaek.view.util.ToastUtil.Companion.showShort
import kotlinx.coroutines.*
import retrofit2.HttpException
import kotlin.coroutines.coroutineContext

/**
 * @author hongbeomi
 */

abstract class BaseViewModel : ViewModel() {

    open val isLoading = MutableLiveData<Int>(View.INVISIBLE)
    open fun showLoading() = isLoading.postValue(View.VISIBLE)
    open fun hideLoading() = isLoading.postValue(View.INVISIBLE)

    open fun onError(e: Exception) {
        viewModelScope.launch {
            when (e) {
                is NoConnectionInterceptor.NoConnectivityException ->
                    showShort("인터넷 연결이 끊어졌습니다!")
                is HttpException -> showShort(getErrorMessage(e))
                else -> {
                    Log.e("ERROR", "${e.message}")
                    showShort("알 수 없는 오류가 발생했습니다")
                }
            }
            hideLoading()
        }
    }

    open fun getErrorMessage(exception: HttpException): String {
        val errorString = exception.response()?.errorBody()?.string()
        val errorDto: ErrorResponse? = Gson().fromJson<ErrorResponse>(
            errorString, ErrorResponse::class.java
        )
        return errorDto?.message ?: "UnknownException"
    }

    suspend fun <T> handle(caller: suspend () -> Result<T>): T? {
        return withContext(CoroutineScope(coroutineContext).coroutineContext) {
            caller.invoke().let {
                when (it) {
                    is Result.Success -> it.data
                    is Result.Error -> {
                        onError(it.exception)
                        null
                    }
                }
            }
        }
    }

}