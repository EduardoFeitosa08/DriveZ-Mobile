package com.example.drivez.data.model

import com.google.gson.annotations.SerializedName

data class Cliente(
    @SerializedName("id") val id: Int,
    @SerializedName("nome") val nome: String,
    @SerializedName("telefone") val telefone: String,
    @SerializedName("email") val email: String,
    @SerializedName("imgPerfil") val imgPerfil: String,
    @SerializedName("cpf") val cpf: String? = null,
    @SerializedName("cnpj") val cnpj: String? = null,
    @SerializedName("distancia") val distancia: Double
)