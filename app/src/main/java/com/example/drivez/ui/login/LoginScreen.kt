package com.example.drivez.ui.login

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.drivez.R
import com.example.drivez.core.network.theme.AppColors
import com.example.drivez.core.session.SessionManager
import com.example.drivez.ui.components.CadastroCampoDigitar

@Composable
fun LoginScreen(
    navController: NavController
) {
    val context = LocalContext.current
    val sessionManager = remember { SessionManager(context) }
    
    val viewModel: LoginViewModel = viewModel(
        factory = object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                @Suppress("UNCHECKED_CAST")
                return LoginViewModel(sessionManager = sessionManager) as T
            }
        }
    )

    var email by remember { mutableStateOf("") }
    var senha by remember { mutableStateOf("") }
    val state = viewModel.uiState

    LaunchedEffect(state) {
        if (state is LoginUiState.Sucesso) {
            if (state.tipoUsuario == "Prestador") {
                navController.navigate("home/prestador") {
                    popUpTo("login") { inclusive = true }
                }
            } else {
                navController.navigate("home/cliente") {
                    popUpTo("login") { inclusive = true }
                }
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(25.dp)
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(R.drawable.logo_login),
            contentDescription = "DriveZ Logo",
            modifier = Modifier.size(450.dp)
        )
        
        Spacer(modifier = Modifier.height(15.dp))

        if (state is LoginUiState.Erro) {
            Text(text = state.mensagem, color = AppColors.ErrorRed, fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.height(8.dp))
        }

        CadastroCampoDigitar(
            campoNome = "Email",
            placeholder = "exemplo@gmail.com",
            value = email,
            onValueChange = { email = it }
        )

        Spacer(modifier = Modifier.height(10.dp))

        CadastroCampoDigitar(
            campoNome = "Senha",
            placeholder = "Digite sua senha",
            value = senha,
            onValueChange = { senha = it },
            painter = painterResource(R.drawable.baseline_lock_24),
            painterTransform = painterResource(R.drawable.baseline_remove_red_eye_24),
            isSenha = true
        )

        Spacer(modifier = Modifier.height(20.dp))

        Button(
            onClick = {
                viewModel.realizarLogin(email, senha)
            },
            enabled = state !is LoginUiState.Loading,
            modifier = Modifier
                .fillMaxWidth()
                .height(60.dp),
            shape = RoundedCornerShape(15.dp),
            colors = ButtonDefaults.buttonColors(containerColor = AppColors.PrimaryRed)
        ) {
            if (state is LoginUiState.Loading) {
                CircularProgressIndicator(color = Color.White, modifier = Modifier.size(24.dp))
            } else {
                Text(text = "Entrar", fontSize = 20.sp, fontWeight = FontWeight.Bold)
            }
        }

        TextButton(onClick = { navController.navigate("cadastro") }) {
            Text("Não tem uma conta? Cadastre-se")
        }
        
        Spacer(modifier = Modifier.height(50.dp))
    }
}
