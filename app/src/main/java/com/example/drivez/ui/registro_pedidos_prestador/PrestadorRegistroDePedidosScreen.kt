package com.example.drivez.ui.registro_pedidos_prestador

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.drivez.ui.components.AplicationTopBar
import com.example.drivez.ui.components.BottomPrestadorBar
import com.example.drivez.ui.components.PrestadorCardHistoricoPedido

@Composable
fun PrestadorRegistroDePedidosScreen(
    navController: NavController,
    viewModel: PrestadorRegistroDePedidosViewModel = viewModel()
) {
    val uiState = viewModel.uiState

    Scaffold(
        topBar = {
            AplicationTopBar(navController = navController, titulo = "Histórico de Pedidos", retornavel = false)
        },
        bottomBar = {
            BottomPrestadorBar(navController = navController)
        },
        containerColor = Color.White
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(15.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            items(uiState.listaPedidos) { pedido ->
                PrestadorCardHistoricoPedido(pedido)
            }
        }
    }
}