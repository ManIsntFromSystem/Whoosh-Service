package com.quantumman.whooshservice.data.local.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.quantumman.whooshservice.data.local.db.dao.MessageDao
import com.quantumman.whooshservice.data.model.db.Message

@Database(entities = [Message::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun messageDao(): MessageDao
}