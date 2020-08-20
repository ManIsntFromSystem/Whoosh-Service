package com.quantumman.whooshservice.data.remote.service

import com.quantumman.whooshservice.data.model.api.MessageResponse
import com.quantumman.whooshservice.data.remote.service.EndPoint.END_POINT_SERVICE
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface ScooterService {
    @GET(END_POINT_SERVICE)
    suspend fun getScooterState(
        @Query("code") code: String,
        @Header("x-api-key") apiKey: String
    ): MessageResponse
}