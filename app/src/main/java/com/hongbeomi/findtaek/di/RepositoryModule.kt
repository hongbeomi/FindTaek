package com.hongbeomi.findtaek.di

import com.hongbeomi.findtaek.repository.DeliveryRepository
import org.koin.dsl.module.module

val repositoryModule = module {
    single { DeliveryRepository(get()) }
}
