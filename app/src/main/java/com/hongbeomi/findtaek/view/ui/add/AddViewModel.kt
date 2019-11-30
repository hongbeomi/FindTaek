package com.hongbeomi.findtaek.view.ui.add

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.viewModelScope
import com.hongbeomi.findtaek.util.event.FinishActivityEvent
import com.hongbeomi.findtaek.compose.BaseViewModel
import com.hongbeomi.findtaek.repository.DeliveryRepository
import kotlinx.coroutines.launch

class AddViewModel
constructor(private val repository: DeliveryRepository) : BaseViewModel() {

    private val finishEvent = FinishActivityEvent()

    fun observeFinish(lifecycleOwner: LifecycleOwner, observer: (Boolean) -> Unit) {
        finishEvent.observe(lifecycleOwner, observer)
    }

    private fun finishActivity() {
        finishEvent.value = true
    }

    // 유효성 검사 필요!
    fun checkTrackIdAndInsertDelivery(
        inputProductName: String, inputCarrierName: String, inputTrackId: String) {
        if (inputProductName.isNotEmpty() and inputTrackId.isNotEmpty() and inputCarrierName.isNotEmpty()) {
            viewModelScope.launch {
                repository.insert("맥북", "CJ대한통운", "348621627991") {
                    showToast(it)
                }
            }
            finishActivity()
        } else {
            showToast("비어 있는 항목이 있습니다!")
        }
    }

}