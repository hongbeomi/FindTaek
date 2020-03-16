package com.hongbeomi.findtaek.models.entity

import android.os.Parcelable
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import com.hongbeomi.findtaek.view.util.CarrierIdUtil
import com.hongbeomi.findtaek.view.util.TimeUtil
import kotlinx.android.parcel.Parcelize

/**
 * @author hongbeomi
 */


data class DeliveryResponse(
    @SerializedName("from")
    val from: From,
    @SerializedName("to")
    @Expose
    val to: To,
    @SerializedName("state")
    @Expose
    val state: State,
    @SerializedName("progresses")
    val progresses: ArrayList<Progresses>,
    @SerializedName("carrier")
    @Expose
    val carrier: Carrier
) : NetworkResponse {
    data class From(
        @SerializedName("name")
        val name: String,
        @SerializedName("time")
        val time: String
    )

    data class To(
        @SerializedName("name")
        @Expose
        val name: String,
        @SerializedName("time")
        @Expose
        val time: String
    )

    data class State(
        @SerializedName("text")
        @Expose
        val text: String
    )

    @Parcelize
    data class Progresses(
        @SerializedName("location")
        val location: Location,
        @SerializedName("status")
        val status: Status,
        @SerializedName("time")
        val time: String,
        @SerializedName("description")
        val description: String
    ) : Parcelable {
        @Parcelize
        data class Location(
            @SerializedName("name")
            val name: String
        ) : Parcelable

        @Parcelize
        data class Status(
            @SerializedName("text")
            val text: String
        ) : Parcelable
    }

    data class Carrier(
        @SerializedName("name")
        val name: String
    )

    fun toDelivery(carrierName: String, productName: String, trackId: String) =
        Delivery(
            id = null,
            fromName = from.name,
            fromTime = from.time,
            toName = to.name,
            toTime = TimeUtil.determineTime(to.time),
            carrierId = CarrierIdUtil().convertId(carrierName)!!,
            carrierName = carrier.name,
            productName = productName,
            trackId = trackId,
            status = state.text
        )

}
