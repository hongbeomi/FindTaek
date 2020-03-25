package com.hongbeomi.findtaek.di

import com.hongbeomi.findtaek.data.repository.DeliveryRepository
import com.hongbeomi.findtaek.data.repository.Repository
import org.koin.dsl.module

/**
 * @author hongbeomi
 */

val repositoryModule = module {
    single<Repository> { DeliveryRepository(localDataSource = get(), remoteDataSource = get()) }
}
