package com.hongbeomi.findtaek.di

import com.hongbeomi.findtaek.models.CoroutineDispatcherProvider
import org.koin.dsl.module

/**
 * @author hongbeomi
 */

val coroutineDispatcherProviderModule = module {

    single(createdAtStart = true) { CoroutineDispatcherProvider() }

}