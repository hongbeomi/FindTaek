package com.hongbeomi.findtaek.di

import androidx.room.Room
import com.hongbeomi.findtaek.db.DATABASE.DATABASE_DELIVERY
import com.hongbeomi.findtaek.db.DeliveryDatabase
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module.module

/**
 * @author hongbeomi
 */

val roomModule = module {
    single {
        Room
            .databaseBuilder(androidApplication(), DeliveryDatabase::class.java, DATABASE_DELIVERY)
            .allowMainThreadQueries()
            .fallbackToDestructiveMigration()
            .build()
    }

    single { get<DeliveryDatabase>().deliveryDao() }
}
