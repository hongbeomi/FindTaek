package com.hongbeomi.findtaek.view.ui.main

import androidx.lifecycle.*
import com.hongbeomi.findtaek.data.repository.Repository
import com.hongbeomi.findtaek.models.dto.DeliveryResponse.Progresses
import com.hongbeomi.findtaek.models.entity.Delivery
import com.hongbeomi.findtaek.view.ui.base.BaseViewModel
import com.hongbeomi.findtaek.view.util.SingleLiveEvent
import kotlinx.coroutines.launch

/**
 * @author hongbeomi
 */

class MainViewModel(private val repository: Repository) : BaseViewModel() {

    val onSendCoordinatesEvent = SingleLiveEvent<Unit>()
    val onInitUpdateEvent = MutableLiveData<Unit?>()
    val isRefresh: MutableLiveData<Boolean?> = MutableLiveData()
    val allDeliveryList = liveData {
        val fromDB: LiveData<List<Delivery>> = repository.getAll().asLiveData()
        emitSource(fromDB)
    }

    fun callInitUpdate() {
        onInitUpdateEvent.value = null
    }

    fun callSendCoordinates() = onSendCoordinatesEvent.call()

    fun delete(delivery: Delivery) {
        viewModelScope.launch { repository.delete(delivery) }
    }

    fun rollback(delivery: Delivery) {
        viewModelScope.launch { repository.rollback(delivery) }
    }

    fun updateAll() {
        viewModelScope.launch {
            allDeliveryList.value
                .takeIf { !it.isNullOrEmpty() }
                ?.let {
                    update(it)
                    isRefresh.postValue(true)
                } ?: isRefresh.postValue(false)
        }
    }

    private suspend fun update(list: List<Delivery>) {
        list.map {
            handle { repository.getData(it.carrierName, it.trackId) }?.toDelivery(
                it.id, it.carrierName, it.productName, it.trackId
            )
        }.apply { repository.updateAll(this.filterNotNull()) }
    }

    fun getProgressesList(
        carrierName: String,
        trackId: String,
        action: (ArrayList<Progresses>) -> Unit,
    ) {
        showLoading()
        viewModelScope.launch {
            handle { repository.getProgresses(carrierName, trackId) }?.let { action(it) }
            hideLoading()
        }
    }

}

