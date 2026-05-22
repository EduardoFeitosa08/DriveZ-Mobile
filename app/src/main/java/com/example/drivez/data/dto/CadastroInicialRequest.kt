package com.example.drivez.data.dto

data class CadastroInicialRequest(
    val nome: String,
    val email: String,
    val telefone: String,
    val tipoUsuario: String
)