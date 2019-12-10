package com.hongbeomi.findtaek.models.network

import com.google.gson.annotations.SerializedName
import com.hongbeomi.findtaek.models.NetworkResponseModel

class ErrorResponse(
    @SerializedName("message")
    val message: String
): NetworkResponseModel