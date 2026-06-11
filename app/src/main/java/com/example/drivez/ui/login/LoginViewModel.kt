package com.example.drivez.ui.login

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.drivez.data.repository.AuthRepository
import kotlinx.coroutines.launch

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class LoginViewModel(
    private val repository: AuthRepository = AuthRepository()
) : ViewModel() {

    // Usa a sua sealed interface LoginUiState
    var uiState: LoginUiState by mutableStateOf(LoginUiState.Idle)
        private set

    fun realizarLogin(email: String, senha: String) {
        if (email.isBlank() || senha.isBlank()) {
            uiState = LoginUiState.Erro("Preencha todos os campos.")
            return
        }

        viewModelScope.launch {
            uiState = LoginUiState.Loading

            try {
                val response = repository.loginPrestador(email, senha)

                uiState = LoginUiState.Sucesso(
                    userId = response.idUsuario,
                    tipoUsuario = response.tipoUsuario,
                    cadastroCompleto = response.cadastroCompleto
                )
            } catch (e: Exception) {
                uiState = LoginUiState.Erro(
                    e.localizedMessage ?: "Erro ao fazer login. Verifique suas credenciais."
                )
            }
        }
    }
}