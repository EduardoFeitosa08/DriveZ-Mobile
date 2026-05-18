package com.example.drivez.data.model

data class Veiculo(
    val id: Int?,
    val titulo: String,
    val placa: String,
    val renavam: String,
    val validadeDocumento: String,
    val categoria: String
) {
    companion object {
        fun criarNovoVazio() = Veiculo(
            id = null,
            titulo = "Novo Veículo",
            placa = "",
            renavam = "",
            validadeDocumento = "",
            categoria = ""
        )
    }
}