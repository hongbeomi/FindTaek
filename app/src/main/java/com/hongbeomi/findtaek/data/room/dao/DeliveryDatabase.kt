package com.hongbeomi.findtaek.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.hongbeomi.findtaek.data.room.dao.DeliveryDao
import com.hongbeomi.findtaek.data.room.dao.DATABASE.DATABASE_DELIVERY_VERSION
import com.hongbeomi.findtaek.models.entity.Delivery

/**
 * @author hongbeomi
 */

@Database(entities = [Delivery::class], version = DATABASE_DELIVERY_VERSION, exportSchema = false)
abstract class DeliveryDatabase : RoomDatabase() {

    abstract fun deliveryDao(): DeliveryDao

}