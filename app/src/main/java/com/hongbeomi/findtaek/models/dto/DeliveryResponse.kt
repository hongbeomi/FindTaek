package com.hongbeomi.findtaek.models.dto

import android.os.Parcelable
import com.hongbeomi.findtaek.models.entity.Delivery
import com.hongbeomi.findtaek.view.util.CarrierIdUtil.Companion.convertId
import com.hongbeomi.findtaek.view.util.TimeUtil
import kotlinx.android.parcel.Parcelize

/**
 * @author hongbeomi
 */

data class DeliveryResponse(
    val from: From,
    val to: To,
    val state: State,
    val progresses: ArrayList<Progresses>,
    val carrier: Carrier
) : NetworkResponse {
    data class From(
        val name: String,
        val time: String
    )

    data class To(
        val name: String,
        val time: String
    )

    data class State(
        val text: String
    )

    @Parcelize
    data class Progresses(
        val location: Location,
        val status: Status,
        val time: String,
        val description: String
    ) : Parcelable {
        @Parcelize
        data class Location(
            val name: String
        ) : Parcelable

        @Parcelize
        data class Status(
            val text: String
        ) : Parcelable
    }

    data class Carrier(
        val name: String
    )

    fun toDelivery(id: Long?, carrierName: String, productName: String, trackId: String) =
        Delivery(
            id = id,
            fromName = from.name,
            fromTime = from.time,
            toName = to.name,
            toTime = TimeUtil.determineTime(to.time),
            carrierId = convertId(carrierName)!!,
            carrierName = carrier.name,
            productName = productName,
            trackId = trackId,
            status = state.text
        )

}
