package com.hongbeomi.findtaek.api

import com.hongbeomi.findtaek.models.network.DeliveryResponse
import com.hongbeomi.findtaek.models.network.ProgressResponse

class ProgressClient (private val service: DeliveryService) {

    fun fetchDelivery(carrierId: String, trackId: String,
                      onResult: (response: ApiResponse<ProgressResponse>) -> Unit) {
        service.getProgressList(carrierId, trackId).async(onResult)
    }

}