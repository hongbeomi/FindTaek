package com.hongbeomi.findtaek.view.ui.base

import android.util.Log
import android.view.View
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hongbeomi.findtaek.R
import com.hongbeomi.findtaek.view.util.NoConnectionInterceptor
import com.hongbeomi.findtaek.view.util.ToastUtil.Companion.showShort
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import kotlin.coroutines.coroutineContext

/**
 * @author hongbeomi
 */

abstract class BaseViewModel : ViewModel() {

    open val isLoading = MutableLiveData(View.INVISIBLE)

    open fun showLoading() {
        isLoading.value = View.VISIBLE
    }

    open fun hideLoading() {
        isLoading.value = View.INVISIBLE
    }

    open fun onError(t: Throwable) {
        viewModelScope.launch {
            when (t) {
                is NoConnectionInterceptor.NoConnectivityException ->
                    showShort(R.string.base_error_no_connect_network)
                is HttpException -> showShort(R.string.base_error_https)
                else -> {
                    showShort(R.string.base_error_unknown)
                    Log.e("ERROR", "${t.message}")
                }
            }
            hideLoading()
        }
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