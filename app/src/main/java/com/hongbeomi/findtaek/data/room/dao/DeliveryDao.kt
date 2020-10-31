package com.hongbeomi.findtaek.data.room.dao

import androidx.room.Dao
import androidx.room.Query
import com.hongbeomi.findtaek.models.entity.Delivery
import kotlinx.coroutines.flow.Flow

/**
 * @author hongbeomi
 */

@Dao
abstract class DeliveryDao : BaseDao<Delivery>() {

    @Query("SELECT * FROM delivery")
    abstract fun getAll(): Flow<List<Delivery>>

}