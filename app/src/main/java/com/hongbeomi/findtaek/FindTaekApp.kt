package com.hongbeomi.findtaek

import android.app.Application
import com.hongbeomi.findtaek.di.*
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

/**
 * @author hongbeomi
 */
@Suppress("unused")
class FindTaekApp : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@FindTaekApp)
            modules(
                listOf(
                    networkModule,
                    roomModule,
                    repositoryModule,
                    viewModelModule
                )
            )
        }
    }

}