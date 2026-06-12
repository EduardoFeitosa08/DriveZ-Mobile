package com.example.drivez.data.dto

import com.google.gson.annotations.SerializedName

data class Cliente(
    @SerializedName("id_cliente") val idCliente: Long,
    @SerializedName("nome") val nome: String,
    @SerializedName("email") val email: String,
    @SerializedName("telefone") val telefone: String?,
    @SerializedName("img_perfil") val imgPerfil: String?
)

data class ClienteApiResponse(
    @SerializedName("status") val status: Boolean,
    @SerializedName("status_code") val statusCode: Int,
    @SerializedName("response") val clientes: List<Cliente>
)

data class ClienteResponseDto(
    @SerializedName("id_cliente")
    val idCliente: Int?,

    @SerializedName("nome")
    val nome: String?,

    @SerializedName("img_perfil")
    val imgPerfil: String?
)

data class ClienteResponseWrapper(
    @SerializedName("response")
    val response: ClienteResponseDto?
)