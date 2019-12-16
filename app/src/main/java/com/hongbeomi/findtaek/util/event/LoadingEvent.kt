package com.hongbeomi.findtaek.util.event

import android.view.View
import androidx.lifecycle.MutableLiveData

/**
 * @author hongbeomi
 */

fun showLoading() {
    isLoading.postValue(View.VISIBLE)
}

fun hideLoading() {
    isLoading.postValue(View.INVISIBLE)
}

val isLoading = MutableLiveData<Int>(View.INVISIBLE)
