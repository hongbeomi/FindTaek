package com.hongbeomi.findtaek.view.ui.add

import androidx.databinding.ObservableField
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.viewModelScope
import com.hongbeomi.findtaek.util.event.FinishActivityEvent
import com.hongbeomi.findtaek.compose.BaseViewModel
import com.hongbeomi.findtaek.repository.DeliveryRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

/**
 * @author hongbeomi
 */
class AddViewModel
constructor(private val repository: DeliveryRepository) : BaseViewModel() {

    private val finishEvent = FinishActivityEvent()
    val productName = ObservableField<String>()
    val carrierName = ObservableField<String>()
    val trackId = ObservableField<String>()

    fun observeFinish(lifecycleOwner: LifecycleOwner, observer: (Boolean) -> Unit) {
        finishEvent.observe(lifecycleOwner, observer)
    }

    private fun finishActivity() {
        finishEvent.value = true
    }

    private fun isInputNullOrBlank() =
        productName.get().isNullOrBlank() or
        carrierName.get().isNullOrBlank() or
        trackId.get().isNullOrBlank()

    fun insertDelivery() {
        if (isInputNullOrBlank()) {
            showToast("비어 있는 항목이 있습니다!")
        } else {
            viewModelScope.launch(Dispatchers.IO) {
                repository.insert("맥북", "CJ대한통운", "348621627991") {
                    showToast(it)
                }
            }
            finishActivity()
        }
    }

}