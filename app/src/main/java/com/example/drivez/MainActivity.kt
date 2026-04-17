package com.example.drivez

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.googlefonts.Font
import androidx.compose.ui.text.googlefonts.GoogleFont
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.drivez.ui.theme.DriveZTheme
import androidx.core.graphics.toColorInt
import com.example.drivez.components.CampoDigitar
import com.example.drivez.components.TituloCampo

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            DriveZTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    CadastroScreen()
                }
            }
        }
    }
}

val provider = GoogleFont.Provider(
    providerAuthority = "com.google.android.gms.fonts",
    providerPackage = "com.google.android.gms",
    certificates = R.array.com_google_android_gms_fonts_certs
)

val fontName = GoogleFont("Roboto")

//@RequiresApi(Build.VERSION_CODES.Q)
//val fontFamily = FontFamily(
//    Font(googleFont = fontName, fontProvider = provider)
//)

val fontFamily = FontFamily(
    Font(
        googleFont = fontName,
        fontProvider = provider,
        weight = FontWeight.Normal,
        style = FontStyle.Normal
    )
)


@Composable
fun LoginScreen(modifier: Modifier = Modifier) {

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(Color(0xFFE8E8E8))
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
                onClick = {TODO()},
                modifier = Modifier
                    .fillMaxWidth()
                    .height(60.dp)
                    .clip(RoundedCornerShape(15.dp)),
                shape = RoundedCornerShape(15.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFFD53035),
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
                color = Color(0xFF1B2D45),
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.End
            )

            Text(
                text = "Ainda não tem conta? Cadastre-se",
                fontSize = 18.sp,
                fontFamily = fontFamily,
                color = Color(0xFF1B2D45),
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center
            )

        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CadastroScreen() {
    var senhaStrength by remember { mutableStateOf("Senha fraca") }

    var cadastroUserClient by remember { mutableStateOf(true) }
    var cadastroUserPrestador by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .verticalScroll(rememberScrollState())
            .padding(start = 25.dp, end = 25.dp, top = 30.dp, bottom = 80.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        TopAppBar(
            title = { Text("Título da Tela") },
            navigationIcon = {
                //Botao de voltar
                IconButton(
                    onClick = {},
                    modifier = Modifier
                        .size(50.dp)
                        .padding(start = 15.dp)
                ) {
                    Icon(
                        painter = painterResource(R.drawable.baseline_arrow_back_24),
                        contentDescription = "Voltar",
                        tint = Color(0xFF1B2D45),
                        modifier = Modifier.fillMaxSize()
                    )
                }
            },
            colors = TopAppBarDefaults.topAppBarColors(
                containerColor = MaterialTheme.colorScheme.primaryContainer,
                titleContentColor = MaterialTheme.colorScheme.primary,
            )
        )

        Text(
            text = "Cadastro",
            fontWeight = FontWeight.Bold,
            fontFamily = fontFamily,
            fontSize = 30.sp,
            color = Color(0xFF1B2D45),
            modifier = Modifier
                .padding(start = 15.dp)
        )

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(100.dp)
                .border(1.dp, Color(0xFFC52F3E), RoundedCornerShape(20.dp))
        ) {
            Button(
                onClick = {
                    cadastroUserClient = true
                    cadastroUserPrestador = false
                },
                modifier = Modifier
                    .weight(1f)
                    .clip(RoundedCornerShape(topStart = 20.dp, bottomStart = 20.dp)),
                shape = RoundedCornerShape(topStart = 20.dp, bottomStart = 20.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = if(cadastroUserClient) Color(0xFFF13132) else Color.White,
                    contentColor = if (cadastroUserClient) Color.White else Color(0xFF1B2D45)
                )
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = "Sou Cliente",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        fontFamily = fontFamily,
                        color = if (cadastroUserClient) Color.White else Color(0xFF1B2D45)
                    )
                    Spacer(modifier = Modifier.height(10.dp))
                    Text(
                        text = "Pessoas que necessitam ajuda",
                        fontSize = 14.sp,
                        fontFamily = fontFamily,
                        fontWeight = FontWeight.Bold,
                        color = if (cadastroUserClient) Color.White else Color(0xFF1B2D45),
                        textAlign = TextAlign.Center
                    )
                }
            }
            Button(
                onClick = {
                    cadastroUserPrestador = true
                    cadastroUserClient = false
                },
                modifier = Modifier
                    .weight(1f)
                    .clip(RoundedCornerShape(topEnd = 20.dp, bottomEnd = 20.dp)),
                shape = RoundedCornerShape(topEnd = 20.dp, bottomEnd = 20.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = if(cadastroUserPrestador) Color(0xFFF13132) else Color.White,
                    contentColor = if (cadastroUserPrestador) Color.White else Color(0xFF1B2D45)
                )
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = "Sou Prestador",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        fontFamily = fontFamily,
                        color = if (cadastroUserPrestador) Color.White else Color(0xFF1B2D45)
                    )
                    Spacer(modifier = Modifier.height(10.dp))
                    Text(
                        text = "Prestadores de serviço",
                        fontSize = 14.sp,
                        fontFamily = fontFamily,
                        fontWeight = FontWeight.Bold,
                        color = if (cadastroUserPrestador) Color.White else Color(0xFF1B2D45),
                        textAlign = TextAlign.Center
                    )
                }
            }
        }
        Spacer(modifier = Modifier.height(10.dp))

        if (cadastroUserClient && !cadastroUserPrestador){

            Column(
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {

                TituloCampo("Nome Completo")
                CampoDigitar(campoNome = "Nome Completo", placeholder = "Digite seu nome completo")

                TituloCampo("Email")
                CampoDigitar(campoNome = "Email", placeholder = "exemplo@gmail.com")

                TituloCampo("Criação de Senha")
                CampoDigitar(campoNome = "Criação da senha", placeholder = "Senha", painter = painterResource(R.drawable.baseline_lock_24), painterTransform = painterResource(R.drawable.baseline_remove_red_eye_24))

                Text(
                    //Validar o estilo da senha
                    text = senhaStrength,
                    fontFamily = fontFamily,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFFFF0A0A),
                    modifier = Modifier
                        .padding(start = 15.dp)
                )

                CampoDigitar(campoNome = "Confirmação da Senha")

                Spacer(modifier = Modifier.height(10.dp))

                Button(
                    onClick = {TODO()},
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(60.dp)
                        .clip(RoundedCornerShape(15.dp)),
                    shape = RoundedCornerShape(15.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFFD53035),
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
            }

        }else{

            Column(
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                Text(
                    text = "Torne-se um parceiro da DriveZ!",
                    fontFamily = fontFamily,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF1B2D45),
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center,

                    )

                Text(
                    text = "Alcance mais clientes e gerencie suas viagens.",
                    fontFamily = fontFamily,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF1B2D45),
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center
                )

                TituloCampo("Nome Completo")
                CampoDigitar(campoNome = "Nome Completo", placeholder = "Digite seu nome completo")

                TituloCampo("Email")
                CampoDigitar(campoNome = "Email", placeholder = "exemplo@gmail.com")

                TituloCampo("Criação de Senha")
                CampoDigitar(campoNome = "Criação da senha", placeholder = "Senha", painter = painterResource(R.drawable.baseline_lock_24), painterTransform = painterResource(R.drawable.baseline_remove_red_eye_24))

                Text(
                    //Validar o estilo da senha
                    text = senhaStrength,
                    fontFamily = fontFamily,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFFFF0A0A),
                    modifier = Modifier
                        .padding(start = 15.dp)
                )

                CampoDigitar(campoNome = "Confirme a Senha")

                Spacer(modifier = Modifier.height(10.dp))

                Button(
                    onClick = {TODO()},
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(60.dp)
                        .clip(RoundedCornerShape(15.dp)),
                    shape = RoundedCornerShape(15.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFFD53035),
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
            }

        }

    }
}