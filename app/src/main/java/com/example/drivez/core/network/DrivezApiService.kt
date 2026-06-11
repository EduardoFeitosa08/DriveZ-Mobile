package com.example.drivez.core.network

import com.example.drivez.data.dto.ApiResponseAvaliacao
import com.example.drivez.model.ApiResponseAvaliacao
import com.example.drivez.model.PedidoResponseDto // Substitua pelo seu DTO real de pedidos
import com.example.drivez.model.ClienteResponseDto // Substitua pelo seu DTO real de cliente
import retrofit2.http.GET
import retrofit2.http.Path

interface DrivezApiService {

    /**
     * Rota para buscar todos os pedidos que precisam de um guincho/prestador
     */
    @GET("v1/drivez/pedidos/pendentes") // Ajuste o endereço final de acordo com seu backend
    suspend fun obtenerPedidosPendentes(): List<PedidoResponseDto>

    /**
     * Rota para buscar os dados de perfil de um cliente específico (nome, foto)
     */
    @GET("v1/drivez/clientes/{id}")
    suspend fun obterClientePorId(
        @Path("id") idCliente: Long
    ): ClienteResponseDto

    /**
     * Rota da Azure que você enviou: busca a média de notas de um cliente específico
     */
    @GET("v1/drivez/avaliacoes/mediaCliente/{idCliente}")
    suspend fun obterMediaCliente(
        @Path("idCliente") idCliente: Long
    ): ApiResponseAvaliacao
}