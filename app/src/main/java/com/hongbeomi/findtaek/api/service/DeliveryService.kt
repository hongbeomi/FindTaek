package com.hongbeomi.findtaek.api.service

import com.hongbeomi.findtaek.models.network.DeliveryResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path

/**
 * @author hongbeomi
 */

interface DeliveryService {

    @Headers("Content-Type: application/json")
    @GET("carriers/{carrier_id}/tracks/{track_id}")
    fun getData(
        @Path("carrier_id") carrierId: String,
        @Path("track_id") trackId: String
    ): Call<DeliveryResponse>

}