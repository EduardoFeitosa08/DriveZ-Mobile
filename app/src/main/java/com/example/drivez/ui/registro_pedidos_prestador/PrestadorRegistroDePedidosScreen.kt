package com.example.drivez.ui.registro_pedidos_prestador

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.drivez.ui.components.AplicationTopBar
import com.example.drivez.ui.components.BottomPrestadorBar
import com.example.drivez.ui.components.PrestadorCardHistoricoPedido

@Composable
fun PrestadorRegistroDePedidosScreen(
    navController: NavController,
    viewModel: PrestadorRegistroDePedidosViewModel
) {
    // Acessando o estado diretamente do ViewModel (que é um mutableStateOf)
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
        Box(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
        ) {
            if (uiState.isLoading) {
                CircularProgressIndicator(
                    modifier = Modifier.align(Alignment.Center),
                    color = Color(0xFFE53935)
                )
            } else if (uiState.erro != null) {
                Column(
                    modifier = Modifier.align(Alignment.Center),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(text = uiState.erro!!, color = Color.Red)
                    Spacer(modifier = Modifier.height(8.dp))
                    Button(onClick = { viewModel.carregarPedidos() }) {
                        Text("Tentar Novamente")
                    }
                }
            } else if (uiState.listaPedidos.isEmpty()) {
                Text(
                    text = "Nenhum pedido no histórico.",
                    modifier = Modifier.align(Alignment.Center)
                )
            } else {
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.spacedBy(15.dp),
                    contentPadding = PaddingValues(15.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    items(uiState.listaPedidos) { pedido ->
                        PrestadorCardHistoricoPedido(pedido)
                    }
                }
            }
        }
    }
}
