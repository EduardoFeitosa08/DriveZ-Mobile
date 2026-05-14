package com.example.drivez.data.model

data class Cliente (
    val id: Int,
    val nome: String,
    val telefone: String,
    val email: String,
    val imgPerfil: String,
    val cpf: String? = null,
    val cnpj: String? = null,
    val distancia: Double
)