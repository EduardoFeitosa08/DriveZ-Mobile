package com.example.drivez.ui.cadastro

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.drivez.fontFamily
import com.example.drivez.ui.cadastro.components.FormularioCliente
import com.example.drivez.ui.cadastro.components.FormularioPrestador
import com.example.drivez.ui.components.AplicationTopBar
import com.example.drivez.core.network.theme.AppColors

//@OptIn(ExperimentalMaterial3Api::class)
//@Composable
//fun CadastroScreen(navController: NavController, viewModel: CadastroViewModel) {
//    var senhaStrength by remember { mutableStateOf("Senha fraca") }
//    var cadastroUserClient by remember { mutableStateOf(true) }
//    var cadastroUserPrestador by remember { mutableStateOf(false) }
//
//    Button(onClick = { viewModel.executarCadastroInicial(nome, email, telefone, tipo) }) {
//        Text("Avançar")
//    }
//
//    // Efeito colateral para escutar a resposta da API e navegar
//    LaunchedEffect(viewModel.uiState) {
//        when (val state = viewModel.uiState) {
//            is CadastroUiState.Redirecionar -> {
//                if (!state.cadastroCompleto) {
//                    // Se o cadastro NÃO está completo, manda para a tela de finalização específica
//                    if (state.tipoUsuario == "CLIENTE") {
//                        navController.navigate("completar_cadastro_cliente")
//                    } else {
//                        navController.navigate("completar_cadastro_prestador")
//                    }
//                } else {
//                    // Se já estivesse completo por algum motivo, iria direto para a Home
//                    navController.navigate("home")
//                }
//            }
//            is CadastroUiState.Erro -> { /* Mostrar Snackbar de erro */ }
//            else -> {}
//        }
//    }
//
//
//    Scaffold(
//        containerColor = Color.White,
//        topBar = {
//            AplicationTopBar(navController = navController)
//        }
//    ) { paddingValues ->
//        Column(
//            modifier = Modifier
//                .padding(paddingValues)
//                .fillMaxSize()
//                .background(Color.White)
//                .verticalScroll(rememberScrollState())
//                .padding(start = 25.dp, end = 25.dp, bottom = 80.dp),
//            verticalArrangement = Arrangement.spacedBy(10.dp)
//        ) {
//
//            Text(
//                text = "Cadastro",
//                fontWeight = FontWeight.Bold,
//                fontFamily = fontFamily,
//                fontSize = 30.sp,
//                color = AppColors.DarkBlue,
//                modifier = Modifier
//                    .padding(start = 15.dp)
//            )
//
//            Row(
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .height(100.dp)
//                    .border(1.dp, AppColors.SecondaryRed, RoundedCornerShape(20.dp))
//            ) {
//                Button(
//                    onClick = {
//                        cadastroUserClient = true
//                        cadastroUserPrestador = false
//                    },
//                    modifier = Modifier
//                        .weight(1f)
//                        .clip(RoundedCornerShape(topStart = 20.dp, bottomStart = 20.dp)),
//                    shape = RoundedCornerShape(topStart = 20.dp, bottomStart = 20.dp),
//                    colors = ButtonDefaults.buttonColors(
//                        containerColor = if(cadastroUserClient) AppColors.HighlightRed else Color.White,
//                        contentColor = if (cadastroUserClient) Color.White else AppColors.DarkBlue
//                    )
//                ) {
//                    Column(
//                        modifier = Modifier
//                            .fillMaxSize(),
//                        horizontalAlignment = Alignment.CenterHorizontally,
//                        verticalArrangement = Arrangement.Center
//                    ) {
//                        Text(
//                            text = "Sou Cliente",
//                            fontSize = 18.sp,
//                            fontWeight = FontWeight.Bold,
//                            fontFamily = fontFamily,
//                            color = if (cadastroUserClient) Color.White else AppColors.DarkBlue
//                        )
//                        Spacer(modifier = Modifier.height(10.dp))
//                        Text(
//                            text = "Pessoas que necessitam ajuda",
//                            fontSize = 14.sp,
//                            fontFamily = fontFamily,
//                            fontWeight = FontWeight.Bold,
//                            color = if (cadastroUserClient) Color.White else AppColors.DarkBlue,
//                            textAlign = TextAlign.Center
//                        )
//                    }
//                }
//                Button(
//                    onClick = {
//                        cadastroUserPrestador = true
//                        cadastroUserClient = false
//                    },
//                    modifier = Modifier
//                        .weight(1f)
//                        .clip(RoundedCornerShape(topEnd = 20.dp, bottomEnd = 20.dp)),
//                    shape = RoundedCornerShape(topEnd = 20.dp, bottomEnd = 20.dp),
//                    colors = ButtonDefaults.buttonColors(
//                        containerColor = if(cadastroUserPrestador) AppColors.HighlightRed else Color.White,
//                        contentColor = if (cadastroUserPrestador) Color.White else AppColors.DarkBlue
//                    )
//                ) {
//                    Column(
//                        modifier = Modifier
//                            .fillMaxSize(),
//                        horizontalAlignment = Alignment.CenterHorizontally,
//                        verticalArrangement = Arrangement.Center
//                    ) {
//                        Text(
//                            text = "Sou Prestador",
//                            fontSize = 18.sp,
//                            fontWeight = FontWeight.Bold,
//                            fontFamily = fontFamily,
//                            color = if (cadastroUserPrestador) Color.White else AppColors.DarkBlue,
//                            textAlign = TextAlign.Center
//                        )
//                        Spacer(modifier = Modifier.height(10.dp))
//                        Text(
//                            text = "Prestadores de serviço",
//                            fontSize = 14.sp,
//                            fontFamily = fontFamily,
//                            fontWeight = FontWeight.Bold,
//                            color = if (cadastroUserPrestador) Color.White else AppColors.DarkBlue,
//                            textAlign = TextAlign.Center
//                        )
//                    }
//                }
//            }
//            Spacer(modifier = Modifier.height(10.dp))
//
//            if (cadastroUserClient && !cadastroUserPrestador){
//                FormularioCliente(navController = navController)
//            }else{
//                FormularioPrestador(navController = navController)
//            }
//
//        }
//    }
//}

@Composable
fun CadastroScreen(
    navController: NavController,
    viewModel: CadastroViewModel,
    cadastroUserClient: Boolean,
    cadastroUserPrestador: Boolean
) {
    Scaffold(
        modifier = Modifier.fillMaxSize()
    ) { innerPadding ->


        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(horizontal = 24.dp)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.Center
        ) {
            Spacer(modifier = Modifier.height(20.dp))

            if (cadastroUserClient && !cadastroUserPrestador) {
                FormularioCliente(
                    navController = navController,
                    onCadastrarClick = { nome, email, senha ->
                        viewModel.executarCadastroInicial(
                            nome = nome,
                            email = email,
                            telefone = "",
                            tipo = "CLIENTE"
                        )
                    }
                )
            } else {
                FormularioPrestador(
                    navController = navController,
                    onCadastrarClick = { nome, email, senha ->
                        viewModel.executarCadastroInicial(
                            nome = nome,
                            email = email,
                            telefone = "",
                            tipo = "PRESTADOR"
                        )
                    }
                )
            }

            // Espaçamento flexível no final
            Spacer(modifier = Modifier.height(20.dp))
        }
    }
}