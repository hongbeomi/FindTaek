package com.hongbeomi.findtaek.repository.util

import com.hongbeomi.findtaek.models.entity.Delivery
import com.hongbeomi.findtaek.models.network.DeliveryResponse
import com.hongbeomi.findtaek.util.carrierIdMap
import com.hongbeomi.findtaek.view.ui.add.AddViewModel

/**
 * @author hongbeomi
 */

fun makeUpdateDelivery(delivery: Delivery, data: DeliveryResponse) =
    Delivery(
        id = delivery.id,
        fromName = data.from.name,
        fromTime = data.from.time,
        toName = data.to.name,
        toTime = determineTime(data.to.time),
        carrierId = delivery.carrierId,
        carrierName = data.carrier.name,
        productName = delivery.productName,
        trackId = delivery.trackId,
        status = data.state.text
    )

// TODO 빈 데이터가 오는지 null로 오는지 확인 필요

fun determineTime(toTimeData: String) =
    if (isNullOrEmptyByTime(toTimeData)) "정보없음" else toTimeData.substring(0, 10)

fun isNullOrEmptyByTime(toTimeData: String?) = toTimeData.isNullOrEmpty()

fun convertCarrierId(inputCarrierName: String) = carrierIdMap[inputCarrierName].toString()

fun finishActivity() { AddViewModel.finishEvent.value = true }
