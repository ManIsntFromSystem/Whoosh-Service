package com.quantumman.whooshservice.data.local.db

import com.quantumman.whooshservice.data.model.Result
import com.quantumman.whooshservice.data.model.db.Message
import com.quantumman.whooshservice.ui.model.StatusScooterDataItem
import kotlinx.coroutines.flow.Flow

interface DbDataSource {
    fun insertMessage(message: Message): Long
    fun delete(message: Message)
    fun deleteAll()
    fun findById(id: Long): Result<Message>
    suspend fun allMessages(): Flow<List<StatusScooterDataItem>>
}