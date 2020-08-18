package com.quantumman.whooshservice.data.remote.datasource

import com.quantumman.whooshservice.data.model.Result
import com.quantumman.whooshservice.data.model.api.MessageResponse

interface RemoteDataSource {
    fun getMessage(code: String, apiKey: String): Result<MessageResponse>
}