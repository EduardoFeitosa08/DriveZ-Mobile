package com.example.drivez.core.network

import com.example.drivez.data.model.ApiResponse
import com.example.drivez.data.model.PedidoResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface PedidoApiService {
    // Se a URL completa for http://localhost:8080/v1/drivez/pedidos
    // E a BASE_URL for http://10.0.2.2:8080/v1/drivez/
    // O @GET deve ser exatamente "pedidos"
    
    @GET("pedidos")
    suspend fun getHistoricoPedidosPrestador(
        @Query("idPrestador") prestadorId: Int
    ): Response<ApiResponse<List<PedidoResponse>>>

    @GET("pedidos")
    suspend fun getHistoricoPedidosCliente(
        @Query("idCliente") clienteId: Int
    ): Response<ApiResponse<List<PedidoResponse>>>
}
