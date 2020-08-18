package com.quantumman.whooshservice.di.component

import android.content.Context
import com.quantumman.whooshservice.di.module.LocalModule
import com.quantumman.whooshservice.di.module.NetworkModule
import com.quantumman.whooshservice.presenters.HistoryPresenter
import com.quantumman.whooshservice.presenters.ScannerPresenter
import com.quantumman.whooshservice.presenters.SettingsPresenter
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [NetworkModule::class, LocalModule::class])
interface AppComponent {

    fun inject(scannerPresenter : ScannerPresenter)
    fun inject(historyPresenter : HistoryPresenter)
    fun inject(settingsPresenter : SettingsPresenter)

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun appContext(context: Context): Builder

        fun build(): AppComponent
    }
}