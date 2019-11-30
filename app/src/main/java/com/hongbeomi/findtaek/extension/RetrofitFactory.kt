package com.hongbeomi.findtaek.extension

import com.hongbeomi.findtaek.api.DeliveryService
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import java.util.concurrent.TimeUnit

fun retrofitFactory(): Retrofit.Builder =
    Retrofit.Builder()
        .client(
            createOkHttpClient(
                createInterceptor()
            )
        )
        .baseUrl("https://apis.tracker.delivery/")
        .addCallAdapterFactory(RxJava2CallAdapterFactory.createAsync())
        .addConverterFactory(GsonConverterFactory.create())

fun createOkHttpClient(interceptor: HttpLoggingInterceptor): OkHttpClient =
    OkHttpClient.Builder()
        .connectTimeout(60, TimeUnit.SECONDS)
        .readTimeout(60, TimeUnit.SECONDS)
        .writeTimeout(60, TimeUnit.SECONDS)
        .addInterceptor(interceptor).build()

fun createInterceptor(): HttpLoggingInterceptor =
    HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }

var server: DeliveryService = retrofitFactory()
    .build().create(
    DeliveryService::class.java)