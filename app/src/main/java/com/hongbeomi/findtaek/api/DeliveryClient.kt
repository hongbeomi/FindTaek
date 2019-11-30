package com.hongbeomi.findtaek.api

import com.hongbeomi.findtaek.models.network.DeliveryResponse

class DeliveryClient(private val service: DeliveryService) {

    fun fetchDelivery(carrierId: String, trackId: String,
                       onResult: (response: ApiResponse<DeliveryResponse>) -> Unit) {
        service.getList(carrierId, trackId).async(onResult)
    }

}