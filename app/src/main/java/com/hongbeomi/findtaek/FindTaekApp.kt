package com.hongbeomi.findtaek

import android.app.Application
import android.content.Context
import com.hongbeomi.findtaek.di.*
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

/**
 * @author hongbeomi
 */

@Suppress("unused")
class FindTaekApp : Application() {

    companion object {
        lateinit var applicationCtx: Context
    }

    override fun onCreate() {
        super.onCreate()
        applicationCtx = applicationContext

        startKoin {
            androidContext(this@FindTaekApp)
            modules(
                listOf(
                    coroutineDispatcherProviderModule,
                    dataSourceModule,
                    networkModule,
                    roomModule,
                    repositoryModule,
                    viewModelModule
                )
            )
        }
    }

}