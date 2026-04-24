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
import androidx.compose.foundation.shape.CircleShape
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.googlefonts.Font
import androidx.compose.ui.text.googlefonts.GoogleFont
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.drivez.ui.theme.DriveZTheme
import androidx.navigation.NavController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.drivez.components.AplicationTopBar
import com.example.drivez.components.Avaliacao
import com.example.drivez.components.BottomClienteBar
import com.example.drivez.components.CampoDigitar
import com.example.drivez.components.TituloCampo
import com.example.drivez.data.model.Categoria
import com.example.drivez.data.model.Pedido
import com.example.drivez.data.model.Prestador
import com.example.drivez.data.model.StatusPedido
import com.example.drivez.util.FormatarData

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
                        composable(
                            route = "home/cliente/servico/{prestadorId}",
                            arguments = listOf(navArgument("prestadorId") { type = NavType.StringType })
                            ){
                            val prestadorId = it.arguments?.getString("prestadorId")
                            ServicoScreen(navController = navController, prestadorId = prestadorId!!)
                        }
                        composable("home/cliente/historico"){
                            RegistroDePedidosScreen(navController = navController)
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
//            Row(
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .padding(top = 16.dp, bottom = 16.dp, start = 25.dp, end = 16.dp),
//                verticalAlignment = Alignment.CenterVertically
//            ) {
//                IconButton(onClick = { navController.popBackStack() }) {
//                    Icon(
//                        painter = painterResource(R.drawable.baseline_arrow_back_24),
//                        contentDescription = "Voltar",
//                        tint = Color(0xFF1B2D45),
//                        modifier = Modifier.size(40.dp)
//                    )
//                }
//            }
            AplicationTopBar(navController = navController)
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
        Prestador(id = 1, nome = "RIMBEIRO", avaliacao = 2.0, totalAvaliacoes = 45, distancia = 2, categorias = listOf(Categoria(nome = "Guincho"), Categoria(nome = "Mecanico"))),
        Prestador(id = 2, nome = "RIMBEIRO", avaliacao = 1.0, totalAvaliacoes = 35, distancia = 2, categorias = listOf(Categoria(nome = "Guincho"), Categoria(nome = "Mecanico"))),
        Prestador(id = 3, nome = "RIMBEIRO", avaliacao = 0.0, totalAvaliacoes = 3, distancia = 2, categorias = listOf(Categoria(nome = "Guincho"), Categoria(nome = "Mecanico"))),
        Prestador(id = 4, nome = "RIMBEIRO", avaliacao = 3.5, totalAvaliacoes = 10, distancia = 2, categorias = listOf(Categoria(nome = "Guincho"), Categoria(nome = "Mecanico")))
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
                    CardPrestador(prestador = it, navController = navController)
                }
            }
        }
    }
}

@Composable
fun CardPrestador(prestador: Prestador, modifier: Modifier = Modifier, navController: NavController) {


    Card(
        modifier = modifier
            .border(1.dp, Color(0xFF1B2D45), RoundedCornerShape(15.dp))
            .clickable {
                navController.navigate("home/cliente/servico/${prestador.id}")
            },
        elevation = CardDefaults.cardElevation(
            defaultElevation = 6.dp,
        ),
        colors = CardDefaults.cardColors(
            containerColor = Color(0xFFFAFAFA)
        ),
        shape = RoundedCornerShape(15.dp)
    ) {
        Row(
            modifier = Modifier.padding(vertical = 15.dp, horizontal = 20.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            //Depois substituir por um asyc image
            Icon(
                painter = painterResource(R.drawable.baseline_person_24),
                contentDescription = "Prestador",
                modifier = Modifier
                    .size(70.dp)
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
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(30.dp),
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


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ServicoScreen(navController: NavController, prestadorId: String) {

    //Buscar o prestador pelo ID dele e exibir aqui!

    //Enquanto nao poder fazer requisicoes na API utilizar esse prestador, depois retirar
    val prestadorTeste = Prestador(id = 1, nome = "RIMBEIRO", avaliacao = 2.0,
        descricao = "Especializada em serviços de guincho e assistência veicular, a Rimberio oferece suporte rápido e seguro para o seu veículo. " +
                "Com foco na eficiência e no cuidado com o patrimônio do cliente, estamos prontos para atender emergências com profissionalismo e pontualidade."
        , totalAvaliacoes = 45, distancia = 2, categorias = listOf(Categoria(nome = "Guincho"), Categoria(nome = "Mecanico")))

    Scaffold(
        topBar = {
            AplicationTopBar(navController = navController)
        }
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
                modifier = Modifier
                    .size(150.dp)
                    .border(1.dp, Color(0xFF1B2D45), RoundedCornerShape(100))
            )

            Avaliacao(prestadorTeste.avaliacao, 34.dp, 3.dp)

            Text(
                text = prestadorTeste.nome,
                fontFamily = fontFamily,
                fontWeight = FontWeight.Bold,
                fontSize = 26.sp,
                textAlign = TextAlign.Center
            )

            Text(
                text = prestadorTeste.descricao,
                fontFamily = fontFamily,
                fontWeight = FontWeight.SemiBold,
                fontSize = 20.sp,
                textAlign = TextAlign.Justify
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ) {
                prestadorTeste.categorias.forEachIndexed {index, item ->
                    Row(
                        modifier = Modifier
                            .clip(RoundedCornerShape(10.dp))
                            .border(1.dp, Color(0xFF1B2D45), RoundedCornerShape(10.dp))
                            .background(Color(0xFF1B2D45))
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
                    if(prestadorTeste.categorias.getOrNull(index + 1) != null){
                        Spacer(modifier = Modifier.width(20.dp))
                    }
                }
            }

            Button(
                onClick = {
                    //Logica da solicitacao
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(100.dp)
                    .padding(horizontal = 10.dp, vertical = 15.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFFC52F3E),
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


@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun RegistroDePedidosScreen(navController: NavController) {

    val listaDePedidos = listOf(
        Pedido(1, StatusPedido.PENDENTE, "2026-04-24 09:00", "Av. Paulista, 1000", "Rua Augusta, 200", "Entrega de documentos", "2.5 km", 101, 201),
        Pedido(2, StatusPedido.EM_ANDAMENTO, "2026-04-24 10:30", "Rua da Consolação, 50", "Av. Rebouças, 800", "Compra de supermercado", "4.0 km", 102, 202),
        Pedido(3, StatusPedido.FINALIZADO, "2026-04-24 08:00", "Praça da Sé, 10", "Bairro Liberdade, 100", "Entrega de presente", "1.2 km", 103, 203),
        Pedido(4, StatusPedido.PENDENTE, "2026-04-24 11:00", "Av. Brigadeiro, 500", "Rua Oscar Freire, 300", "Transporte de móveis", "5.8 km", 104, 204),
        Pedido(5, StatusPedido.EM_ANDAMENTO, "2026-04-24 12:15", "Aeroporto de Congonhas", "Hotel Transamerica", "Levar bagagem", "8.5 km", 105, 205),
        Pedido(6, StatusPedido.FINALIZADO, "2026-04-24 07:30", "Centro Histórico", "Rodoviária", "Envio de pacote urgente", "12.0 km", 106, 206)
    )

    Scaffold(
        topBar = {
            AplicationTopBar(navController = navController, titulo = "Registro de Pedidos")
        }
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(15.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            items(listaDePedidos){ pedido ->
                CardHistoricoPedido(pedido)
            }
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun CardHistoricoPedido(pedido: Pedido, modifier: Modifier = Modifier) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 15.dp, horizontal = 15.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = FormatarData(pedido.dataSolicitacao),
                fontFamily = fontFamily,
                fontSize = 30.sp,
                fontWeight = FontWeight.Bold,
            )
        }
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 10.dp, horizontal = 15.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            Text(
                text = "Origem",
                fontFamily = fontFamily,
                fontSize = 30.sp,
                fontWeight = FontWeight.Bold,
            )
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(15.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .size(10.dp)
                        .clip(CircleShape)
                        .background(Color.Black)
                )
                Text(
                    text = pedido.enderecoOrigem,
                    fontFamily = fontFamily,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                )
            }
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(15.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    painter = painterResource(R.drawable.outline_arrow_downward_24),
                    contentDescription = null,
                    modifier = Modifier.width(30.dp).height(40.dp)
//                    .align(Alignment.CenterHorizontally)
                )
                Text(
                    text = "Origem",
                    fontFamily = fontFamily,
                    fontSize = 30.sp,
                    fontWeight = FontWeight.Bold,
                )
            }
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(15.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .size(10.dp)
                        .clip(CircleShape)
                        .background(Color.Black)
                )
                Text(
                    text = pedido.enderecoDestino,
                    fontFamily = fontFamily,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                )
            }
        }
        Row(
            modifier = Modifier.fillMaxWidth()
                .padding(horizontal = 15.dp, vertical = 15.dp),
            horizontalArrangement = Arrangement.spacedBy(15.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                painter = painterResource(R.drawable.baseline_person_24),
                contentDescription = "Prestador",
                modifier = Modifier
                    .size(40.dp)
                    .border(1.dp, Color.Black, RoundedCornerShape(100))
            )
            Text(
                text = "Prestador: Carlos Oliveira",
                fontFamily = fontFamily,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
            )
        }

    }
}