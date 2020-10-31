package com.hongbeomi.findtaek.data.room

import com.hongbeomi.findtaek.models.entity.Delivery
import kotlinx.coroutines.flow.Flow

/**
 * @author hongbeomi
 */

interface LocalDataSource {

    fun getAll() : Flow<List<Delivery>>

    suspend fun insert(delivery: Delivery)

    suspend fun updateAll(deliveryList: List<Delivery>)

    suspend fun rollback(delivery: Delivery)

    suspend fun delete(delivery: Delivery)

}