package com.example.drivez.core.network

import com.example.drivez.data.dto.ClienteApiResponse
import com.example.drivez.data.dto.PedidoApiResponse
import retrofit2.http.GET

interface PedidoApiService {

    @GET("pedidos")
    suspend fun getPedidos(): PedidoApiResponse

    @GET("cliente")
    suspend fun getClientes(): ClienteApiResponse
}