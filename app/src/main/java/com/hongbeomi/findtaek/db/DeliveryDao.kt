package com.hongbeomi.findtaek.db

import androidx.lifecycle.LiveData
import androidx.room.*
import com.hongbeomi.findtaek.models.entity.Delivery

/**
 * @author hongbeomi
 */

@Dao
interface DeliveryDao {

    @Query("SELECT * FROM delivery")
    fun getAll(): LiveData<List<Delivery>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(delivery: Delivery)

    @Update
    fun update(delivery: Delivery)

    @Delete
    fun delete(delivery: Delivery)

}