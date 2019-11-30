package com.hongbeomi.findtaek.room

import androidx.lifecycle.LiveData
import androidx.room.*
import com.hongbeomi.findtaek.models.entity.Delivery

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

    @Query("DELETE FROM delivery")
    fun deleteAll()

}