package com.example.drivez.ui.login

sealed interface LoginUiState {
    object Idle : LoginUiState
    object Loading : LoginUiState
    data class Sucesso(
        val userId: Int,
        val tipoUsuario: String,
        val cadastroCompleto: Boolean
    ) : LoginUiState
    data class Erro(val mensagem: String) : LoginUiState
}