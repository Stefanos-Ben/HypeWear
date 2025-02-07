package com.stephben.hypewear

import android.app.Application
import com.google.firebase.FirebaseApp
import com.stephben.hypewear.di.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class HypeWearApp: Application() {
    override fun onCreate() {
        super.onCreate()
        FirebaseApp.initializeApp(this)

        startKoin{
            androidContext(this@HypeWearApp)
            modules(
                appModule
            )
        }
    }
}