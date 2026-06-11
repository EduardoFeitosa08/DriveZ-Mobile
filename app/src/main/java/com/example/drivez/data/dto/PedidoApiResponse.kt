package com.example.drivez.data.dto

import com.example.drivez.data.model.Pedido
import com.google.gson.annotations.SerializedName

data class PedidoApiResponse(
    @SerializedName("status") val status: Boolean,
    @SerializedName("status_code") val statusCode: Int,
    @SerializedName("response") val pedidos: List<Pedido>
)