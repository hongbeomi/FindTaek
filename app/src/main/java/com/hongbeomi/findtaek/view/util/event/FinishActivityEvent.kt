package com.hongbeomi.findtaek.view.util.event

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer

/**
 * @author hongbeomi
 */

class FinishActivityEvent : SingleLiveEvent<Boolean>() {

    fun observe(owner: LifecycleOwner, observer: (Boolean) -> Unit) =
        super.observe(owner, Observer {
            it.run {
                if (it) {
                    observer(it)
                }
            }
        })

}