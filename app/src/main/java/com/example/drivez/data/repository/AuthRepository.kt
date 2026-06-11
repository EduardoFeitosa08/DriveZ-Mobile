package com.example.drivez.data.repository

import com.example.drivez.core.network.AuthApiService
import com.example.drivez.core.network.RetrofitClient
import com.example.drivez.data.dto.CadastroInicialRequest
import com.example.drivez.data.dto.ConcluirClienteRequest
import com.example.drivez.data.dto.LoginRequest
import com.example.drivez.data.dto.LoginResponse
import com.example.drivez.data.model.Cliente
import retrofit2.Retrofit

class AuthRepository(private val apiService: AuthApiService = RetrofitClient.authApiService) {

    suspend fun realizarCadastroInicial(nome: String, email: String, tipo: String): LoginResponse {
        val request = CadastroInicialRequest(nome, email, tipo)
        return apiService.cadastrarInicial(request)
    }

    suspend fun loginPrestador(email: String, senha: String): LoginResponse {
        return apiService.realizarLogin(LoginRequest(email, senha))
    }
}