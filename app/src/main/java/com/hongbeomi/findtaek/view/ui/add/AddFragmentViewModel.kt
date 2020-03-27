package com.hongbeomi.findtaek.view.ui.add

import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import com.hongbeomi.findtaek.data.repository.Repository
import com.hongbeomi.findtaek.models.dto.DeliveryResponse
import com.hongbeomi.findtaek.view.ui.base.BaseViewModel
import com.hongbeomi.findtaek.view.util.SingleLiveEvent

/**
 * @author hongbeomi
 */

class AddFragmentViewModel(
    private val repository: Repository
) : BaseViewModel() {

    val productName = MutableLiveData<String>()
    val carrierName = MutableLiveData<String>()
    val trackId = MutableLiveData<String>()
    val onCloseEvent = SingleLiveEvent<Unit>()

    val deliveryResponse = MutableLiveData<DeliveryResponse?>()

    var valid = MediatorLiveData<Boolean>().apply {
        addSource(productName) { value = isValid() }
        addSource(carrierName) { value = isValid() }
        addSource(trackId) { value = isValid() }
    }

    fun callOnClose() = onCloseEvent.call()

    fun onClickInsertButton() {
        showLoading()
        launchViewModelScope {
            deliveryResponse.postValue(
                handle { repository.getData(carrierName.value!!, trackId.value!!) }
            )
        }
    }

    fun insert(deliveryResponse: DeliveryResponse) =
        launchViewModelScope {
            repository.insert(
                trackId.value!!,
                carrierName.value!!,
                productName.value!!,
                deliveryResponse
            )
        }

    private fun isValid() = !productName.value.isNullOrEmpty() &&
            !carrierName.value.isNullOrEmpty() &&
            !trackId.value.isNullOrEmpty()

}