package com.example.drivez.ui.contatos_cliente

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.drivez.core.network.theme.AppColors
import com.example.drivez.data.model.Contato
import com.example.drivez.ui.components.AplicationTopBar
import com.example.drivez.ui.components.BottomClienteBar
import com.example.drivez.ui.components.PrestadorCardContato

@Composable
fun ContatosClienteScreen(navController: NavController) {

    val listaDeContatos = listOf(
        Contato(
            id = "1",
            name = "Rimberio - Guincho",
            ultimaMensagem = "Estou chegando na sua localização.",
            perfilImgUrl = "https://i.pravatar.cc/150?u=1"
        ),
        Contato(
            id = "2",
            name = "Lojão das Baterias",
            ultimaMensagem = "A peça que você solicitou já está disponível.",
            perfilImgUrl = "https://i.pravatar.cc/150?u=2"
        ),
        Contato(
            id = "3",
            name = "Brand - Borracheiro",
            ultimaMensagem = "Pode trazer o veículo, estou livre.",
            perfilImgUrl = "https://i.pravatar.cc/150?u=3"
        ),
        Contato(
            id = "4",
            name = "Auto Repair - Mecanico",
            ultimaMensagem = "O diagnóstico ficou pronto, confira o valor.",
            perfilImgUrl = "https://i.pravatar.cc/150?u=4"
        ),
        Contato(
            id = "5",
            name = "Silva - Eletricista",
            ultimaMensagem = "Preciso de mais detalhes sobre o problema.",
            perfilImgUrl = "https://i.pravatar.cc/150?u=5"
        ),
        Contato(
            id = "6",
            name = "Santos - Funileiro",
            ultimaMensagem = "Podemos agendar para amanhã?",
            perfilImgUrl = "https://i.pravatar.cc/150?u=6"
        )
    )

    Scaffold(
        topBar = {
            AplicationTopBar(
                navController = navController, titulo = "Contatos", retornavel = false, backgroundColor = AppColors.PrimaryRed,
                textColor = AppColors.TextWhite, iconColor = AppColors.TextWhite
            )
        },
        bottomBar = {
            BottomClienteBar(navController = navController, shadow = false)
        },
        containerColor = Color.White
    ) { paddingValues ->
        Box(
            modifier = Modifier.fillMaxSize()
        ) {
            LazyColumn(
                modifier = Modifier
                    .padding(paddingValues)
                    .padding(horizontal = 15.dp, vertical = 20.dp),
                verticalArrangement = Arrangement.spacedBy(20.dp)
            ) {
                items(listaDeContatos) {
                    PrestadorCardContato(it, navController)
                }
            }
        }
    }
}
