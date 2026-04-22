package com.example.drivez

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.VerticalDivider
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
import androidx.compose.ui.graphics.RectangleShape
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
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.room.util.copy
import com.example.drivez.components.Avaliacao
import com.example.drivez.components.BottomClienteBar
import com.example.drivez.components.CampoDigitar
import com.example.drivez.components.TituloCampo
import com.example.drivez.model.Categoria
import com.example.drivez.model.Prestador

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            DriveZTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->

                    val navController = rememberNavController()
                    //Fazer a logica do usuario já estar logado ou não e guardar na variavel do startDestination
                    NavHost(
                        navController = navController,
                        startDestination = "login"
                    ){
                        composable("login") {
                            LoginScreen(navController = navController)
                        }
                        composable("cadastro"){
                            CadastroScreen(navController = navController)
                        }
                        composable("home/cliente"){
                            HomeClienteScreen(navController = navController)
                        }
                    }
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

val fontFamily = FontFamily(
    Font(
        googleFont = fontName,
        fontProvider = provider,
        weight = FontWeight.Normal,
        style = FontStyle.Normal
    )
)


@Composable
fun LoginScreen(modifier: Modifier = Modifier, navController: NavController) {

    Scaffold(
        containerColor = Color.White
    ) { paddingValues ->
        Column(
            modifier = modifier
                .padding(paddingValues)
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
                    onClick = {
                        //Depois alterar para somente logar depois de validar os dados
                        navController.navigate(route = "home/cliente")
                    },
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CadastroScreen(navController: NavController) {
    var senhaStrength by remember { mutableStateOf("Senha fraca") }
    var cadastroUserClient by remember { mutableStateOf(true) }
    var cadastroUserPrestador by remember { mutableStateOf(false) }


    Scaffold(
        containerColor = Color.White,
        topBar = {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp, bottom = 16.dp, start = 25.dp, end = 16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(onClick = { navController.popBackStack() }) {
                    Icon(
                        painter = painterResource(R.drawable.baseline_arrow_back_24),
                        contentDescription = "Voltar",
                        tint = Color(0xFF1B2D45),
                        modifier = Modifier.size(40.dp)
                    )
                }
            }
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
                .background(Color.White)
                .verticalScroll(rememberScrollState())
                .padding(start = 25.dp, end = 25.dp, bottom = 80.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {

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
                            fontSize = 18.sp,
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
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold,
                            fontFamily = fontFamily,
                            color = if (cadastroUserPrestador) Color.White else Color(0xFF1B2D45),
                            textAlign = TextAlign.Center
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
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeClienteScreen(navController: NavController) {

    val progresso = remember { Animatable(1f) }

    val listaPrestadores = listOf(
        Prestador(nome = "RIMBEIRO", avaliacao = 2.0, totalAvaliacoes = 45, distancia = 2, categorias = listOf(Categoria(nome = "Guincho"), Categoria(nome = "Mecanico"))),
        Prestador(nome = "RIMBEIRO", avaliacao = 1.0, totalAvaliacoes = 35, distancia = 2, categorias = listOf(Categoria(nome = "Guincho"), Categoria(nome = "Mecanico"))),
        Prestador(nome = "RIMBEIRO", avaliacao = 0.0, totalAvaliacoes = 3, distancia = 2, categorias = listOf(Categoria(nome = "Guincho"), Categoria(nome = "Mecanico"))),
        Prestador(nome = "RIMBEIRO", avaliacao = 3.5, totalAvaliacoes = 10, distancia = 2, categorias = listOf(Categoria(nome = "Guincho"), Categoria(nome = "Mecanico")))
    )

    LaunchedEffect(Unit) {
        progresso.animateTo(
            targetValue = 0f,
            animationSpec = infiniteRepeatable(
                animation = tween(durationMillis = 3 * 60 * 1000, easing = LinearEasing),
                //Logica quando realizar o reset
                repeatMode = RepeatMode.Restart
            )
        )
    }

    Scaffold(
        containerColor = Color.White,
        topBar = {
            TopAppBar(
                title = {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Image(
                            painter = painterResource(R.drawable.logo_home),
                            contentDescription = "DriveZ",
                            modifier = Modifier
                                .width(220.dp)
                                .height(50.dp)
                        )
                    }
                },
                actions = {
                    IconButton(
                        onClick = {
                            //Depois adicionar a logica de trocar de icone e desligar as notificações do app
                        }
                    ) {
                        Icon(
                            painter = painterResource(R.drawable.baseline_notifications_active_24),
                            contentDescription = "Notificação Ativa",
                            modifier = Modifier
                                .size(50.dp)
                                .padding(end = 15.dp)
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.White
                )
            )
        },
        bottomBar = { BottomClienteBar(navController = navController) }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
                .padding(horizontal = 10.dp)
        ) {
            Column(
                modifier = Modifier.padding(16.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column() {
                        Text(
                            text = "Mostrando prestadores próximos a você",
                            fontFamily = fontFamily,
                            fontWeight = FontWeight.SemiBold
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        LinearProgressIndicator(
                            progress = {progresso.value},
                            modifier = Modifier
                                .fillMaxWidth(0.8f)
                                .height(4.dp),
                            color = Color.Red
                        )
                    }
                    Icon(
                        painter = painterResource(R.drawable.baseline_refresh_24),
                        contentDescription = "Atualizar",
                        modifier = Modifier.size(35.dp)
                    )
                }
                Button(
                    onClick = {},
                    modifier = Modifier
                        .padding(top = 8.dp),
                    shape = RoundedCornerShape(15.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFFC52F3E),
                        contentColor = Color.White
                    )
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceEvenly
                    ) {
                        Icon(
                            painter = painterResource(R.drawable.baseline_filter_alt_24),
                            contentDescription = "Filtrar",
                            tint = Color.White
                        )
                        Spacer(modifier = Modifier.width(10.dp))
                        Text(
                            text = "Filtrar"
                        )
                    }
                }
            }
            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight()
                    .padding(bottom = 40.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(20.dp)
            ) {
                items(listaPrestadores){
                    CardPrestador(prestador = it)
                }
            }
        }
    }
}

@Composable
fun CardPrestador(prestador: Prestador, modifier: Modifier = Modifier) {


    Card(
        modifier = modifier
            .clip(RoundedCornerShape(15.dp))
            .border(1.dp, Color(0xFF1B2D45), RoundedCornerShape(15.dp)),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 6.dp,
            pressedElevation = 12.dp
        ),
        colors = CardDefaults.cardColors(
            containerColor = Color(0xFFFAFAFA)
        )
    ) {
        Row(
            modifier = Modifier.padding(vertical = 15.dp, horizontal = 20.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            //Depois substituir por um asyc image
            Icon(
                painter = painterResource(R.drawable.baseline_person_24),
                contentDescription = "Prestador",
                modifier = Modifier.size(70.dp)
                    .border(1.dp, Color(0xFF1B2D45), RoundedCornerShape(100))
            )
            Spacer(modifier = Modifier.width(20.dp))
            Column(
                modifier = Modifier,
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                Text(
                    text = prestador.nome,
                    fontFamily = fontFamily,
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp,
                    color = Color(0xFFC52F3E)
                )
                Row(
                    modifier = Modifier.fillMaxWidth().height(30.dp),
                    horizontalArrangement = Arrangement.spacedBy(10.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {

                    Avaliacao(prestador.avaliacao, 24.dp, 2.dp)

                    Text(
                        text = "(${prestador.totalAvaliacoes})",
                        fontFamily = fontFamily,
                        fontWeight = FontWeight.Bold,
                    )
                    VerticalDivider(
                        modifier = Modifier.fillMaxHeight(),
                        color = Color.Gray,
                        thickness = 1.dp
                    )
                    Text(
                        text = "${prestador.distancia} Km",
                        fontFamily = fontFamily,
                        color = Color.Gray
                    )
                }
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(15.dp)
                ) {
                    prestador.categorias.forEach { item ->
                        Row(
                            modifier = Modifier
                                .clip(RoundedCornerShape(10.dp))
                                .border(1.dp, Color(0xFFAEAEAE), RoundedCornerShape(10.dp))
                        ) {
                            Text(
                                text = item.nome,
                                fontFamily = fontFamily,
                                fontWeight = FontWeight.SemiBold,
                                modifier = Modifier
                                    .padding(vertical = 5.dp, horizontal = 15.dp)
                            )
                        }
                    }
                }

            }
        }
    }
}