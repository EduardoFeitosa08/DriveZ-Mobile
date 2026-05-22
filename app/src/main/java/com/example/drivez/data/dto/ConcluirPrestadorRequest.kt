package com.example.drivez.data.dto

import com.example.drivez.data.model.Categoria

data class ConcluirPrestadorRequest(
    val descricao: String,
    val categorias: List<Categoria>
)