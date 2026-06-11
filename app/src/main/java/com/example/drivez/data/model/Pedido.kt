package com.example.drivez.data.model

import com.google.gson.annotations.SerializedName

data class Pedido(
    @SerializedName("id_pedido") val id: Long,
    @SerializedName("id_cliente") val clienteId: Long,
    @SerializedName("id_prestador") val prestadorId: Long,
    @SerializedName("descricao") val descricao: String,
    @SerializedName("distancia_km") val distanciaKm: String,
    @SerializedName("endereco_origem") val enderecoOrigem: String,
    @SerializedName("endereco_destino") val enderecoDestino: String,
    @SerializedName("data_solicitacao") val dataSolicitacao: String
)