package com.hongbeomi.findtaek.models.network

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import com.hongbeomi.findtaek.models.NetworkResponseModel

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
    @Expose
    val progresses: ArrayList<Progresses>,
    @SerializedName("carrier")
    @Expose
    val carrier: Carrier
) : NetworkResponseModel {
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

    data class Progresses(
        @SerializedName("location")
        @Expose
        val location: Location,
        @SerializedName("status")
        @Expose
        val status: Status,
        @SerializedName("time")
        @Expose
        val time: String,
        @SerializedName("description")
        @Expose
        val description: String
    ) {
        data class Location(
            @SerializedName("name")
            @Expose
            val name: String
        )

        data class Status(
            @SerializedName("text")
            @Expose
            val text: String
        )
    }

    data class Carrier(
        @SerializedName("name")
        val name: String
    )
}
