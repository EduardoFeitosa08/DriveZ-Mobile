package com.example.drivez.ui.cadastro

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.drivez.data.repository.AuthRepository
import kotlinx.coroutines.launch

sealed interface CadastroUiState {
    object Idle : CadastroUiState
    object Loading : CadastroUiState
    data class Redirecionar(val tipoUsuario: String, val cadastroCompleto: Boolean) : CadastroUiState
    data class Erro(val msg: String) : CadastroUiState
}

class CadastroViewModel(private val repository: AuthRepository) : ViewModel() {

    var uiState: CadastroUiState by mutableStateOf(CadastroUiState.Idle)
        private set

    fun executarCadastroInicial(nome: String, email: String, telefone: String, tipo: String) {
        viewModelScope.launch {
            uiState = CadastroUiState.Loading
            try {
                val response = repository.realizarCadastroInicial(nome, email, telefone, tipo)

                uiState = CadastroUiState.Redirecionar(
                    tipoUsuario = response.tipoUsuario,
                    cadastroCompleto = response.cadastroCompleto
                )
            } catch (e: Exception) {
                uiState = CadastroUiState.Erro("Falha ao cadastrar: ${e.localizedMessage}")
            }
        }
    }
}