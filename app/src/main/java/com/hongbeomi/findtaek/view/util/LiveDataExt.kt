package com.hongbeomi.findtaek.view.util

import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData

fun <T> MediatorLiveData<T>.addSourceList(vararg liveDataList: MutableLiveData<*>, onChanged: () -> T) {
    liveDataList.forEach {
        this.addSource(it) { value = onChanged() }
    }
}