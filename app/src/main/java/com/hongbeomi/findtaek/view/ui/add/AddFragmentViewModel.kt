package com.hongbeomi.findtaek.view.ui.add

import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.hongbeomi.findtaek.data.repository.Repository
import com.hongbeomi.findtaek.models.dto.DeliveryResponse
import com.hongbeomi.findtaek.view.ui.base.BaseViewModel
import com.hongbeomi.findtaek.view.util.SingleLiveEvent
import com.hongbeomi.findtaek.view.util.addSourceList
import kotlinx.coroutines.launch

/**
 * @author hongbeomi
 */

class AddFragmentViewModel(
    private val repository: Repository,
) : BaseViewModel() {

    val productName = MutableLiveData("")
    val carrierName = MutableLiveData<String>()
    val trackId = MutableLiveData<String>()
    val onCloseEvent = SingleLiveEvent<Unit>()

    val deliveryResponse = MutableLiveData<DeliveryResponse?>()

    var isValidInput = MediatorLiveData<Boolean>().apply {
        addSourceList(carrierName, trackId) { isValid() }
    }

    fun callOnClose() = onCloseEvent.call()

    fun onClickInsertButton() {
        showLoading()
        viewModelScope.launch {
            deliveryResponse.value = handle { repository.getData(carrierName.value!!, trackId.value!!) }
            hideLoading()
        }
    }

    fun insert(deliveryResponse: DeliveryResponse) {
        viewModelScope.launch {
            repository.insert(
                trackId.value!!,
                carrierName.value!!,
                productName.value!!,
                deliveryResponse
            )
        }
    }

    private fun isValid() = !carrierName.value.isNullOrEmpty() && !trackId.value.isNullOrEmpty()

}