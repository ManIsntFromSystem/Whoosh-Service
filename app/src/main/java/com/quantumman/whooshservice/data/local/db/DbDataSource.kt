package com.quantumman.whooshservice.data.local.db

import androidx.lifecycle.LiveData
import com.quantumman.whooshservice.data.model.db.Message
import com.quantumman.whooshservice.data.model.Result

interface DbDataSource {
    suspend fun insertMessage(message: Message)
    suspend fun deleteMessage(message: Message)
    suspend fun findById(id: Int): Result<Message>
    fun allMessages(): LiveData<List<Message>>
}