package com.example.drivez.data.model

import kotlinx.serialization.Serializable

@Serializable
data class Contato(
    val id: String,
    val name: String,
    val ultimaMensagem: String,
    val perfilImgUrl: String,
)