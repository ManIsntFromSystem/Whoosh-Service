package com.quantumman.whooshservice.di.component

import android.content.Context
import com.quantumman.whooshservice.di.module.LocalModule
import com.quantumman.whooshservice.di.module.NetworkModule
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [NetworkModule::class, LocalModule::class])
interface AppComponent {

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun appContext(context: Context): Builder

        fun build(): AppComponent
    }
}