package com.hongbeomi.findtaek.data.repository

import com.hongbeomi.findtaek.models.dto.DeliveryResponse
import com.hongbeomi.findtaek.models.dto.DeliveryResponse.Progresses
import com.hongbeomi.findtaek.models.entity.Delivery
import kotlinx.coroutines.flow.Flow

/**
 * @author hongbeomi
 */

interface Repository {

    suspend fun getData(carrierName: String, trackId: String): DeliveryResponse

    suspend fun getProgresses(carrierName: String, trackId: String): ArrayList<Progresses>

    fun getAll(): Flow<List<Delivery>>

    suspend fun insert(
        trackId: String,
        carrierName: String,
        productName: String,
        deliveryResponse: DeliveryResponse
    )

    suspend fun updateAll(deliveryList: List<Delivery>)

    suspend fun rollback(delivery: Delivery)

    suspend fun delete(delivery: Delivery)

}