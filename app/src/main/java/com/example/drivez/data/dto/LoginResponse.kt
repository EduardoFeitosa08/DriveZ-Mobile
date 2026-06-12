package com.example.drivez.data.dto

import com.google.gson.annotations.SerializedName

data class LoginResponse(
    val idUsuario: Int,
    val tipoUsuario: String,
    val cadastroCompleto: Boolean,
    val nome: String
)
