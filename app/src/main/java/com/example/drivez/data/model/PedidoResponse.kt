package com.example.drivez.data.model

import com.google.gson.annotations.SerializedName

data class PedidoResponse(
    @SerializedName("id")
    val id: Int,

    @SerializedName("cliente_id")
    val clienteId: Int,

    @SerializedName("distancia")
    val distancia: Double?,

    @SerializedName("descricao_servico")
    val descricaoServico: String? = null,

    @SerializedName("status")
    val status: String? = null
)