package com.hongbeomi.findtaek.api.client

import com.hongbeomi.findtaek.api.ApiResponse
import com.hongbeomi.findtaek.api.service.DeliveryService
import com.hongbeomi.findtaek.api.async
import com.hongbeomi.findtaek.models.network.ProgressResponse

class ProgressClient (private val service: DeliveryService) {

    fun fetchDelivery(carrierId: String, trackId: String,
                      onResult: (response: ApiResponse<ProgressResponse>) -> Unit) {
        service.getProgressList(carrierId, trackId).async(onResult)
    }

}