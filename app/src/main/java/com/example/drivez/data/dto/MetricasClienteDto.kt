package com.example.drivez.data.dto

import com.google.gson.annotations.SerializedName

data class MetricasClienteDto(
    @SerializedName("id_prestador") val idPrestador: String,
    @SerializedName("total_avaliacoes") val totalAvaliacoes: Int,
    @SerializedName("media_nota") val mediaNota: String
)