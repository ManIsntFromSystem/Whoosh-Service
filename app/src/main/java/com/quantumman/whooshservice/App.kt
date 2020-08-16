package com.quantumman.whooshservice

import android.app.Application
import com.quantumman.whooshservice.di.DI
import com.quantumman.whooshservice.di.component.DaggerAppComponent

class App : Application() {
    override fun onCreate() {
        super.onCreate()
        initDI()
    }

    private fun initDI() {
        DI.appComponent = DaggerAppComponent.builder()
            .appContext(this)
            .build()
    }
}