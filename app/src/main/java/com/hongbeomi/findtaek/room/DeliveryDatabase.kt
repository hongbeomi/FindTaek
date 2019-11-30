package com.hongbeomi.findtaek.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.hongbeomi.findtaek.room.DATABASE.DATABASE_DELIVERY
import com.hongbeomi.findtaek.room.DATABASE.DATABASE_DELIVERY_VERSION
import com.hongbeomi.findtaek.models.entity.Delivery

@Database(entities = [Delivery::class], version = DATABASE_DELIVERY_VERSION, exportSchema = false)
abstract class DeliveryDatabase : RoomDatabase() {

    abstract fun deliveryDao(): DeliveryDao

    companion object {

        @Volatile
        private var INSTANCE: DeliveryDatabase? = null

        fun getInstance(context: Context): DeliveryDatabase {
            if (INSTANCE == null) {
                synchronized(DeliveryDatabase::class) {
                    INSTANCE = Room.databaseBuilder(
                        context.applicationContext, DeliveryDatabase::class.java, DATABASE_DELIVERY
                    ).fallbackToDestructiveMigration().build()
                }
            }
            return INSTANCE!!
        }

    }

}