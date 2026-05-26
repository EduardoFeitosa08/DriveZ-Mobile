package com.example.drivez.data.model

data class Prestador(
    val id: Int,
    val nome: String,
    val avaliacao: Double,
    val descricao: String = "",
    val perfilImgUrl: String = "",
    val totalAvaliacoes: Int,
    val categorias: List<Categoria>
)

data class Categoria(
    val nome: String
)