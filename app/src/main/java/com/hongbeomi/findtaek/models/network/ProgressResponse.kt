package com.hongbeomi.findtaek.models.network

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import com.hongbeomi.findtaek.models.NetworkResponseModel

class ProgressResponse(
    @SerializedName("progresses")
    @Expose
    val progresses: ArrayList<Progresses>
) : NetworkResponseModel {
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
}