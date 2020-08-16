package com.quantumman.whooshservice.data.local.db

import androidx.lifecycle.LiveData
import com.quantumman.whooshservice.data.model.Result
import com.quantumman.whooshservice.data.model.db.Message
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DbRepository @Inject constructor(private val appDatabase: AppDatabase) : DbDataSource {

    override suspend fun insertMessage(message: Message) = appDatabase.messageDao().insert(message)
    override suspend fun deleteMessage(message: Message) = appDatabase.messageDao().delete(message)
    override suspend fun findById(id: Int): Result<Message> = try {
        val message = appDatabase.messageDao().findById(id)
        Result.Success(message)
    } catch (e: Exception) {
        Result.Error(e.localizedMessage)
    }
    override fun allMessages(): LiveData<List<Message>> = appDatabase.messageDao().loadAll()
}