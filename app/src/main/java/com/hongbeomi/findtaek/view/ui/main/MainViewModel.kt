package com.hongbeomi.findtaek.view.ui.main

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.hongbeomi.findtaek.models.entity.Delivery
import com.hongbeomi.findtaek.core.BaseViewModel
import com.hongbeomi.findtaek.repository.DeliveryRepository
import com.hongbeomi.findtaek.view.util.event.InitListEvent
import kotlinx.coroutines.*

/**
 * @author hongbeomi
 */

class MainViewModel
constructor(
    private val repository: DeliveryRepository,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
) :
    BaseViewModel() {

    companion object {
        val initEvent = InitListEvent()
    }

    private lateinit var liveItemList: LiveData<List<Delivery>>

    fun getAllDelivery(): LiveData<List<Delivery>> {
        liveItemList = repository.getAllByRepository()
        return liveItemList
    }

    fun deleteDelivery(delivery: Delivery) {
        viewModelScope.launch(ioDispatcher) {
            repository.deleteByRepository(delivery)
        }
    }

    fun rollbackDelivery(delivery: Delivery) {
        viewModelScope.launch(ioDispatcher) {
            repository.rollbackByRepository(delivery)
        }
    }

    fun updateDelivery() {
        try {
            for (item in liveItemList.value!!) {
                viewModelScope.launch(ioDispatcher) {
                    repository.loadDeliveryDataAndUpdate(item) {
                        showToast(it)
                    }
                }
            }
        } catch (e: NullPointerException) {
            showToast("리스트가 비었습니다!")
        }
    }

    fun observeInit(lifecycleOwner: LifecycleOwner, observer: (Boolean) -> Unit) =
        initEvent.observe(lifecycleOwner, observer)

}

