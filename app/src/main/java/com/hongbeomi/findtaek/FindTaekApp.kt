package com.hongbeomi.findtaek

import android.app.Application
import com.hongbeomi.findtaek.di.*
import org.koin.android.ext.android.startKoin

class FindTaekApp : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin(this, listOf(
            networkModule,
            apiModule,
            roomModule,
            repositoryModule,
            viewModelModule
        ))
    }

}