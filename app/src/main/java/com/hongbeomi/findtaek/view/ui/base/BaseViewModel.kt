package com.hongbeomi.findtaek.view.ui.base

import android.util.Log
import android.view.View
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.gson.Gson
import com.hongbeomi.findtaek.R
import com.hongbeomi.findtaek.models.dto.ErrorResponse
import com.hongbeomi.findtaek.view.util.NoConnectionInterceptor
import com.hongbeomi.findtaek.view.util.ToastUtil.Companion.showShort
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import kotlin.coroutines.coroutineContext

/**
 * @author hongbeomi
 */

abstract class BaseViewModel : ViewModel() {

    open fun launchViewModelScope(doWork: suspend () -> Unit) =
        viewModelScope.launch(viewModelScope.coroutineContext + Dispatchers.IO) {
            doWork()
        }

    open val isLoading = MutableLiveData(View.INVISIBLE)
    open fun showLoading() = isLoading.postValue(View.VISIBLE)
    open fun hideLoading() = isLoading.postValue(View.INVISIBLE)

    open fun onError(t: Throwable) {
        viewModelScope.launch {
            when (t) {
                is NoConnectionInterceptor.NoConnectivityException ->
                    showShort(R.string.base_error_no_connect_network)
                is HttpException -> showShort(getErrorMessage(t))
                else -> {
                    showShort(R.string.base_error_unknown)
                    Log.e("ERROR", "${t.message}")
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
        return errorDto?.message ?: "알 수 없는 오류가 발생했습니다"
    }

    suspend fun <T> handle(call: suspend () -> T): T? {
        return withContext(CoroutineScope(coroutineContext).coroutineContext) {
            call.runCatching { this.invoke() }
                .getOrElse {
                    onError(it)
                    null
                }
        }
    }

}