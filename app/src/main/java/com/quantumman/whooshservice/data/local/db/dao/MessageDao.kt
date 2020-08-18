package com.quantumman.whooshservice.data.local.db.dao

import androidx.room.*
import com.quantumman.whooshservice.data.model.db.Message
import kotlinx.coroutines.flow.Flow

@Dao
interface MessageDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(message: Message): Long

    @Query("SELECT * FROM message WHERE id = :id")
    fun findById(id: Long): Message

    @Query("SELECT * FROM message ORDER BY date DESC")
    fun loadAll(): Flow<List<Message>>

    @Delete
    fun delete(message: Message)

    @Query("DELETE FROM message")
    fun deleteAll()
}
