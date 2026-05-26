package com.example.drivez.ui.cadastro.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.drivez.R
import com.example.drivez.fontFamily
import com.example.drivez.ui.components.CampoDigitar
import com.example.drivez.ui.components.TituloCampo
import com.example.drivez.core.network.theme.AppColors
import com.example.drivez.core.utils.SenhaForca
import com.example.drivez.core.utils.SenhaValidator
import com.example.drivez.ui.components.CadastroCampoDigitar

@Composable
fun FormularioCliente(
    modifier: Modifier = Modifier, navController: NavController, onCadastrarClick: (nome: String, email: String, senha: String) -> Unit
) {
    var nome by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var senha by remember { mutableStateOf("") }
    var confirmaSenha by remember { mutableStateOf("") }
    var senhaStrength by remember { mutableStateOf("Senha fraca") }

    var senhaForca by remember { mutableStateOf(SenhaForca.VAZIA) }

    Column(
        verticalArrangement = Arrangement.spacedBy(10.dp),
        modifier = modifier
    ) {
        TituloCampo("Nome Completo")
        CadastroCampoDigitar(
            campoNome = "Nome Completo",
            placeholder = "Digite seu nome completo",
            value = nome,
            onValueChange = { nome = it }
        )

        TituloCampo("Email")
        CadastroCampoDigitar(
            campoNome = "Email",
            placeholder = "exemplo@gmail.com",
            value = email,
            onValueChange = { email = it }
        )

        TituloCampo("Criação de Senha")
        CadastroCampoDigitar(
            campoNome = "Criação da senha",
            placeholder = "Senha",
            painter = painterResource(R.drawable.baseline_lock_24),
            painterTransform = painterResource(R.drawable.baseline_remove_red_eye_24),
            value = senha,
            onValueChange = {
                senha = it
                senhaForca = SenhaValidator.avaliarForca(it)
            },
            isSenha = true
        )

        if (senhaForca != SenhaForca.VAZIA) {
            Text(
                text = senhaForca.texto,
                fontFamily = fontFamily,
                fontSize = 13.sp,
                fontWeight = FontWeight.Bold,
                color = senhaForca.cor,
                modifier = Modifier.padding(start = 15.dp)
            )
        }

        TituloCampo("Confirmação da Senha")
        CadastroCampoDigitar(
            campoNome = "Confirmação da Senha",
            placeholder = "Repita sua senha",
            value = confirmaSenha,
            onValueChange = { confirmaSenha = it },
            isSenha = true
        )

        Spacer(modifier = Modifier.height(10.dp))

        Button(
            onClick = {
                val senhaValida = senhaForca == SenhaForca.MEDIA || senhaForca == SenhaForca.FORTE

                if (nome.isNotBlank() && email.isNotBlank() && senha == confirmaSenha) {
                    onCadastrarClick(nome, email, senha)
                }
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
                text = "Cadastrar Cliente",
                fontSize = 24.sp,
                fontFamily = fontFamily,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )
        }
    }
}