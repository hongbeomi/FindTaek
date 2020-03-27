package com.hongbeomi.findtaek.view.util

import androidx.annotation.MainThread
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer

/**
 * @author hongbeomi
 *
 * 호출했을 경우 단 한 번만 실행되는 LiveData
 */

class OneTakeLiveData<T> : MutableLiveData<OneTakeLiveData<T>>() {

    override fun observe(owner: LifecycleOwner, observer: Observer<in OneTakeLiveData<T>>) {
        super.observe(owner,  object : Observer<OneTakeLiveData<T>> {
            override fun onChanged(t: OneTakeLiveData<T>?) {
                observer.onChanged(t)
                removeObserver(this)
            }
        })
    }

    @MainThread
    fun call() {
        value = null
    }

}