package com.hongbeomi.findtaek.repository.util

import com.hongbeomi.findtaek.models.entity.Delivery
import com.hongbeomi.findtaek.models.network.DeliveryResponse
import com.hongbeomi.findtaek.util.carrierIdMap

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

fun determineTime(toTimeData: String) =
    if (isNullOrEmptyByTime(toTimeData)) "정보없음" else toTimeData.substring(0, 10)

fun isNullOrEmptyByTime(toTimeData: String?) = toTimeData.isNullOrEmpty()

fun convertCarrierId(inputCarrierName: String) = carrierIdMap[inputCarrierName]
