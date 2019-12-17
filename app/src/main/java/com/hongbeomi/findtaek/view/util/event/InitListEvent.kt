package com.hongbeomi.findtaek.view.util.event

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer

class InitListEvent : SingleLiveEvent<Boolean?>() {

    fun observe(owner: LifecycleOwner, observer: (Boolean) -> Unit) =
        super.observe(owner, Observer {
            it.run {
                if (it != null) {
                    observer(it)
                }
            }
        })

}