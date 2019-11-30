package com.hongbeomi.findtaek.view.ui.add

import android.util.Log
import androidx.lifecycle.LifecycleOwner
import com.hongbeomi.findtaek.util.carrierIdMap
import com.hongbeomi.findtaek.models.entity.Delivery
import com.hongbeomi.findtaek.models.network.DeliveryResponse
import com.hongbeomi.findtaek.util.event.FinishActivityEvent
import com.hongbeomi.findtaek.compose.BaseViewModel
import com.hongbeomi.findtaek.util.Mapper
import com.hongbeomi.findtaek.extension.server
import com.hongbeomi.findtaek.extension.with
import com.hongbeomi.findtaek.repository.DeliveryRepository

class AddViewModel
constructor(private val repository: DeliveryRepository) : BaseViewModel(),
    Mapper<DeliveryResponse, Delivery> {

    lateinit var inputProductName: String
    lateinit var inputTrackId: String
    lateinit var inputCarrierName: String
    lateinit var inputCarrierId: String
    private val finishEvent = FinishActivityEvent()

    fun setProductNameInput(inputProductName: String) {
        this.inputProductName = inputProductName
    }

    fun setTrackIdInput(inputTrackId: String) {
        this.inputTrackId = inputTrackId
    }

    fun setCarrierNameInput(inputCarrierName: String) {
        this.inputCarrierName = inputCarrierName
    }

    private fun findCarrierId(inputCarrierName: String) =
        carrierIdMap[inputCarrierName].toString()

    fun setCarrierId() {
        this.inputCarrierId = findCarrierId(inputCarrierName = inputCarrierName)
    }

    fun observeFinish(lifecycleOwner: LifecycleOwner, observer: (Boolean) -> Unit) {
        finishEvent.observe(lifecycleOwner, observer)
    }

    private fun finishActivity() { finishEvent.value = true }

    private fun insertDeliveryFromServer() {
        addToDisposable(
            server.getList(trackId = inputTrackId, carrierId = inputCarrierId).with()
            .subscribe({
                it.run {
                    if (progresses.size > 0) {
                        repository.insert(mapFrom(it))
                        showToast("성공!")
                        finishActivity()
                    } else {
                        showToast("상품이 아직 준비중입니다.")
                    }
                }
            }) {
                Log.e("AddViewModel Error", it.message)
                showToast("통신 상태를 확인해주세요!")
            })
    }

    private fun isNullOrEmptyToTime(toTimeData: String) = toTimeData.isNullOrEmpty()

    private fun decideToTime(toTimeData: String) =
        if(isNullOrEmptyToTime(toTimeData)) "정보없음" else toTimeData.substring(0, 10)

    // 유효성 검사 필요!
    fun carrierIdValidation(inputProductName: String, inputCarrierName: String, inputTrackId: String) =
        if (inputProductName.isNotEmpty() and inputTrackId.isNotEmpty() and inputCarrierName.isNotEmpty()) {
            insertDeliveryFromServer()
        } else {
            showToast("비어 있는 항목이 있습니다!")
        }

    override fun mapFrom(by: DeliveryResponse) =
        Delivery(
            id = id,
            fromName = by.from.name,
            fromTime = by.from.time,
            toName = by.to.name,
            toTime = decideToTime(by.to.time),
            carrierId = inputCarrierId,
            carrierName = by.carrier.name,
            productName = inputProductName,
            trackId = inputTrackId,
            status = by.state.text
        )

}