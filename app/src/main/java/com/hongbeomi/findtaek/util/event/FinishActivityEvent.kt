package com.hongbeomi.findtaek.util.event

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import com.hongbeomi.findtaek.util.SingleLiveEvent

class FinishActivityEvent : SingleLiveEvent<Boolean>() {

    fun observe(owner: LifecycleOwner, observer: (Boolean) -> Unit) =
        super.observe(owner, Observer {
            it.run {
                observer(it)
            }
        })

}