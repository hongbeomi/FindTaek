package com.hongbeomi.findtaek.view.util.event

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer

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