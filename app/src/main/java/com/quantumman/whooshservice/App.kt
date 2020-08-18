package com.quantumman.whooshservice

import android.app.Application
import com.facebook.stetho.Stetho
import com.quantumman.whooshservice.di.component.AppComponent
import com.quantumman.whooshservice.di.component.DaggerAppComponent

class App : Application() {
    override fun onCreate() {
        super.onCreate()
        initDI()
        initStetho()
    }

    private fun initDI() {
        graph = DaggerAppComponent.builder()
            .appContext(this)
            .build()
    }

    private fun initStetho() {
        Stetho.initializeWithDefaults(this)
    }

    companion object {
        lateinit var graph: AppComponent
    }
}