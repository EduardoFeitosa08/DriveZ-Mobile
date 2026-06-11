package com.example.drivez.data.model

import com.google.gson.annotations.SerializedName

data class ApiResponse<T>(
    @SerializedName("status")
    val status: Boolean,
    @SerializedName("status_code")
    val statusCode: Int,
    @SerializedName("response")
    val response: T
)