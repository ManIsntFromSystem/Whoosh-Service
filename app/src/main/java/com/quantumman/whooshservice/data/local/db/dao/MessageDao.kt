package com.quantumman.whooshservice.data.local.db.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.quantumman.whooshservice.data.model.db.Message

@Dao
interface MessageDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(message: Message)

    @Delete
    suspend fun delete(message: Message)

    @Query("SELECT * FROM message WHERE id = :id")
    suspend fun findById(id: Int): Message

    @Query("SELECT * FROM message")
    fun loadAll(): LiveData<List<Message>>
}