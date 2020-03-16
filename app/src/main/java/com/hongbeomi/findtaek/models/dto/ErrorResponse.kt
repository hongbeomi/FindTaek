package com.hongbeomi.findtaek.models.entity

import com.google.gson.annotations.SerializedName

/**
 * @author hongbeomi
 */

data class ErrorResponse(
    @SerializedName("message")
    val message: String
) : NetworkResponse