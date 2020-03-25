package com.hongbeomi.findtaek.view.ui.main

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.liveData
import com.hongbeomi.findtaek.data.repository.Repository
import com.hongbeomi.findtaek.models.dto.DeliveryResponse.Progresses
import com.hongbeomi.findtaek.models.entity.Delivery
import com.hongbeomi.findtaek.view.ui.base.BaseViewModel
import com.hongbeomi.findtaek.view.util.SingleLiveEvent

/**
 * @author hongbeomi
 */

class MainViewModel(private val repository: Repository) : BaseViewModel() {

    val onSendCoordinatesEvent = SingleLiveEvent<Unit>()

    val isRefresh: MutableLiveData<Boolean?> = MutableLiveData()
    val allDeliveryList = liveData {
        emitSource(repository.getAll())
    }

    fun callSendCoordinates() = onSendCoordinatesEvent.call()

    fun delete(delivery: Delivery) =
        launchViewModelScope { repository.delete(delivery) }

    fun rollback(delivery: Delivery) =
        launchViewModelScope { repository.rollback(delivery) }

    fun updateAll() =
        launchViewModelScope {
            if (allDeliveryList.value.isNullOrEmpty())
                isRefresh.postValue(false)
            else {
                allDeliveryList.value!!
                    .map {
                        handle { repository.getData(it.carrierName, it.trackId) }?.toDelivery(
                            it.id, it.carrierName, it.productName, it.trackId
                        )
                    }
                    .apply { repository.updateAll(this.filterNotNull()) }
                isRefresh.postValue(true)
            }
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

