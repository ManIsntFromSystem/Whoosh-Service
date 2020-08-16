package com.quantumman.whooshservice.data.remote.service

import com.quantumman.whooshservice.data.model.api.MessageResponse
import com.quantumman.whooshservice.data.remote.service.EndPoint.END_POINT_SERVICE
import retrofit2.http.GET
import retrofit2.http.Query

interface ScooterService {
    @GET(END_POINT_SERVICE)
    fun getScooterState(
        @Query("api-key") apiKey: String,
        @Query("code") code: String
    ): MessageResponse
}