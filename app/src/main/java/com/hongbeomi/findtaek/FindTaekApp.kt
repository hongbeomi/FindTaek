package com.hongbeomi.findtaek

import android.app.Application
import com.hongbeomi.findtaek.di.*
import org.koin.android.ext.android.startKoin

/**
 * @author hongbeomi
 */

class FindTaekApp : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin(
            this, listOf(
                networkModule,
                roomModule,
                repositoryModule,
                viewModelModule
            )
        )
    }

}