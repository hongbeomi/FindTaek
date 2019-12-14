package com.hongbeomi.findtaek.view.ui.add

import androidx.lifecycle.*
import com.hongbeomi.findtaek.util.event.FinishActivityEvent
import com.hongbeomi.findtaek.core.BaseViewModel
import com.hongbeomi.findtaek.repository.DeliveryRepository
import kotlinx.coroutines.*

/**
 * @author hongbeomi
 */

class AddViewModel
constructor(private val repository: DeliveryRepository) : BaseViewModel() {

    companion object {
        val finishEvent = FinishActivityEvent()
    }

    val productName = MutableLiveData<String>("")
    val carrierName = MutableLiveData<String>("")
    val trackId = MutableLiveData<String>("")
    var valid = MediatorLiveData<Boolean>().apply {
        addSource(productName) { value = isValid() }
        addSource(carrierName) { value = isValid() }
        addSource(trackId) { value = isValid() }
    }

    fun observeFinish(lifecycleOwner: LifecycleOwner, observer: (Boolean) -> Unit) {
        finishEvent.observe(lifecycleOwner, observer)
    }

    fun insertButtonClick() {
        viewModelScope.launch(Dispatchers.IO) {
            repository.insert("맥", "CJ대한통운", "344054765256") {
                showToast(it)
            }
        }
    }

    private fun isValid(): Boolean {
        return productName.value?.length!! > 0 &&
                carrierName.value?.length!! > 0 &&
                trackId.value?.length!! > 0
    }

}

