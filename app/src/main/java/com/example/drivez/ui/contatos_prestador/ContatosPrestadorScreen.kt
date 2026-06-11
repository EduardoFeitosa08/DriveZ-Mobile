package com.example.drivez.ui.contatos_prestador

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
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
import com.example.drivez.ui.components.BottomPrestadorBar
import com.example.drivez.ui.components.ClienteCardContato

@Composable
fun ContatosPrestadorScreen(navController: NavController) {

    val listaDeContatosClientes = listOf(
        Contato(
            id = "1",
            name = "Rogerio Silva",
            ultimaMensagem = "Consegue chegar em 15 minutos?",
            perfilImgUrl = "https://i.pravatar.cc/150?u=rog"
        ),
        Contato(
            id = "2",
            name = "Ana Beatriz",
            ultimaMensagem = "Já aceitei o orçamento, pode vir.",
            perfilImgUrl = "https://i.pravatar.cc/150?u=ana"
        ),
        Contato(
            id = "3",
            name = "Marcos Oliveira",
            ultimaMensagem = "Estou parado bem em frente ao posto.",
            perfilImgUrl = "https://i.pravatar.cc/150?u=mar"
        ),
        Contato(
            id = "4",
            name = "Luciana Costa",
            ultimaMensagem = "Você trabalha com cartões de crédito?",
            perfilImgUrl = "https://i.pravatar.cc/150?u=lu"
        ),
        Contato(
            id = "5",
            name = "Ricardo Santos",
            ultimaMensagem = "O pneu reserva também está furado...",
            perfilImgUrl = "https://i.pravatar.cc/150?u=ric"
        ),
        Contato(
            id = "6",
            name = "Beatriz Mendes",
            ultimaMensagem = "Mandei a foto do motor no seu WhatsApp.",
            perfilImgUrl = "https://i.pravatar.cc/150?u=bia"
        ),
        Contato(
            id = "7",
            name = "Fernando Souza",
            ultimaMensagem = "Obrigado pelo suporte hoje cedo!",
            perfilImgUrl = "https://i.pravatar.cc/150?u=fer"
        ),
        Contato(
            id = "8",
            name = "Carlos Eduardo",
            ultimaMensagem = "A bateria descarregou totalmente agora.",
            perfilImgUrl = "https://i.pravatar.cc/150?u=cad"
        ),
        Contato(
            id = "9",
            name = "Juliana Lima",
            ultimaMensagem = "Qual o valor da sua taxa de deslocamento?",
            perfilImgUrl = "https://i.pravatar.cc/150?u=juli"
        ),
        Contato(
            id = "10",
            name = "Roberto Junior",
            ultimaMensagem = "Estou enviando minha localização em tempo real.",
            perfilImgUrl = "https://i.pravatar.cc/150?u=rob"
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
            BottomPrestadorBar(navController = navController)
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
                items(listaDeContatosClientes) {
                    ClienteCardContato(it, navController)
                }
            }
        }
    }
}
