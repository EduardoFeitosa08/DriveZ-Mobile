package com.example.drivez.ui.home_cliente

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.drivez.data.model.Categoria
import com.example.drivez.data.model.Prestador
import com.example.drivez.data.repository.HomeRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class HomeClienteViewModel(
    private val repository: HomeRepository = HomeRepository()
) : ViewModel() {

    private val _prestadores = MutableStateFlow<List<Prestador>>(emptyList())
    val prestadores = _prestadores.asStateFlow()

    private val _notificacoesAtivas = MutableStateFlow(true)
    val notificacoesAtivas = _notificacoesAtivas.asStateFlow()

    fun buscarPrestadoresProximos() {
        viewModelScope.launch {
            try {
                val listaVindaDoBanco = repository.obterPrestadores()

                _prestadores.value = listaVindaDoBanco
            } catch (e: Exception) {
                _prestadores.value = emptyList()
                e.printStackTrace()
            }
        }
    }

    fun alternarNotificacoes() {
        _notificacoesAtivas.value = !_notificacoesAtivas.value
    }
}