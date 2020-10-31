package com.hongbeomi.findtaek.view.util

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer

fun <T> MediatorLiveData<T>.addSourceList(vararg liveDataList: MutableLiveData<*>, onChanged: () -> T) {
    liveDataList.forEach {
        this.addSource(it) { value = onChanged() }
    }
}

inline fun <T> LiveData<T>.observeOnce(
    lifecycleOwner: LifecycleOwner,
    crossinline observer: (T) -> Unit
) {
    observe(lifecycleOwner, object : Observer<T> {
        override fun onChanged(t: T) {
            observer.invoke(t)
            removeObserver(this)
        }
    })
}