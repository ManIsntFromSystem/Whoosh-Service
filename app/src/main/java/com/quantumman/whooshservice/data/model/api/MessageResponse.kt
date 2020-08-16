package com.quantumman.whooshservice.data.model.api

import com.google.gson.annotations.SerializedName

data class MessageResponse(
    @SerializedName("status") val status: String,
    @SerializedName("comments") val comments: String
)