package com.example.drivez.ui.cadastro

sealed interface CadastroUiState {
    object Idle : CadastroUiState
    object Loading : CadastroUiState
    data class Redirecionar(val tipoUsuario: String, val cadastroCompleto: Boolean, val userId: Int) : CadastroUiState
    data class Erro(val msg: String) : CadastroUiState
}