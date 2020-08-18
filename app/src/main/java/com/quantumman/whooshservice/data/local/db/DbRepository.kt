package com.quantumman.whooshservice.data.local.db

import com.quantumman.whooshservice.data.model.Result
import com.quantumman.whooshservice.data.model.db.Message
import com.quantumman.whooshservice.ui.model.StatusScooterDataItem
import com.quantumman.whooshservice.util.toListStatusScooterDataItems
import kotlinx.coroutines.channels.ConflatedBroadcastChannel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DbRepository @Inject constructor(private val appDatabase: AppDatabase) : DbDataSource {

    private val chanel = ConflatedBroadcastChannel<Result<List<StatusScooterDataItem>>>(Result.NoItems)

    override fun insertMessage(message: Message): Long = appDatabase.messageDao().insert(message)
    override fun delete(message: Message) = appDatabase.messageDao().delete(message)
    override fun deleteAll() = appDatabase.messageDao().deleteAll()
    override fun findById(id: Long): Result<Message> = try {
        val message = appDatabase.messageDao().findById(id)
        Result.Success(message)
    } catch (e: Exception) {
        Result.Error(e.localizedMessage)
    }

    override suspend fun allMessages(): Flow<List<StatusScooterDataItem>> = appDatabase.messageDao().loadAll().map { it.toListStatusScooterDataItems() }

    fun observe(): Flow<Result<List<StatusScooterDataItem>>> = chanel.asFlow()
}