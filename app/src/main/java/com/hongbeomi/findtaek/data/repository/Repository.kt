package com.hongbeomi.findtaek.repository

import androidx.lifecycle.LiveData
import com.hongbeomi.findtaek.models.entity.Delivery
import com.hongbeomi.findtaek.models.dto.DeliveryResponse

/**
 * @author hongbeomi
 */

interface Repository {

    suspend fun getData(carrierName: String, trackId: String): DeliveryResponse

    fun getAll(): LiveData<List<Delivery>>

    suspend fun insert(
        trackId: String,
        carrierName: String,
        productName: String,
        deliveryResponse: DeliveryResponse
    )

    suspend fun update(delivery: Delivery)

    suspend fun rollback(delivery: Delivery)

    suspend fun delete(delivery: Delivery)

}