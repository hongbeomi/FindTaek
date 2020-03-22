package com.hongbeomi.findtaek.di

import com.hongbeomi.findtaek.data.network.DeliveryRemoteDataSource
import com.hongbeomi.findtaek.data.network.RemoteDataSource
import com.hongbeomi.findtaek.data.room.DeliveryLocalDataSource
import com.hongbeomi.findtaek.data.room.LocalDataSource
import org.koin.dsl.module

/**
 * @author hongbeomi
 */


val dataSourceModule = module {

    single<LocalDataSource> { DeliveryLocalDataSource(deliveryDao = get()) }
    single<RemoteDataSource> { DeliveryRemoteDataSource(deliveryService = get()) }

}