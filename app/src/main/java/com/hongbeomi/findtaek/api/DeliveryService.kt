package com.hongbeomi.findtaek.api

import com.hongbeomi.findtaek.models.network.DeliveryResponse
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path

interface DeliveryService {

    @Headers("Content-Type: application/json")
    @GET("carriers/{carrier_id}/tracks/{track_id}")
    fun getList(
        @Path("carrier_id") carrierId: String,
        @Path("track_id") trackId: String
    ): Observable<DeliveryResponse>

}