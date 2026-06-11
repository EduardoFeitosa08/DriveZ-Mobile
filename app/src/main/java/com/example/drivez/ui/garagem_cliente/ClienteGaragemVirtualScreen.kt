package com.example.drivez.ui.garagem_cliente

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.drivez.R
import com.example.drivez.core.network.theme.AppColors
import com.example.drivez.data.model.Veiculo
import com.example.drivez.ui.components.BottomPrestadorBar
import com.example.drivez.ui.components.CardVeiculo
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ClienteGaragemVirtualScreen(navController: NavController, clienteId: String) {
    val listaVeiculosFake = remember {
        mutableStateListOf(
            Veiculo(1, "Veiculo 1", "ABC-1D23", "12345678910", "25/01/2026", "SEDAN"),
            Veiculo(2, "Veiculo 2", "XYZ-9M87", "98765432100", "15/08/2027", "GUINCHO"),
            Veiculo(3, "Veiculo 3", "ABC-1D23", "12345678910", "25/01/2026", "SEDAN"),
            Veiculo(4, "Veiculo 4", "XYZ-9M87", "98765432100", "15/08/2027", "GUINCHO"),
            Veiculo(5, "Veiculo 5", "ABC-1D23", "12345678910", "25/01/2026", "SEDAN"),
            Veiculo(6, "Veiculo 6", "XYZ-9M87", "98765432100", "15/08/2027", "GUINCHO")
        )
    }

    val listState = rememberLazyListState()
    val coroutineScope = rememberCoroutineScope()

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text("Minha Garagem", color = Color.White, fontWeight = FontWeight.Bold)
                },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            painter = painterResource(R.drawable.baseline_arrow_back_24),
                            contentDescription = "Voltar",
                            tint = Color.White,
                            modifier = Modifier.size(30.dp)
                        )
                    }
                },
                actions = {
                    IconButton(onClick = {
                        val jaTemItemEmEdicao = listaVeiculosFake.any { it.id == null }
                        if (!jaTemItemEmEdicao) {
                            listaVeiculosFake.add(0, Veiculo.criarNovoVazio())
                        }

                        coroutineScope.launch {
                            listState.animateScrollToItem(index = 0)
                        }
                    }) {
                        Icon(
                            painter = painterResource(R.drawable.ic_plus),
                            contentDescription = "Adicionar",
                            tint = Color.White
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = AppColors.PrimaryRed
                )
            )
        },
        bottomBar = {
            BottomPrestadorBar(navController = navController)
        }
    ) { paddingValues ->
        LazyColumn(
            state = listState,
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFFF5F5F5))
                .padding(paddingValues)
                .padding(horizontal = 16.dp),
            contentPadding = PaddingValues(top = 8.dp, bottom = 80.dp)
        ) {
            items(listaVeiculosFake, key = { "${it.id}_${it.placa}" }) { veiculo ->
                CardVeiculo(
                    veiculo = veiculo,
                    onSalvarAlteracoes = { veiculoAtualizado ->
                        val index = if (veiculo.id == null) {
                            listaVeiculosFake.indexOfFirst { it.id == null }
                        } else {
                            listaVeiculosFake.indexOfFirst { it.id == veiculo.id }
                        }

                        if (index != -1) {
                            listaVeiculosFake[index] = veiculoAtualizado
                        }

                        //View Model será chamada aqui
                    },
                    onCancelarEdicao = {
                        if (veiculo.id == null) {
                            listaVeiculosFake.remove(veiculo)
                        }
                    },
                    onRemoverVeiculo = { veiculoParaRemover ->
                        listaVeiculosFake.remove(veiculoParaRemover)

                        //View Model será chamada aqui
                    }
                )
            }
        }
    }
}
