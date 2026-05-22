package com.example.drivez.core.network

import com.example.drivez.data.dto.CadastroInicialRequest
import com.example.drivez.data.dto.ConcluirClienteRequest
import com.example.drivez.data.dto.ConcluirPrestadorRequest
import com.example.drivez.data.model.Cliente
import com.example.drivez.data.model.Prestador
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface AuthApiService {

    @POST("auth/cadastro-inicial")
    suspend fun cadastrarInicial(@Body request: CadastroInicialRequest): LoginResponse

    @PUT("usuarios/completar-cliente/{id}")
    suspend fun completarCliente(@Path("id") id: Int, @Body request: ConcluirClienteRequest): Cliente

    @PUT("usuarios/completar-prestador/{id}")
    suspend fun completarPrestador(@Path("id") id: Int, @Body request: ConcluirPrestadorRequest): Prestador
}

data class LoginResponse(
    val token: String,
    val tipoUsuario: String,
    val cadastroCompleto: Boolean,
    val idUsuario: Int
)