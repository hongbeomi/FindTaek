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
    fun insertItem(delivery: Delivery)

    @Update
    fun updateItem(delivery: Delivery)

    @Delete
    fun deleteItem(delivery: Delivery)

}