package com.hongbeomi.findtaek.compose

import android.app.Application
import androidx.lifecycle.*
import com.hongbeomi.findtaek.repository.DeliveryRepository
import com.hongbeomi.findtaek.models.entity.Delivery
import com.hongbeomi.findtaek.util.SingleLiveEvent
import com.hongbeomi.findtaek.util.event.ToastMessageEvent
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import kotlinx.coroutines.Dispatchers

open class BaseViewModel : ViewModel() {

    protected var id: Long? = null

    private val disposables: CompositeDisposable = CompositeDisposable()
    private val toastMessage = ToastMessageEvent()

    fun observeToast(lifecycleOwner: LifecycleOwner, observer: (String) -> Unit) {
        toastMessage.observe(lifecycleOwner, observer)
    }

    fun addToDisposable(disposable: Disposable) {
        disposables.add(disposable)
    }

    override fun onCleared() {
        disposables.clear()
        super.onCleared()
    }

    fun showToast(str: String) {
        toastMessage.value = str
    }

}

