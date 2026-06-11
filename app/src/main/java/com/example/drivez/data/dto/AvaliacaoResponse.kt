package com.example.drivez.data.dto

import com.google.gson.annotations.SerializedName

data class ApiResponseAvaliacao(
    @SerializedName("status_code") val statusCode: Int,
    val message: String,
    val dados: MetricasClienteDto
)