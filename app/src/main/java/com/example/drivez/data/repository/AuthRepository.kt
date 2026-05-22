package com.example.drivez.data.repository

import com.example.drivez.core.network.AuthApiService
import com.example.drivez.core.network.LoginResponse
import com.example.drivez.data.dto.CadastroInicialRequest
import com.example.drivez.data.dto.ConcluirClienteRequest
import com.example.drivez.data.model.Cliente

class AuthRepository(private val apiService: AuthApiService) {

    suspend fun realizarCadastroInicial(nome: String, email: String, telefone: String, tipo: String): LoginResponse {
        val request = CadastroInicialRequest(nome, email, telefone, tipo)
        return apiService.cadastrarInicial(request)
    }

    suspend fun finalizarCliente(id: Int, cpf: String?, cnpj: String?): Cliente {
        return apiService.completarCliente(id, ConcluirClienteRequest(cpf, cnpj))
    }
}