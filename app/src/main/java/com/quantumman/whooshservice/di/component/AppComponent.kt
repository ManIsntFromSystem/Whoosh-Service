package com.quantumman.whooshservice.di.component

import android.content.Context
import com.quantumman.whooshservice.di.module.LocalModule
import com.quantumman.whooshservice.di.module.NetworkModule
import com.quantumman.whooshservice.presenters.HistoryPresenter
import com.quantumman.whooshservice.presenters.ScannerPresenter
import com.quantumman.whooshservice.presenters.SettingsPresenter
import com.quantumman.whooshservice.ui.activities.SplashActivity
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [NetworkModule::class, LocalModule::class])
interface AppComponent {

    fun inject(scannerPresenter : ScannerPresenter)
    fun inject(historyPresenter : HistoryPresenter)
    fun inject(settingsPresenter : SettingsPresenter)
    fun inject(splashActivity: SplashActivity)

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun appContext(context: Context): Builder

        fun build(): AppComponent
    }
}