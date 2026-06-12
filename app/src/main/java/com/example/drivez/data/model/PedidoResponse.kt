package com.example.drivez.data.model

import com.google.gson.annotations.SerializedName

data class PedidoResponse(
    @SerializedName("id_pedido")
    val id: Int,

    @SerializedName("id_cliente")
    val clienteId: Int,

    @SerializedName("id_prestador")
    val prestadorId: Int?,

    @SerializedName("distancia_km")
    val distancia: String?,

    @SerializedName("descricao")
    val descricaoServico: String? = null,

    @SerializedName("data_solicitacao")
    val dataSolicitacao: String? = null,

    @SerializedName("endereco_origem")
    val enderecoOrigem: String? = null,

    @SerializedName("endereco_destino")
    val enderecoDestino: String? = null,

    @SerializedName("status")
    val status: String? = null,

    @SerializedName("nome_prestador")
    val prestadorNome: String? = null,

    @SerializedName("nome_cliente")
    val clienteNome: String? = null
)