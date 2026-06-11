package com.example.drivez.ui.registro_pedidos_cliente

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.example.drivez.data.model.Pedido
import com.example.drivez.data.model.StatusPedido
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

//class ClienteRegistroDePedidosViewModel(
//    savedStateHandle: SavedStateHandle
//) : ViewModel() {
//
//    val clienteId: String? = savedStateHandle["clienteId"]
//
//    private val _pedidos = MutableStateFlow<List<Pedido>>(emptyList())
//    val pedidos: StateFlow<List<Pedido>> = _pedidos.asStateFlow()
//
//    init {
//        carregarHistoricoDePedidos()
//    }
//
////    private fun carregarHistoricoDePedidos() {
////
////        _pedidos.value = listOf(
////            Pedido(1, StatusPedido.PENDENTE, "2026-04-24 09:00", "Av. Paulista, 1000", "Rua Augusta, 200", "Entrega de documentos do Cliente ID: $clienteId", "2.5 km", 101, 201),
////            Pedido(2, StatusPedido.EM_ANDAMENTO, "2026-04-24 10:30", "Rua da Consolação, 50", "Av. Rebouças, 800", "Compra de supermercado do Cliente ID: $clienteId", "4.0 km", 102, 202),
////            Pedido(3, StatusPedido.FINALIZADO, "2026-04-24 08:00", "Praça da Sé, 10", "Bairro Liberdade, 100", "Entrega de presente", "1.2 km", 103, 203),
////            Pedido(4, StatusPedido.PENDENTE, "2026-04-24 11:00", "Av. Brigadeiro, 500", "Rua Oscar Freire, 300", "Transporte de móveis", "5.8 km", 104, 204),
////            Pedido(5, StatusPedido.EM_ANDAMENTO, "2026-04-24 12:15", "Aeroporto de Congonhas", "Hotel Transamerica", "Levar bagagem", "8.5 km", 105, 205),
////            Pedido(6, StatusPedido.FINALIZADO, "2026-04-24 07:30", "Centro Histórico", "Rodoviária", "Envio de pacote urgente", "12.0 km", 106, 206)
////        )
////    }
//}