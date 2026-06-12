package com.example.drivez.data.dto

import com.google.gson.annotations.SerializedName

data class PedidoResponseDto(
    @SerializedName("id_pedido")
    val id_pedido: Int?,

    @SerializedName("data_solicitacao")
    val data_solicitacao: String?,

    @SerializedName("endereco_destino")
    val endereco_destino: String?,

    @SerializedName("endereco_origem")
    val endereco_origem: String?,

    @SerializedName("descricao")
    val descricao: String?,

    @SerializedName("distancia_km")
    val distancia_km: String?,

    @SerializedName("id_cliente")
    val id_cliente: Int?,

    @SerializedName("id_prestador")
    val id_prestador: Int?
)

data class ApiResponseWrapper(
    @SerializedName("response")
    val response: List<PedidoResponseDto> = emptyList()
)