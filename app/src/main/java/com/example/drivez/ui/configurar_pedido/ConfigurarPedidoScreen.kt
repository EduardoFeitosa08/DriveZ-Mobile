package com.example.drivez.ui.configurar_pedido

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.drivez.R
import com.example.drivez.core.network.theme.AppColors
import com.example.drivez.core.network.theme.fontFamily
import com.example.drivez.data.model.CategoriaPedido
import com.example.drivez.ui.components.CampoConfigurarPedido
import com.example.drivez.ui.components.CardCategoria
import com.example.drivez.ui.components.CardConfirmacao

@Composable
fun ConfigurarPedidoScreen(navController: NavController, isSOS: Boolean) {
    var origem by remember { mutableStateOf("") }
    var destino by remember { mutableStateOf("") }
    var descricao by remember { mutableStateOf("") }
    var pesquisa by remember { mutableStateOf("") }

    var mostrarCategorias by remember { mutableStateOf(false) }
    var cardState by remember { mutableStateOf(false) }

    val listaCategorias = listOf(
        CategoriaPedido(painterResource(id = R.drawable.ic_guincho), "Guincho"),
        CategoriaPedido(painterResource(id = R.drawable.ic_mecanico), "Mecanico"),
        CategoriaPedido(painterResource(id = R.drawable.ic_eletricista), "Eletricista"),
        CategoriaPedido(painterResource(id = R.drawable.ic_borracheiro), "Borracheiro"),
        CategoriaPedido(painterResource(id = R.drawable.ic_pneu), "Troca de Pneu")
    )
    val categoriasFiltradas = listaCategorias.filter { it.nome.contains(pesquisa, ignoreCase = true) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 16.dp, start = 16.dp, end = 16.dp, bottom = 70.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {

            item {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 10.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            painter = painterResource(R.drawable.baseline_arrow_back_24),
                            contentDescription = "Voltar",
                            modifier = Modifier.size(35.dp)
                        )
                    }
                    Text(
                        text = "Configurar Pedido",
                        fontFamily = fontFamily,
                        fontWeight = FontWeight.Bold,
                        fontSize = 18.sp
                    )
                }

            }

            item {
                CampoConfigurarPedido(value = origem, onValueChange = { origem = it }, label = "Origem")
                Spacer(modifier = Modifier.height(8.dp))
                CampoConfigurarPedido(value = destino, onValueChange = { destino = it }, label = "Destino")
                Spacer(modifier = Modifier.height(8.dp))
                if (isSOS) {
                    CampoConfigurarPedido(
                        value = descricao,
                        onValueChange = { descricao = it },
                        label = "Descrição",
                        isSingleLine = false,
                        modifier = Modifier.height(120.dp)
                    )
                }
            }

            item {
                Button(
                    onClick = {
                        //Depois alterar a logica para se encontrar os endereços escritos
                        if (origem != "" && destino != "") {
                            if (isSOS) {
                                mostrarCategorias = true
                            } else {
                                //Depois pensar em como passar o id do prestador quando ser um SOS
                                navController.navigate(route = "home/cliente/servico_status/1/${isSOS}")
                            }
                        }
                    },
                    modifier = Modifier
                        .width(200.dp)
                        .height(48.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = AppColors.SecondaryRed),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Text(
                        text = "Continuar",
                        color = Color.White,
                        fontFamily = fontFamily,
                        fontWeight = FontWeight.Bold,
                        fontSize = 20.sp
                    )
                }
            }

            if (isSOS) {
                item {
                    AnimatedVisibility(visible = mostrarCategorias) {
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            HorizontalDivider(modifier = Modifier.padding(vertical = 24.dp), color = AppColors.PrimaryRed, thickness = 2.dp)

                            Text(
                                text = "Selecione a categoria do pedido",
                                fontFamily = fontFamily,
                                fontWeight = FontWeight.Bold,
                                modifier = Modifier.padding(bottom = 16.dp),
                                fontSize = 18.sp
                            )

                            OutlinedTextField(
                                value = pesquisa,
                                onValueChange = { pesquisa = it },
                                placeholder = { Text("Pesquisar Categoria") },
                                modifier = Modifier.fillMaxWidth(),
                                shape = CircleShape,
                                leadingIcon = { Icon(painterResource(R.drawable.search), contentDescription = null) }
                            )
                        }
                    }
                }

                if (mostrarCategorias) {
                    items(categoriasFiltradas) { categoria ->
                        CardCategoria(categoriaPedido = categoria, onClick = {
                            cardState = true
                        })
                    }
                }
            }

        }
        if (cardState) {
            CardConfirmacao(
                pergunta = "Iniciar Busca?",
                onBackClick = { cardState = false },
                onConfirmClick = { navController.navigate(route = "home/cliente/servico_status/1/true") }
            )
        }

    }
}
