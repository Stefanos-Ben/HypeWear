package com.stephben.hypewear.app

import android.app.Application
import com.stephben.hypewear.di.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin


class HypeWearApp: Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@HypeWearApp)
            modules(appModule)
        }
    }
}