package com.hongbeomi.findtaek.data.network

import com.hongbeomi.findtaek.models.dto.DeliveryResponse
import retrofit2.http.GET
import retrofit2.http.Path

/**
 * @author hongbeomi
 */

interface DeliveryService {

    @GET("carriers/{carrier_id}/tracks/{track_id}")
    suspend fun getData(
        @Path("carrier_id") carrierId: String,
        @Path("track_id") trackId: String
    ): DeliveryResponse

}