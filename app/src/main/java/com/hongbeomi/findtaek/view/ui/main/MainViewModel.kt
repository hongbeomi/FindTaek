package com.hongbeomi.findtaek.view.ui.main

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.liveData
import com.hongbeomi.findtaek.data.repository.Repository
import com.hongbeomi.findtaek.models.dto.DeliveryResponse.Progresses
import com.hongbeomi.findtaek.models.entity.Delivery
import com.hongbeomi.findtaek.view.ui.base.BaseViewModel
import com.hongbeomi.findtaek.view.util.OneTakeLiveData
import com.hongbeomi.findtaek.view.util.SingleLiveEvent

/**
 * @author hongbeomi
 */

class MainViewModel(private val repository: Repository) : BaseViewModel() {

    val onSendCoordinatesEvent = SingleLiveEvent<Unit>()
    val onInitUpdateEvent = OneTakeLiveData<Unit>()
    val isRefresh: MutableLiveData<Boolean?> = MutableLiveData()
    val allDeliveryList = liveData {
        val fromDB: LiveData<List<Delivery>> = repository.getAll()
        emitSource(fromDB)
    }

    fun callInitUpdate() = onInitUpdateEvent.call()

    fun callSendCoordinates() = onSendCoordinatesEvent.call()

    fun delete(delivery: Delivery) = launchViewModelScope { repository.delete(delivery) }

    fun rollback(delivery: Delivery) = launchViewModelScope { repository.rollback(delivery) }

    fun updateAll() =
        launchViewModelScope {
            allDeliveryList.value
                .takeIf { !it.isNullOrEmpty() }
                ?.let {
                    update(it)
                    isRefresh.postValue(true)
                } ?: isRefresh.postValue(false)
        }

    private suspend fun update(list: List<Delivery>) {
        Log.e("업데이트", list.first().id.toString())
        list.map {
            handle { repository.getData(it.carrierName, it.trackId) }?.toDelivery(
                it.id, it.carrierName, it.productName, it.trackId
            )
        }.apply { repository.updateAll(this.filterNotNull()) }
    }

    fun getProgressesList(
        carrierName: String,
        trackId: String,
        action: (ArrayList<Progresses>) -> Unit
    ) = launchViewModelScope {
        showLoading()
        handle { repository.getProgresses(carrierName, trackId) }?.let { action(it) }
        hideLoading()
    }

}

