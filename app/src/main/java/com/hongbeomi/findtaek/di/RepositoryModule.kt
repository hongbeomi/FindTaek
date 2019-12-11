package com.hongbeomi.findtaek.di

import com.hongbeomi.findtaek.repository.DeliveryRepository
import com.hongbeomi.findtaek.repository.ProgressRepository
import com.hongbeomi.findtaek.view.ui.add.AddActivity
import org.koin.dsl.module.module

/**
 * @author hongbeomi
 */

val repositoryModule = module {
    single { DeliveryRepository(get(), get()) }
    single { ProgressRepository(get()) }
}
