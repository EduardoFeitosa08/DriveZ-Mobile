package com.example.drivez.ui.login

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.drivez.R
import com.example.drivez.fontFamily
import com.example.drivez.ui.components.CampoDigitar
import com.example.drivez.core.network.theme.AppColors
import com.example.drivez.ui.components.CadastroCampoDigitar
import android.util.Log
import androidx.compose.foundation.layout.imePadding

@Composable
fun LoginScreen(
    navController: NavController,
    viewModel: LoginViewModel = viewModel()
) {
    var email by remember { mutableStateOf("") }
    var senha by remember { mutableStateOf("") }
    val state = viewModel.uiState

    LaunchedEffect(state) {
        if (state is LoginUiState.Sucesso) {
            val cadastroIgnoradoParaTestes = true

            if (cadastroIgnoradoParaTestes) {
                val tipoUsuarioNormalizado = state.tipoUsuario.lowercase()

                if (tipoUsuarioNormalizado == "prestador") {
                    navController.navigate("home/prestador") {
                        popUpTo("login") { inclusive = true }
                    }
                } else if (tipoUsuarioNormalizado == "cliente") {
                    navController.navigate("home/cliente") {
                        popUpTo("login") { inclusive = true }
                    }
                }
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 15.dp, vertical = 20.dp)
            .verticalScroll(rememberScrollState())
            .imePadding(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(R.drawable.logo_principal),
            contentDescription = "DriveZ Logo",
            modifier = Modifier
                .size(width = 400.dp, height = 250.dp)
        )
        Spacer(modifier = Modifier.height(5.dp))

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
//        Spacer(modifier = Modifier.height(350.dp))
    }
}