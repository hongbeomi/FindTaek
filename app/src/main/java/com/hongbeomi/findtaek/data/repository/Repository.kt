package com.hongbeomi.findtaek.data.repository

import androidx.lifecycle.LiveData
import com.hongbeomi.findtaek.models.dto.DeliveryResponse
import com.hongbeomi.findtaek.models.dto.DeliveryResponse.Progresses
import com.hongbeomi.findtaek.models.entity.Delivery

/**
 * @author hongbeomi
 */

interface Repository {

    suspend fun getData(carrierName: String, trackId: String): DeliveryResponse

    suspend fun getProgresses(carrierName: String, trackId: String): ArrayList<Progresses>

    fun getAll(): LiveData<List<Delivery>>

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