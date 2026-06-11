package com.example.drivez.ui.servico

import androidx.compose.foundation.border
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.drivez.R
import com.example.drivez.core.network.theme.AppColors
import com.example.drivez.core.network.theme.fontFamily
import com.example.drivez.data.model.Categoria
import com.example.drivez.data.model.Prestador
import com.example.drivez.ui.components.AplicationTopBar
import com.example.drivez.ui.components.Avaliacao

@Composable
fun ServicoScreen(navController: NavController, prestadorId: String) {

    //Buscar o prestador pelo ID dele e exibir aqui!

    //Enquanto nao poder fazer requisicoes na API utilizar esse prestador, depois retirar
    val prestadorTeste = Prestador(
        id = 1, nome = "RIMBEIRO", avaliacao = 2.0,
        descricao = "Especializada em serviços de guincho e assistência veicular, a Rimberio oferece suporte rápido e seguro para o seu veículo. " +
                "Com foco na eficiência e no cuidado com o patrimônio do cliente, estamos prontos para atender emergências com profissionalismo e pontualidade.",
        totalAvaliacoes = 45, categorias = listOf(Categoria(nome = "Guincho"), Categoria(nome = "Mecanico"))
    )

    Scaffold(
        topBar = {
            AplicationTopBar(navController = navController)
        },
        containerColor = Color.White
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
                .padding(horizontal = 20.dp)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(20.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(
                painter = painterResource(R.drawable.baseline_person_24),
                contentDescription = "Prestador",
                tint = AppColors.DarkBlue,
                modifier = Modifier
                    .size(150.dp)
                    .border(1.dp, AppColors.DarkBlue, RoundedCornerShape(100))
            )

            Avaliacao(prestadorTeste.avaliacao, 34.dp, 3.dp)

            Text(
                text = prestadorTeste.nome,
                fontFamily = fontFamily,
                fontWeight = FontWeight.Bold,
                color = Color.Black,
                fontSize = 26.sp,
                textAlign = TextAlign.Center
            )

            Text(
                text = prestadorTeste.descricao,
                fontFamily = fontFamily,
                fontWeight = FontWeight.SemiBold,
                color = Color.Black,
                fontSize = 20.sp,
                textAlign = TextAlign.Justify
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ) {
                prestadorTeste.categorias.forEachIndexed { index, item ->
                    Row(
                        modifier = Modifier
                            .clip(RoundedCornerShape(10.dp))
                            .border(1.dp, AppColors.DarkBlue, RoundedCornerShape(10.dp))
                            .background(AppColors.DarkBlue)
                            .height(60.dp)
                            .padding(horizontal = 10.dp, vertical = 10.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Text(
                            text = item.nome,
                            fontFamily = fontFamily,
                            fontWeight = FontWeight.SemiBold,
                            modifier = Modifier
                                .padding(vertical = 5.dp, horizontal = 15.dp),
                            color = Color.White,
                            fontSize = 20.sp
                        )
                    }
                    if (prestadorTeste.categorias.getOrNull(index + 1) != null) {
                        Spacer(modifier = Modifier.width(20.dp))
                    }
                }
            }

            Button(
                onClick = {
                    navController.navigate(route = "home/cliente/contatos/conversa/${prestadorId}")
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(100.dp)
                    .padding(horizontal = 10.dp, vertical = 15.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = AppColors.SecondaryRed,
                    contentColor = Color.White
                ),
                shape = RoundedCornerShape(10.dp)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Solicitar Serviço",
                        fontFamily = fontFamily,
                        fontWeight = FontWeight.Bold,
                        color = Color.White,
                        fontSize = 25.sp
                    )
                }
            }

        }
    }
}
