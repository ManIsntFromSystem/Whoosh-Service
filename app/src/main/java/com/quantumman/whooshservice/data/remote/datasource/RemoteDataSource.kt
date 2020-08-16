package com.quantumman.whooshservice.data.remote.datasource

import com.quantumman.whooshservice.data.model.api.MessageResponse
import com.quantumman.whooshservice.data.model.Result

interface RemoteDataSource {
    suspend fun getMessage(code: String): Result<MessageResponse>
}