package com.stephben.hypewear.di

import android.app.Application
import com.google.firebase.FirebaseApp

class HypeWearApp: Application() {
    override fun onCreate() {
        super.onCreate()
        FirebaseApp.initializeApp(this)
    }
}