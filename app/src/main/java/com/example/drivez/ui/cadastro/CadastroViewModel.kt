package com.example.drivez.ui.cadastro

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.drivez.data.repository.AuthRepository
import kotlinx.coroutines.launch

class CadastroViewModel(
    private val repository: AuthRepository = AuthRepository()
) : ViewModel() {
    var uiState: CadastroUiState by mutableStateOf(CadastroUiState.Idle)
        private set

    fun executarCadastroInicial(nome: String, email: String, tipo: String) {
        if (nome.isBlank() || email.isBlank()) {
            uiState = CadastroUiState.Erro("Por favor, preencha todos os campos obrigatórios.")
            return
        }

        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            uiState = CadastroUiState.Erro("O formato do e-mail é inválido.")
            return
        }
        viewModelScope.launch {
            uiState = CadastroUiState.Loading
            try {
                val response = repository.realizarCadastroInicial(nome, email, tipo)

                uiState = CadastroUiState.Redirecionar(
                    userId = response.idUsuario,
                    tipoUsuario = response.tipoUsuario,
                    cadastroCompleto = response.cadastroCompleto
                )
            } catch (e: Exception) {
                uiState = CadastroUiState.Erro(e.localizedMessage ?: "Falha ao conectar com o servidor.")
            }
        }
    }

    fun resetarEstado() {
        uiState = CadastroUiState.Idle
    }
}