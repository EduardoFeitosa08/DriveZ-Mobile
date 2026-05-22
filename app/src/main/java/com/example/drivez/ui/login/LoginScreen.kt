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
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
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
import com.example.drivez.fontFamily
import com.example.drivez.ui.components.CampoDigitar
import com.example.drivez.core.network.theme.AppColors

@Composable
fun LoginScreen(modifier: Modifier = Modifier, navController: NavController) {

    Scaffold(
        containerColor = Color.White
    ) { paddingValues ->
        Column(
            modifier = modifier
                .padding(paddingValues)
                .fillMaxSize()
                .background(AppColors.BackgroundGray)
                .verticalScroll(rememberScrollState())
                .padding(bottom = 30.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Image(
                painter = painterResource(R.drawable.logo_login),
                contentDescription = "DriveZ Logo",
                modifier = Modifier
                    .size(450.dp)
            )
            Spacer(modifier = Modifier.height(15.dp))
            Column(
                modifier = Modifier
                    .padding(horizontal = 45.dp),
                verticalArrangement = Arrangement.spacedBy(20.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                //TextField do Email
                CampoDigitar(campoNome = "E-mail")

                //TextField da Senha
                CampoDigitar(campoNome = "Senha")

                Button(
                    onClick = {
                        //Depois alterar para somente logar depois de validar os dados
                        navController.navigate(route = "home/prestador")

                        //Depois fazer a validacao para saber se o usuario é um cliente ou prestador
//                        navController.navigate(route = "home/prestador")
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(60.dp)
                        .clip(RoundedCornerShape(15.dp)),
                    shape = RoundedCornerShape(15.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = AppColors.PrimaryRed,
                        contentColor = Color.White
                    )
                ) {
                    Text(
                        text = "Entrar",
                        fontSize = 24.sp,
                        fontFamily = fontFamily,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                }

                Text(
                    text = "Esqueceu a senha?",
                    fontSize = 14.sp,
                    fontFamily = fontFamily,
                    color = AppColors.DarkBlue,
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.End
                )

                Text(
                    text = "Ainda não tem conta? Cadastre-se",
                    fontSize = 18.sp,
                    fontFamily = fontFamily,
                    color = AppColors.DarkBlue,
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable {
                            navController.navigate(route = "cadastro")
                        },
                    textAlign = TextAlign.Center
                )

            }
        }
    }
}