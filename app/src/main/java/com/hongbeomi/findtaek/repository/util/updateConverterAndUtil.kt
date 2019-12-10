package com.hongbeomi.findtaek.repository.util

import com.hongbeomi.findtaek.models.entity.Delivery
import com.hongbeomi.findtaek.models.network.DeliveryResponse

/**
 * @author hongbeomi
 */
fun updateConverter(delivery: Delivery, data: DeliveryResponse) =
    Delivery(
        id = delivery.id,
        fromName = data.from.name,
        fromTime = data.from.time,
        toName = data.to.name,
        toTime = decideToTime(data.to.time),
        carrierId = delivery.carrierId,
        carrierName = data.carrier.name,
        productName = delivery.productName,
        trackId = delivery.trackId,
        status = data.state.text
    )

fun decideToTime(toTimeData: String) =
    if (isNullOrEmptyToTime(toTimeData)) "정보없음" else toTimeData.substring(0, 10)

fun isNullOrEmptyToTime(toTimeData: String?) = toTimeData.isNullOrEmpty()