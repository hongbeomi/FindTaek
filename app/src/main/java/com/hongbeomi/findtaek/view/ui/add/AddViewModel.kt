package com.hongbeomi.findtaek.view.ui.add

import android.util.Log
import androidx.databinding.ObservableField
import androidx.lifecycle.*
import com.hongbeomi.findtaek.util.event.FinishActivityEvent
import com.hongbeomi.findtaek.compose.BaseViewModel
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

    val productName = ObservableField<String>()
    val carrierName = ObservableField<String>()
    val trackId = ObservableField<String>()

    fun observeFinish(lifecycleOwner: LifecycleOwner, observer: (Boolean) -> Unit) {
        finishEvent.observe(lifecycleOwner, observer)
    }

    private fun isInputNullOrBlank() =
        productName.get()?.trim().isNullOrBlank() or
                carrierName.get()?.trim().isNullOrBlank() or
                trackId.get()?.trim().isNullOrBlank()

    // TODO 디자인 받으면 코드 구현 필요
    fun insertDelivery() {
//        if (isInputNullOrBlank()) {
//            showToast("비어 있는 항목이 있습니다!")
//        } else {

        viewModelScope.launch(Dispatchers.IO) {
            repository.insert("맥북", "CJ대한통운", "348621627991") {
                showToast(it)
            }
        }
//        }
    }

}