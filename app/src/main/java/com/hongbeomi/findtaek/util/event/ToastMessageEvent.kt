package com.hongbeomi.findtaek.util.event

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import com.hongbeomi.findtaek.util.SingleLiveEvent

/**
 * @author hongbeomi
 */
class ToastMessageEvent : SingleLiveEvent<String>() {

    fun observe(owner: LifecycleOwner, observer: (String) -> Unit) {
        super.observe(owner, Observer {
            it?.run {
                observer(it)
            }
        })
    }

}