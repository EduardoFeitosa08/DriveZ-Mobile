package com.example.drivez.data.model

data class Prestador(
    val id: Int,
    val nome: String,
    val avaliacao: Double,
    val descricao: String = "",
    val totalAvaliacoes: Int,
    val distancia: Int, //Depois verificar se a API do Google retorna em km ou em metros
    val categorias: List<Categoria>
)

data class Categoria(
    val nome: String
)