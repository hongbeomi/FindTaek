package com.hongbeomi.findtaek.data_source

import androidx.lifecycle.LiveData
import com.hongbeomi.findtaek.models.entity.Delivery

/**
 * @author hongbeomi
 */

interface LocalDataSource {
    fun getAll() : LiveData<List<Delivery>>
    suspend fun insert(delivery: Delivery)
    suspend fun update(delivery: Delivery)
    suspend fun rollback(delivery: Delivery)
    suspend fun delete(delivery: Delivery)
}