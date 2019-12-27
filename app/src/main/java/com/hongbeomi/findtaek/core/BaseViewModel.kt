package com.hongbeomi.findtaek.core

import androidx.lifecycle.*
import com.hongbeomi.findtaek.view.util.event.ToastMessageEvent

/**
 * @author hongbeomi
 */

open class BaseViewModel : ViewModel() {

    private val toastMessage = ToastMessageEvent()

    fun observeToast(lifecycleOwner: LifecycleOwner, observer: (String) -> Unit) {
        toastMessage.observe(lifecycleOwner, observer)
    }

    fun showToast(str: String) {
        toastMessage.value = str
    }

}

