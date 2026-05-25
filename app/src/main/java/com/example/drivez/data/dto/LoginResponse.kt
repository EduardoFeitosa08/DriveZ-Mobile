package com.example.drivez.data.dto

data class LoginResponse(
    val idUsuario: Int,
    val tipoUsuario: String,
    val cadastroCompleto: Boolean,
    val nome: String
)