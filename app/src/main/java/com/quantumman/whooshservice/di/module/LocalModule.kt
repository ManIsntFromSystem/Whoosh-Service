package com.quantumman.whooshservice.di.module

import android.content.Context
import androidx.room.Room
import com.quantumman.whooshservice.data.local.db.AppDatabase
import com.quantumman.whooshservice.util.AppContract.DB_NAME
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class  LocalModule {
    @Provides
    @Singleton
    fun provideAppDatabase(context: Context): AppDatabase {
        return Room.databaseBuilder(context, AppDatabase::class.java, DB_NAME)
            .fallbackToDestructiveMigration()
            .allowMainThreadQueries()
            .build()
    }
}