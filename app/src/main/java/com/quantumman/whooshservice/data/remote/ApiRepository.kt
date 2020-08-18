package com.quantumman.whooshservice.data.remote

import com.quantumman.whooshservice.data.model.Result
import com.quantumman.whooshservice.data.model.api.MessageResponse
import com.quantumman.whooshservice.data.remote.datasource.RemoteDataSource
import com.quantumman.whooshservice.data.remote.service.ScooterService
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ApiRepository @Inject constructor(
    private val api: ScooterService
) : RemoteDataSource {

    override fun getMessage(code: String, apiKey: String): Result<MessageResponse> {
        return try {
            val messageResponse = api.getScooterState(apiKey = apiKey, code = code)
            Result.Success(messageResponse)
        } catch (e: Exception) {
            Result.Error(e.localizedMessage)
        }
    }
}