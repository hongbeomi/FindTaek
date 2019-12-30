package com.hongbeomi.findtaek.view.ui.add

import androidx.lifecycle.*
import com.hongbeomi.findtaek.view.util.event.FinishActivityEvent
import com.hongbeomi.findtaek.core.BaseViewModel
import com.hongbeomi.findtaek.repository.DeliveryRepository
import com.hongbeomi.findtaek.view.util.event.showLoading
import kotlinx.coroutines.*

/**
 * @author hongbeomi
 */

class AddViewModel
constructor(private val repository: DeliveryRepository) : BaseViewModel() {

    companion object {
        val finishEvent = FinishActivityEvent()
    }

    val productName = MutableLiveData<String>()
    val carrierName = MutableLiveData<String>()
    val trackId = MutableLiveData<String>()
    var valid = MediatorLiveData<Boolean>().apply {
        addSource(productName) { value = isValid() }
        addSource(carrierName) { value = isValid() }
        addSource(trackId) { value = isValid() }
    }

    fun observeFinish(lifecycleOwner: LifecycleOwner, observer: (Boolean) -> Unit) =
        finishEvent.observe(lifecycleOwner, observer)

    fun insertButtonClick() {
        showLoading()
        viewModelScope.launch {
            repository.loadDeliveryDataAndInsert(
                productName.value.toString(),
                carrierName.value.toString(),
                trackId.value.toString()
            ) {
                showToast(it)
            }
        }
    }

    private fun isValid() =
        !productName.value.isNullOrEmpty() &&
                !carrierName.value.isNullOrEmpty() &&
                !trackId.value.isNullOrEmpty()

}

