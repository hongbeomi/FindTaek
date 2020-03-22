package com.hongbeomi.findtaek.data.room

import androidx.lifecycle.LiveData
import com.hongbeomi.findtaek.models.entity.Delivery

/**
 * @author hongbeomi
 */

interface LocalDataSource {

    fun getAll() : LiveData<List<Delivery>>

    suspend fun insert(delivery: Delivery)

    suspend fun updateAll(deliveryList: List<Delivery>)

    suspend fun rollback(delivery: Delivery)

    suspend fun delete(delivery: Delivery)

}