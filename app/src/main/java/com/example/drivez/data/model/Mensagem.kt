package com.example.drivez.data.model

import kotlinx.serialization.Serializable

@Serializable
data class Mensagem(
    val id: String,
    val contatoId: String,
    val remententeId: String,
    val texto: String?,
    val imgUrl: String? = null,
    val horario: String,
    val status: StatusMensagem
)