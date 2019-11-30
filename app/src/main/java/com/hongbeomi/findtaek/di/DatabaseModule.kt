package com.hongbeomi.findtaek.di

import com.hongbeomi.findtaek.room.DeliveryDatabase
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module.module

val roomModule = module {
    single { DeliveryDatabase.getInstance(androidApplication()) }
    single(createOnStart = true) { get<DeliveryDatabase>().deliveryDao() }
}