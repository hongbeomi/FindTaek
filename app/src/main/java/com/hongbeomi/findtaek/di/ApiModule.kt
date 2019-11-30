package com.hongbeomi.findtaek.di

import com.hongbeomi.findtaek.api.service.DeliveryService
import org.koin.dsl.module.module
import retrofit2.Retrofit

val apiModule = module {
    single(createOnStart = false) { get<Retrofit>().create(DeliveryService::class.java) }
}