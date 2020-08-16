package com.quantumman.whooshservice.data.remote

import android.content.SharedPreferences
import com.quantumman.whooshservice.data.model.api.MessageResponse
import com.quantumman.whooshservice.data.model.Result
import com.quantumman.whooshservice.data.remote.datasource.RemoteDataSource
import com.quantumman.whooshservice.data.remote.service.ScooterService
import com.quantumman.whooshservice.util.AppContract.PREF_API_KEY
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ApiRepository @Inject constructor(
    private val api: ScooterService,
    private val pref: SharedPreferences
) : RemoteDataSource {

    override suspend fun getMessage(code: String): Result<MessageResponse> {
        return try {
            val apiKey = pref.getString(PREF_API_KEY, null) ?: ""
            val messageResponse = api.getScooterState(apiKey = apiKey, code = code)
            Result.Success(messageResponse)
        } catch (e: Exception) {
            Result.Error(e.localizedMessage)
        }
    }
}