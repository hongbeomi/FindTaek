package com.hongbeomi.findtaek.data.room.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query
import com.hongbeomi.findtaek.models.entity.Delivery

/**
 * @author hongbeomi
 */

@Dao
abstract class DeliveryDao : BaseDao<Delivery>() {

    @Query("SELECT * FROM delivery")
    abstract fun getAll(): LiveData<List<Delivery>>

}