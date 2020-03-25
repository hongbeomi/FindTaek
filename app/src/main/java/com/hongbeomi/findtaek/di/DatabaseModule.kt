package com.hongbeomi.findtaek.di

import androidx.room.Room
import com.hongbeomi.findtaek.data.room.dao.DATABASE.DATABASE_DELIVERY
import com.hongbeomi.findtaek.data.room.dao.DeliveryDatabase
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module

/**
 * @author hongbeomi
 */

val roomModule = module {
    single {
        Room.databaseBuilder(androidApplication(), DeliveryDatabase::class.java, DATABASE_DELIVERY)
            .allowMainThreadQueries()
            .fallbackToDestructiveMigration()
            .build()
    }
    single { get<DeliveryDatabase>().deliveryDao() }
}
