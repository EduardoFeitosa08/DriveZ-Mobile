package com.example.drivez.data.model

import com.google.gson.annotations.SerializedName

data class ClienteDetalhesResponse(
    @SerializedName("id_cliente")
    val id: Int,

    @SerializedName("nome")
    val nome: String?,

    @SerializedName("telefone")
    val telefone: String?,

    @SerializedName("email")
    val email: String?,

    @SerializedName("img_perfil")
    val imgPerfil: String?,

    @SerializedName("cpf")
    val cpf: String? = null,

    @SerializedName("cnpj")
    val cnpj: String? = null
)