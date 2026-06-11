package com.example.drivez

import MenuSelecao
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
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
import androidx.compose.foundation.layout.offset
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
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.googlefonts.Font
import androidx.compose.ui.text.googlefonts.GoogleFont
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.drivez.core.network.theme.DriveZTheme
import androidx.navigation.NavController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import android.net.Uri
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Divider
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.layout.ContentScale
import androidx.lifecycle.viewmodel.compose.viewModel
import coil3.compose.AsyncImage
import com.example.drivez.ui.components.AddressTimeline
import com.example.drivez.ui.components.AplicationTopBar
import com.example.drivez.ui.components.Avaliacao
import com.example.drivez.ui.components.BalaoChat
import com.example.drivez.ui.components.BotaoAceitarArrastavel
import com.example.drivez.ui.components.BotaoFlutuante
import com.example.drivez.ui.components.BottomClienteBar
import com.example.drivez.ui.components.BottomPrestadorBar
import com.example.drivez.ui.components.CampoConfigurarPedido
import com.example.drivez.ui.components.CampoDigitar
import com.example.drivez.ui.components.CardCategoria
import com.example.drivez.ui.components.CardCliente
import com.example.drivez.ui.components.CardConfirmacao
import com.example.drivez.ui.components.CardVeiculo
import com.example.drivez.ui.components.ClienteCardContato
import com.example.drivez.ui.components.PrestadorCardContato
import com.example.drivez.ui.components.TituloCampo
import com.example.drivez.data.model.Categoria
import com.example.drivez.data.model.CategoriaPedido
import com.example.drivez.data.model.CategoriaServico
import com.example.drivez.data.model.Cliente
import com.example.drivez.data.model.Contato
import com.example.drivez.data.model.Mensagem
import com.example.drivez.data.model.Pedido
import com.example.drivez.data.model.Prestador
import com.example.drivez.data.model.RemetenteMensagem
import com.example.drivez.data.model.StatusMensagem
import com.example.drivez.data.model.StatusPedido
import com.example.drivez.data.model.TipoVeiculo
import com.example.drivez.data.model.Veiculo
import com.example.drivez.core.network.theme.AppColors
import com.example.drivez.ui.cadastro.CadastroScreen
import com.example.drivez.ui.cadastro.CadastroViewModel
import com.example.drivez.ui.home_cliente.HomeClienteScreen
import com.example.drivez.ui.login.LoginScreen
import com.example.drivez.ui.registro_pedidos_cliente.ClienteRegistroDePedidosScreen
import com.example.drivez.util.FormatarData
import kotlinx.coroutines.launch


class MainActivity : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            DriveZTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->

                    val navController = rememberNavController()
                    NavHost(
                        navController = navController,
                        startDestination = "login"
                    ){
                        composable("login") {
                            LoginScreen(navController = navController)
                        }
                        composable("cadastro") {

                            val cadastroViewModel: CadastroViewModel = viewModel()

                            CadastroScreen(
                                navController = navController,
                                viewModel = cadastroViewModel,
                            )
                        }

                        composable("completar_cadastro_cliente/{userId}") { backStackEntry ->
                            val userId = backStackEntry.arguments?.getString("userId")
                        }

                        composable("completar_cadastro_prestador/{userId}") { backStackEntry ->
                            val userId = backStackEntry.arguments?.getString("userId")
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
                        composable(
                            "home/cliente/historico/{clienteId}",
                            arguments = listOf(navArgument("clienteId") {type = NavType.StringType})
                        ){
                            val clienteId = it.arguments?.getString("clienteId")
                            ClienteRegistroDePedidosScreen(navController = navController)
                        }
                        composable("home/cliente/contatos") {
                            ContatosClienteScreen(navController = navController)
                        }
                        composable(
                            route = "home/cliente/perfil",
                        ) {
                            ClientePerfilScreen(navController = navController)
                        }
                        composable(
                            route = "home/cliente/contatos/conversa/{contatoId}",
                            arguments = listOf(navArgument("contatoId") { type = NavType.StringType })
                        ) {
                            val contatoId = it.arguments?.getString("contatoId")
                            ClienteConversaScreen(navController = navController, contatoId = contatoId!!)
                        }
                        composable(
                            route = "home/cliente/servico_status/{prestadorId}/{isSOS}",
                            arguments = listOf(
                                navArgument("prestadorId") { type = NavType.StringType },
                                navArgument("isSOS"){type = NavType.BoolType; defaultValue = false}
                            )
                        ) {
                            val prestadorId = it.arguments?.getString("prestadorId")
                            val isSOS = it.arguments?.getBoolean("isSOS")
                            ServiceStatusScreen(navController = navController, userId = prestadorId!!, isSOS!!)
                        }
                        composable(
                            route = "home/cliente/pedido/{isSOS}",
                            arguments = listOf(
                                navArgument("isSOS"){type = NavType.BoolType; defaultValue = false}
                            )
                        ){
                            val isSOS = it.arguments?.getBoolean("isSOS")
                            ConfigurarPedidoScreen(navController = navController, isSOS!!)
                        }

                        composable(
                            "home/cliente/perfil/garagem/{clienteId}",
                            arguments = listOf(navArgument("clienteId") { type = NavType.StringType })
                        ) {
                            val clienteId = it.arguments?.getString("clienteId")
                            ClienteGaragemVirtualScreen(navController = navController, clienteId = clienteId!!)
                        }

                        //Prestador

                        composable("home/prestador"){
                            HomePrestadorScreen(navController = navController)
                        }

                        composable(
                            route = "home/prestador/detalhes_solicitacao/{clienteId}",
                            arguments = listOf(navArgument("clienteId") { type = NavType.StringType })
                        ) {
                            val clienteId = it.arguments?.getString("clienteId")
                            DetalhesSolicitacaoScreen(navController = navController, clienteId = clienteId!!)
                        }
                        composable(
                            route = "home/prestador/detalhes_solicitacao/emergencia/{clienteId}",
                            arguments = listOf(navArgument("clienteId") { type = NavType.StringType })
                        ) {
                            val clienteId = it.arguments?.getString("clienteId")
                            DetalhesSolicitacaoEmergenciaScreen(navController = navController, clienteId = clienteId!!,
                                onCorridaAceita = {})
                        }

                        composable(
                            route = "home/prestador/servico_status/{clienteId}/",
                            arguments = listOf(navArgument("clienteId") { type = NavType.StringType })
                        ) {
                            val clienteId = it.arguments?.getString("clienteId")
                            DetalhesSolicitacaoScreen(navController = navController, clienteId = clienteId!!)
                        }

                        composable(
                            route = "home/prestador/servico_status/{clienteId}/{isSOS}",
                            arguments = listOf(
                                navArgument("clienteId") { type = NavType.StringType },
                                navArgument("isSOS"){type = NavType.BoolType; defaultValue = false}
                            )
                        ) {
                            val clienteId = it.arguments?.getString("clienteId")
                            val isSOS = it.arguments?.getBoolean("isSOS")
                            ServiceStatusScreen(navController = navController, userId = clienteId!!, isSOS!!)
                        }

                        composable("home/prestador/contatos") {
                            ContatosPrestadorScreen(navController = navController)
                        }

                        composable(
                            route = "home/prestador/contatos/conversa/{contatoId}",
                            arguments = listOf(navArgument("contatoId") { type = NavType.StringType })
                        ) {
                            val contatoId = it.arguments?.getString("contatoId")
                            PrestadorConversaScreen(navController = navController, contatoId = contatoId!!)
                        }

                        composable("home/prestador/perfil") {
                            PrestadorPerfilScreen(navController = navController)
                        }

                        composable(
                            "home/prestador/perfil/garagem/{prestadorId}",
                            arguments = listOf(navArgument("prestadorId") { type = NavType.StringType })
                        ) {
                            val prestadorId = it.arguments?.getString("prestadorId")
                            PrestadorGaragemVirtualScreen(navController = navController, prestadorId = prestadorId!!)
                        }

                        composable("home/prestador/historico"){
                            PrestadorRegistroDePedidosScreen(navController = navController)
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

//@Composable
//fun CardPrestador(prestador: Prestador, modifier: Modifier = Modifier, navController: NavController) {
//
//
//    Card(
//        modifier = modifier
//            .border(1.dp, AppColors.DarkBlue, RoundedCornerShape(15.dp))
//            .clickable {
//                navController.navigate("home/cliente/servico/${prestador.id}")
//            },
//        elevation = CardDefaults.cardElevation(
//            defaultElevation = 6.dp,
//        ),
//        colors = CardDefaults.cardColors(
//            containerColor = AppColors.CardBackground
//        ),
//        shape = RoundedCornerShape(15.dp)
//    ) {
//        Row(
//            modifier = Modifier.padding(vertical = 15.dp, horizontal = 20.dp),
//            verticalAlignment = Alignment.CenterVertically,
//        ) {
//            //Depois substituir por um asyc image
//            Icon(
//                painter = painterResource(R.drawable.baseline_person_24),
//                contentDescription = "Prestador",
//                tint = AppColors.DarkBlue,
//                modifier = Modifier
//                    .size(70.dp)
//                    .border(1.dp, AppColors.DarkBlue, RoundedCornerShape(100))
//            )
//            Spacer(modifier = Modifier.width(20.dp))
//            Column(
//                modifier = Modifier,
//                verticalArrangement = Arrangement.spacedBy(10.dp)
//            ) {
//                Text(
//                    text = prestador.nome,
//                    fontFamily = fontFamily,
//                    fontWeight = FontWeight.Bold,
//                    fontSize = 18.sp,
//                    color = AppColors.SecondaryRed
//                )
//                Row(
//                    modifier = Modifier
//                        .fillMaxWidth()
//                        .height(30.dp),
//                    horizontalArrangement = Arrangement.spacedBy(10.dp),
//                    verticalAlignment = Alignment.CenterVertically
//                ) {
//
//                    Avaliacao(prestador.avaliacao, 24.dp, 2.dp)
//
//                    Text(
//                        text = "(${prestador.totalAvaliacoes})",
//                        fontFamily = fontFamily,
//                        fontWeight = FontWeight.Bold,
//                        color = Color.Black,
//                    )
//                }
//                Row(
//                    modifier = Modifier.fillMaxWidth(),
//                    horizontalArrangement = Arrangement.spacedBy(15.dp)
//                ) {
//                    prestador.categorias.forEach { item ->
//                        Row(
//                            modifier = Modifier
//                                .clip(RoundedCornerShape(10.dp))
//                                .border(1.dp, AppColors.BorderGray, RoundedCornerShape(10.dp))
//                        ) {
//                            Text(
//                                text = item.nome,
//                                fontFamily = fontFamily,
//                                fontWeight = FontWeight.SemiBold,
//                                color = Color.Black,
//                                modifier = Modifier
//                                    .padding(vertical = 5.dp, horizontal = 15.dp)
//                            )
//                        }
//                    }
//                }
//
//            }
//        }
//    }
//}


@Composable
fun ServicoScreen(navController: NavController, prestadorId: String) {

    //Buscar o prestador pelo ID dele e exibir aqui!

    //Enquanto nao poder fazer requisicoes na API utilizar esse prestador, depois retirar
    val prestadorTeste = Prestador(id = 1, nome = "RIMBEIRO", avaliacao = 2.0,
        descricao = "Especializada em serviços de guincho e assistência veicular, a Rimberio oferece suporte rápido e seguro para o seu veículo. " +
                "Com foco na eficiência e no cuidado com o patrimônio do cliente, estamos prontos para atender emergências com profissionalismo e pontualidade."
        , totalAvaliacoes = 45, categorias = listOf(Categoria(nome = "Guincho"), Categoria(nome = "Mecanico")))

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
                prestadorTeste.categorias.forEachIndexed {index, item ->
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
                    if(prestadorTeste.categorias.getOrNull(index + 1) != null){
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

//@RequiresApi(Build.VERSION_CODES.O)
//@Composable
//fun ClienteRegistroDePedidosScreen(navController: NavController) {
//
//    val listaDePedidos = listOf(
//        Pedido(1, StatusPedido.PENDENTE, "2026-04-24 09:00", "Av. Paulista, 1000", "Rua Augusta, 200", "Entrega de documentos", "2.5 km", 101, 201),
//        Pedido(2, StatusPedido.EM_ANDAMENTO, "2026-04-24 10:30", "Rua da Consolação, 50", "Av. Rebouças, 800", "Compra de supermercado", "4.0 km", 102, 202),
//        Pedido(3, StatusPedido.FINALIZADO, "2026-04-24 08:00", "Praça da Sé, 10", "Bairro Liberdade, 100", "Entrega de presente", "1.2 km", 103, 203),
//        Pedido(4, StatusPedido.PENDENTE, "2026-04-24 11:00", "Av. Brigadeiro, 500", "Rua Oscar Freire, 300", "Transporte de móveis", "5.8 km", 104, 204),
//        Pedido(5, StatusPedido.EM_ANDAMENTO, "2026-04-24 12:15", "Aeroporto de Congonhas", "Hotel Transamerica", "Levar bagagem", "8.5 km", 105, 205),
//        Pedido(6, StatusPedido.FINALIZADO, "2026-04-24 07:30", "Centro Histórico", "Rodoviária", "Envio de pacote urgente", "12.0 km", 106, 206)
//    )
//
//    Scaffold(
//        topBar = {
//            AplicationTopBar(navController = navController, titulo = "Registro de Pedidos", retornavel = false)
//        },
//        bottomBar = {
//            BottomClienteBar(navController = navController, shadow = false)
//        },
//        containerColor = Color.White
//    ) { paddingValues ->
//        LazyColumn(
//            modifier = Modifier
//                .padding(paddingValues)
//                .fillMaxSize(),
//            verticalArrangement = Arrangement.spacedBy(15.dp),
//            horizontalAlignment = Alignment.CenterHorizontally
//        ) {
//            items(listaDePedidos){ pedido ->
//                ClienteCardHistoricoPedido(pedido)
//            }
//        }
//    }
//}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun ClienteCardHistoricoPedido(pedido: Pedido, modifier: Modifier = Modifier) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 15.dp, horizontal = 15.dp)
            .clip(RoundedCornerShape(15.dp))
            .border(1.dp, Color.Black, RoundedCornerShape(15.dp)),
        colors = CardDefaults.cardColors(
            containerColor = Color(0xFFF5F5F5),
            contentColor = Color.Black
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 6.dp,
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 30.dp)
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
                    fontWeight = FontWeight.ExtraBold,
                )
            }
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 10.dp, horizontal = 20.dp)
                    .border(1.dp, Color.Black, RoundedCornerShape(15.dp)),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 15.dp, end = 15.dp, top = 20.dp),
                    horizontalArrangement = Arrangement.spacedBy(15.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        painter = painterResource(R.drawable.outline_arrow_downward_24),
                        contentDescription = null,
                        modifier = Modifier
                            .width(30.dp)
                            .height(40.dp),
//                    .align(Alignment.CenterHorizontally)
                        tint = Color.Transparent
                    )
                    Text(
                        text = "Origem",
                        fontFamily = fontFamily,
                        fontSize = 30.sp,
                        fontWeight = FontWeight.Bold,
                    )
                }
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 15.dp),
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
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 15.dp),
                    horizontalArrangement = Arrangement.spacedBy(15.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        painter = painterResource(R.drawable.outline_arrow_downward_24),
                        contentDescription = null,
                        modifier = Modifier
                            .width(30.dp)
                            .height(40.dp)
//                    .align(Alignment.CenterHorizontally)
                    )
                    Text(
                        text = "Destino",
                        fontFamily = fontFamily,
                        fontSize = 30.sp,
                        fontWeight = FontWeight.Bold,
                    )
                }
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 15.dp, end = 15.dp, bottom = 25.dp),
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
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 15.dp, vertical = 15.dp),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    painter = painterResource(R.drawable.baseline_person_24),
                    contentDescription = "Prestador",
                    modifier = Modifier
                        .size(40.dp)
                        .border(1.dp, Color.Black, RoundedCornerShape(100))
                )
                Spacer(modifier = Modifier.width(15.dp))
                Text(
                    text = "Prestador: Carlos Oliveira",
                    fontFamily = fontFamily,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                )
            }
        }

    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ContatosClienteScreen(navController: NavController) {

    val listaDeContatos = listOf(
        Contato(
            id = "1",
            name = "Rimberio - Guincho",
            ultimaMensagem = "Estou chegando na sua localização.",
            perfilImgUrl = "https://i.pravatar.cc/150?u=1"
        ),
        Contato(
            id = "2",
            name = "Lojão das Baterias",
            ultimaMensagem = "A peça que você solicitou já está disponível.",
            perfilImgUrl = "https://i.pravatar.cc/150?u=2"
        ),
        Contato(
            id = "3",
            name = "Brand - Borracheiro",
            ultimaMensagem = "Pode trazer o veículo, estou livre.",
            perfilImgUrl = "https://i.pravatar.cc/150?u=3"
        ),
        Contato(
            id = "4",
            name = "Auto Repair - Mecanico",
            ultimaMensagem = "O diagnóstico ficou pronto, confira o valor.",
            perfilImgUrl = "https://i.pravatar.cc/150?u=4"
        ),
        Contato(
            id = "5",
            name = "Silva - Eletricista",
            ultimaMensagem = "Preciso de mais detalhes sobre o problema.",
            perfilImgUrl = "https://i.pravatar.cc/150?u=5"
        ),
        Contato(
            id = "6",
            name = "Santos - Funileiro",
            ultimaMensagem = "Podemos agendar para amanhã?",
            perfilImgUrl = "https://i.pravatar.cc/150?u=6"
        )
    )

    Scaffold(
        topBar = {
            AplicationTopBar(navController = navController, titulo = "Contatos", retornavel = false, backgroundColor = AppColors.PrimaryRed,
                textColor = AppColors.TextWhite, iconColor = AppColors.TextWhite)
        },
        bottomBar = {
            BottomClienteBar(navController = navController, shadow = false)
        },
        containerColor = Color.White
    ) { paddingValues ->
        Box(
            modifier = Modifier.fillMaxSize()
        ){
            LazyColumn(
                modifier = Modifier
                    .padding(paddingValues)
                    .padding(horizontal = 15.dp, vertical = 20.dp),
                verticalArrangement = Arrangement.spacedBy(20.dp)
            ) {
                items(listaDeContatos){
                    PrestadorCardContato(it, navController)
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ClienteConversaScreen(navController: NavController, contatoId: String) {

    //Com o id do contato preciso pesquisar as mensagens do prestador e com isso o id dele
    //ira vir e quando solicitar o servico poderá com o id do prestador encontrar a localizacao dele
    //para comparar com quem pediu e dizer a distancia e também exibir a rua que o prestador está
    //e onde o cliente esta

    val contatoTeste = Contato(
        id = "100",
        name = "Rimberio - Guincho",
        ultimaMensagem = "Estou chegando na sua localização.",
        perfilImgUrl = "https://i.pravatar.cc/150?u=1"
    )

    //Lista de Mensagens para fazer o design
    val listaDeMensagens = listOf(
        Mensagem(
            id = "1",
            contatoId = "200", // ID do Prestador
            remententeId = "100", // ID do Cliente
            texto = "Olá, meu carro parou na rodovia.",
            horario = "10:00",
            status = StatusMensagem.LIDA,
            remetenteMensagem = RemetenteMensagem.CLIENTE
        ),
        Mensagem(
            id = "2",
            contatoId = "100",
            remententeId = "200",
            texto = "Bom dia! Qual seria o modelo do veículo?",
            horario = "10:02",
            status = StatusMensagem.LIDA,
            remetenteMensagem = RemetenteMensagem.PRESTADOR
        ),
        Mensagem(
            id = "3",
            contatoId = "200",
            remententeId = "100",
            texto = "É um sedan prata. Segue a foto do local.",
            horario = "10:05",
            status = StatusMensagem.LIDA,
            remetenteMensagem = RemetenteMensagem.CLIENTE
        ),
        Mensagem(
            id = "4",
            contatoId = "200",
            remententeId = "100",
            texto = null,
            imgUrl = "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRs17aTBsPEIrihX-smPkNhd9ccDK3O8tj-6Q&s",
            horario = "10:05",
            status = StatusMensagem.ENTREGUE,
            remetenteMensagem = RemetenteMensagem.CLIENTE
        ),
        Mensagem(
            id = "5",
            contatoId = "100",
            remententeId = "200",
            texto = "Recebido. O guincho chega em 10 minutos!",
            horario = "10:07",
            status = StatusMensagem.ENVIADA,
            remetenteMensagem = RemetenteMensagem.PRESTADOR
        )
    )

    var textoState by remember {
        mutableStateOf("")
    }

    val listState = rememberLazyListState()

    var showCard by remember { mutableStateOf(false) }

    LaunchedEffect(listaDeMensagens.size) {
        if (listaDeMensagens.isNotEmpty()) {
            listState.scrollToItem(listaDeMensagens.size - 1)
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                modifier = Modifier.height(90.dp),
                title = {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(15.dp)
                    ) {
                        IconButton(onClick = { navController.popBackStack() }) {
                            Icon(
                                painter = painterResource(R.drawable.baseline_arrow_back_24),
                                contentDescription = "Voltar",
                                tint = Color.White,
                                modifier = Modifier.size(40.dp)
                            )
                        }
                        AsyncImage(
                            model = "${contatoTeste.perfilImgUrl}",
                            placeholder = painterResource(R.drawable.baseline_person_24),
                            error = painterResource(R.drawable.baseline_person_24),
                            contentDescription = null,
                            modifier = Modifier
                                .size(50.dp)
                                .clip(RoundedCornerShape(100))
                                .border(1.dp, Color.White, RoundedCornerShape(100)),
                        )
                        Text(
                            text = contatoTeste.name,
                            fontFamily = fontFamily,
                            fontWeight = FontWeight.SemiBold,
                            color = Color.White,
                            fontSize = 20.sp
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = AppColors.PrimaryRed,
                ),
            )
        },
        bottomBar = {
            BottomAppBar(
                modifier = Modifier
                    .padding(horizontal = 15.dp, vertical = 15.dp)
                    .height(120.dp)
                    .navigationBarsPadding()
                    .imePadding()
                    .clip(RoundedCornerShape(20.dp))
                    .border(1.dp, AppColors.DarkBlue, RoundedCornerShape(20.dp)),
                actions = {
                    Row(
                        modifier = Modifier
                            .padding(horizontal = 8.dp)
                            .fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        IconButton(
                            onClick = {}
                        ) {
                            Image(
                                painter = painterResource(R.drawable.gallery_icon),
                                contentDescription = "Anexar"
                            )
                        }

                        TextField(
                            value = textoState,
                            onValueChange = { textoState = it },
                            modifier = Modifier
                                .weight(1f)
                                .padding(horizontal = 4.dp),
                            placeholder = { Text("Mensagem...") },
                            maxLines = 5,
                            colors = TextFieldDefaults.colors(
                                focusedContainerColor = Color.Transparent,
                                unfocusedContainerColor = Color.Transparent,
                                focusedIndicatorColor = Color.Transparent,
                                unfocusedIndicatorColor = Color.Transparent
                            )
                        )

                        IconButton(
                            onClick = {

                            },
                            enabled = textoState.isNotBlank()
                        ) {
                            Image(
                                painter = painterResource(R.drawable.send_icon),
                                contentDescription = "Enviar",
                            )
                        }
                    }
                },
                containerColor = AppColors.CardBackground
            )
        },
        containerColor = AppColors.BackgroundConversaYellow
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
        ){
            LazyColumn(
                contentPadding = PaddingValues(vertical = 20.dp),
                state = listState
            ) {
                items(listaDeMensagens){ item ->
                    BalaoChat(item, false)
                }
            }
            BotaoFlutuante(onClick = {
                showCard = true
            })
        }
    }
    if(showCard){
        CardConfirmacao(pergunta = "Solicitar Prestador?", onBackClick = { showCard = false },
            onConfirmClick = {navController.navigate(route = "home/cliente/pedido/false")})
    }

}

@Composable
fun ServiceStatusScreen(navController: NavController, userId: String, isSOS: Boolean) {

    var servicoAceitoState by remember { mutableStateOf(false) }

    var cardState by remember { mutableStateOf(false) }

    var chatState by remember { mutableStateOf(false) }

    var servicoState by remember { mutableStateOf(false) }

    val prestadorTeste = Prestador(id = 1, nome = "RIMBEIRO", avaliacao = 2.0, perfilImgUrl = "https://i.pravatar.cc/150?u=1", totalAvaliacoes = 45, categorias = listOf(Categoria(nome = "Guincho"), Categoria(nome = "Mecanico")))

    Box(modifier = Modifier.fillMaxSize()) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFFE0E0E0)),
            contentAlignment = Alignment.Center
        ) {
            Text("Google Maps será carregado aqui")
        }

        IconButton(
            onClick = {
                cardState = true
            },
            modifier = Modifier
                .padding(16.dp)
                .align(Alignment.TopStart)
                .background(Color.White, CircleShape)
        ) {
            Icon(
                painter = painterResource(R.drawable.baseline_arrow_back_24),
                contentDescription = "Voltar",
                tint = AppColors.DarkBlue
            )
        }

        //Quando o servicoState se tornar true significa que o servico foi concluido e com isso a aplicacao deve
        //exibir a o card de avaliacao do prestador (Pensar na ideia do WebSocket ou na notificacao)

        CardBuscandoPrestador(
            cancelarOnClick = {cardState = true},
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(horizontal = 16.dp)
                .navigationBarsPadding(),
            isSOS = isSOS
        )

//        CardServicoStatus(
//            modifier = Modifier
//                .align(Alignment.BottomCenter)
//                .padding(16.dp)
//                .navigationBarsPadding(),
//            prestador = prestadorTeste,
//            cancelarOnClick = {
//                cardState = true
//            },
//            chatRapidoClick = {
//                chatState = true
//            }
//        )
//        CardAvaliacao(
//            navController = navController,
//            prestador = prestadorTeste
//        )
    }
    if(cardState){
        CardConfirmacao(pergunta = "Cancelar Solicitação?", onBackClick = { cardState = false  },
            onConfirmClick = {
                navController.navigate(route = "home/cliente")
            })
    }
    if(chatState){
        ChatRapidoScreen(backOnClick = {chatState = false})
    }
}

@Composable
fun ClientePerfilScreen(navController: NavController) {

    var imagemSelecionada by rememberSaveable { mutableStateOf<Uri?>(null) }

    val photoPickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia(),
        onResult = { uri: Uri? -> imagemSelecionada = uri }
    )

    //Quando ir fazer o viewModel fazer a parte de salvar o usuario logado globalmente e salva-lo para chegar nessa tela aqui
    Scaffold(
        topBar = {
            AplicationTopBar(navController = navController, titulo = "Meu Perfil", retornavel = true)
        },
        containerColor = Color.White
    ) { paddingValues ->
        Card(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
                .padding(bottom = 50.dp, start = 20.dp, end = 20.dp)
                .verticalScroll(rememberScrollState())
                .border(1.dp, AppColors.BorderGray, RoundedCornerShape(15.dp)),
            shape = RoundedCornerShape(15.dp),
            colors = CardDefaults.cardColors(
                containerColor = AppColors.CardBackground,
            ),
            elevation = CardDefaults.cardElevation(
                defaultElevation = 10.dp
            )
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 20.dp, horizontal = 15.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(30.dp)
            ) {
                Box(
                    modifier = Modifier
                        .size(190.dp)
                ) {
                    //Depois pensar na logica para pesquisar o id do usuario Logado e retornar as informações aqui

                    if(imagemSelecionada != null){
                        AsyncImage(
                            model = imagemSelecionada,
                            contentDescription = "Foto de Perfil",
                            modifier = Modifier
                                .fillMaxSize()
                                .clip(CircleShape)
                                .clickable {
                                    photoPickerLauncher.launch(
                                        PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
                                    )
                                },
                            contentScale = ContentScale.Crop
                        )
                    }else{
                        Icon(
                            painter = painterResource(R.drawable.baseline_person_24),
                            contentDescription = null,
                            tint = AppColors.DarkBlue,
                            modifier = Modifier
                                .fillMaxSize()
                                .border(1.dp, AppColors.SecondaryRed, RoundedCornerShape(100))
                                .clickable {
                                    photoPickerLauncher.launch(
                                        PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
                                    )
                                },
                        )
                    }

                    IconButton(
                        onClick = {
                            photoPickerLauncher.launch(
                                PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
                            )
                        },
                        modifier = Modifier
                            .align(Alignment.BottomEnd)
                    ) {
                        Icon(
                            painter = painterResource(R.drawable.baseline_camera_alt_24),
                            contentDescription = "Alterar Foto",
                            tint = AppColors.DarkBlue,
                            modifier = Modifier
                                .size(30.dp)
                                .offset(x = 5.dp, y = 5.dp)
                        )
                    }
                }
                Avaliacao(4.0, 30.dp, 3.dp)
                Text(
                    text = "Nome do Usuario",
                    fontFamily = fontFamily,
                    fontWeight = FontWeight.SemiBold,
                    color = Color.Black,
                    fontSize = 25.sp
                )
                Card(
                    modifier = Modifier
                        .border(1.dp, AppColors.BorderGray, RoundedCornerShape(15.dp)),
                    shape = RoundedCornerShape(15.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = AppColors.CardBackground,
                    ),
                    elevation = CardDefaults.cardElevation(
                        defaultElevation = 10.dp
                    )
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .fillMaxHeight()
                            .padding(vertical = 20.dp, horizontal = 20.dp),
                        verticalArrangement = Arrangement.spacedBy(20.dp)
                    ) {
                        Text(
                            text = "Dados Pessoais",
                            fontFamily = fontFamily,
                            color = Color.Black,
                            fontWeight = FontWeight.Bold,
                            fontSize = 20.sp
                        )

                        CampoDigitar(campoNome = "Nome do Usuario", alteravel = true,
                            painter = painterResource(R.drawable.baseline_person_24),
                            painterTransform = painterResource(R.drawable.baseline_person_24),
                            iconFim = false)


                        CampoDigitar( campoNome = "CPF/CNPJ", alteravel = false)

                        //Depois alterar pelo icone do email
                        CampoDigitar(campoNome = "Email", alteravel = true,
                            painter = painterResource(R.drawable.baseline_person_24),
                            painterTransform = painterResource(R.drawable.baseline_person_24),
                            iconFim = false)

                        //Depois alterar pelo icone do telefone
                        CampoDigitar(campoNome = "Telefone", alteravel = true,
                            painter = painterResource(R.drawable.baseline_person_24),
                            painterTransform = painterResource(R.drawable.baseline_person_24),
                            iconFim = false)

                        Button(
                            onClick = {},
                            modifier = Modifier
                                .height(50.dp)
                                .width(140.dp)
                                .clip(RoundedCornerShape(15.dp))
                                .border(1.dp, Color.Black, RoundedCornerShape(15.dp)),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = Color.Transparent,
                                contentColor = AppColors.SecondaryRed
                            )
                        ) {
                            Text(
                                text = "Alterar Senha",
                                fontFamily = fontFamily,
                                fontWeight = FontWeight.SemiBold,
                                fontSize = 12.sp
                            )
                        }
                    }
                    Text(
                        text = "Excluir Conta",
                        style = TextStyle(
                            textDecoration = TextDecoration.Underline,
                            fontSize = 10.sp
                        ),
                        fontFamily = fontFamily,
                        fontWeight = FontWeight.SemiBold,
                        color = AppColors.PlaceholderGray,
                        modifier = Modifier
                            .align(Alignment.End)
                            .padding(end = 40.dp, bottom = 40.dp)
                    )
                }
                Text(
                    text = "Salvar Alterações?",
                    fontFamily = fontFamily,
                    fontWeight = FontWeight.SemiBold,
                    color = Color.Black,
                    fontSize = 25.sp
                )
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    Button(
                        onClick = {},
                        colors = ButtonDefaults.buttonColors(
                            containerColor = AppColors.SecondaryRed,
                            contentColor = Color.White
                        ),
                        modifier = Modifier
                            .height(50.dp),
                        shape = RoundedCornerShape(20.dp)
                    ) {
                        Text(
                            text = "Cancelar",
                            fontFamily = fontFamily,
                            fontWeight = FontWeight.SemiBold,
                            color = Color.White,
                            fontSize = 16.sp
                        )
                    }
                    Spacer(modifier = Modifier.width(30.dp))
                    Button(
                        onClick = {},
                        colors = ButtonDefaults.buttonColors(
                            containerColor = AppColors.ConfirmGreen,
                            contentColor = Color.White
                        ),
                        modifier = Modifier
                            .height(50.dp),
                        shape = RoundedCornerShape(20.dp)
                    ) {
                        Text(
                            text = "Confirmar",
                            fontFamily = fontFamily,
                            fontWeight = FontWeight.SemiBold,
                            color = Color.White,
                            fontSize = 16.sp
                        )
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChatRapidoScreen(backOnClick: () -> Unit) {
    val contatoTeste = Contato(
        id = "100",
        name = "Rimberio - Guincho",
        ultimaMensagem = "Estou chegando na sua localização.",
        perfilImgUrl = "https://i.pravatar.cc/150?u=1"
    )

    //Lista de Mensagens para fazer o design
    val listaDeMensagens = listOf(
        Mensagem(
            id = "1",
            contatoId = "200", // ID do Prestador
            remententeId = "100", // ID do Cliente
            texto = "Olá, meu carro parou na rodovia.",
            horario = "10:00",
            status = StatusMensagem.LIDA,
            remetenteMensagem = RemetenteMensagem.CLIENTE
        ),
        Mensagem(
            id = "2",
            contatoId = "100",
            remententeId = "200",
            texto = "Bom dia! Qual seria o modelo do veículo?",
            horario = "10:02",
            status = StatusMensagem.LIDA,
            remetenteMensagem = RemetenteMensagem.PRESTADOR
        ),
        Mensagem(
            id = "3",
            contatoId = "200",
            remententeId = "100",
            texto = "É um sedan prata. Segue a foto do local.",
            horario = "10:05",
            status = StatusMensagem.LIDA,
            remetenteMensagem = RemetenteMensagem.CLIENTE
        ),
        Mensagem(
            id = "4",
            contatoId = "100",
            remententeId = "200",
            texto = "Recebido. O guincho chega em 10 minutos!",
            horario = "10:07",
            status = StatusMensagem.ENVIADA,
            remetenteMensagem = RemetenteMensagem.PRESTADOR
        )
    )

    var textoState by remember {
        mutableStateOf("")
    }

    val listState = rememberLazyListState()

    LaunchedEffect(listaDeMensagens.size) {
        if (listaDeMensagens.isNotEmpty()) {
            listState.scrollToItem(listaDeMensagens.size - 1)
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        // Avatar simplificado (substitua por AsyncImage no futuro)
                        Surface(
                            modifier = Modifier.size(40.dp),
                            shape = CircleShape,
                            color = Color.LightGray,
                            tonalElevation = 6.dp,
                            shadowElevation = 8.dp
                        ) {
                            Icon(
                                painter = painterResource(R.drawable.baseline_person_24),
                                contentDescription = null)
                        }
                        Spacer(modifier = Modifier.width(12.dp))
                        Text(text = contatoTeste.name, fontFamily = fontFamily)
                    }
                },
                navigationIcon = {
                    IconButton(
                        onClick = backOnClick
                    ) {
                        Icon(
                            painterResource(R.drawable.baseline_arrow_back_24),
                            contentDescription = "Voltar"
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.White)
            )
        },
        bottomBar = {
            Surface(
                modifier = Modifier
                    .fillMaxWidth()
                    .navigationBarsPadding(),
                shadowElevation = 8.dp,
                color = Color.White
            ) {
                Row(
                    modifier = Modifier
                        .padding(16.dp)
                        .fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    OutlinedTextField(
                        value = textoState,
                        onValueChange = { textoState = it },
                        placeholder = { Text("Texto") },
                        modifier = Modifier
                            .weight(1f)
                            .clip(CircleShape),
                        shape = CircleShape,
                        trailingIcon = {
                            IconButton(
                                onClick = {
                                    textoState = ""
                                }
                            ) {
                                Icon(
                                    painterResource(R.drawable.send_icon),
                                    contentDescription = "Enviar",
                                    tint = Color.DarkGray
                                )
                            }
                        }
                    )
                }
            }
        }
    ) { paddingValues ->
        LazyColumn(
            state = listState,
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            contentPadding = PaddingValues(vertical = 16.dp)
        ) {
            items(listaDeMensagens) { mensagem ->
                BalaoChat(mensagem = mensagem, souOPrestador = false)
            }
        }
    }

}

@Composable
fun CardAvaliacao(navController: NavController, prestador: Prestador, modifier: Modifier = Modifier) {
    var rating by remember { mutableIntStateOf(0) }
    var comentario by remember { mutableStateOf("") }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .navigationBarsPadding()
    ) {
//        Image(
//            painter = painterResource(id = R.drawable.map_placeholder),
//            contentDescription = null,
//            modifier = Modifier.fillMaxSize(),
//            contentScale = ContentScale.Crop
//        )

        Column(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth()
                .fillMaxHeight(0.85f) // Ocupa a maior parte da tela
                .clip(RoundedCornerShape(topStart = 32.dp, topEnd = 32.dp))
                .background(Color.White)
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "A solicitação foi finalizada,\navalie o seu serviço!",
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.Bold,
                fontFamily = fontFamily,
                modifier = Modifier.padding(bottom = 24.dp)
            )

            Surface(
                modifier = Modifier.size(80.dp),
                shape = CircleShape,
                border = BorderStroke(1.dp, AppColors.DarkBlue)
            ) {
                AsyncImage(
                    model = "${prestador.perfilImgUrl}",
                    placeholder = painterResource(R.drawable.baseline_person_24),
                    error = painterResource(R.drawable.baseline_person_24),
                    contentDescription = null,
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                repeat(5) { index ->
                    val isSelected = index < rating
                    Icon(
                        painter = painterResource(R.drawable.baseline_star_24),
                        contentDescription = null,
                        modifier = Modifier
                            .size(40.dp)
                            .clickable { rating = index + 1 },
                        tint = if (isSelected) Color(0xFFFFC107) else Color.LightGray
                    )
                }
            }

            Spacer(modifier = Modifier.height(32.dp))

            Text(
                text = "Fazer comentário (Opcional)",
                modifier = Modifier.fillMaxWidth(),
                fontWeight = FontWeight.Bold,
                fontFamily = fontFamily,
                color = Color.DarkGray
            )

            Spacer(modifier = Modifier.height(8.dp))

            OutlinedTextField(
                value = comentario,
                onValueChange = { comentario = it },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(150.dp),
                shape = RoundedCornerShape(16.dp),
                colors = TextFieldDefaults.colors(
                    unfocusedContainerColor = Color.Transparent,
                    focusedContainerColor = Color.Transparent,
                    unfocusedIndicatorColor = Color(0xFFE57373),
                )
            )

            Spacer(modifier = Modifier.weight(1f))

            Button(
                onClick = {},
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFF44336)),
                shape = RoundedCornerShape(28.dp)
            ) {
                Text(text = "Enviar", fontSize = 18.sp, fontWeight = FontWeight.Bold)
            }
        }
    }
}

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
    ){
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
                if(isSOS){
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
                        if(origem != "" && destino != ""){
                            if(isSOS){
                                mostrarCategorias = true
                            }else{
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

            if(isSOS){
                item {
                    AnimatedVisibility(visible = mostrarCategorias) {
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Divider(modifier = Modifier.padding(vertical = 24.dp), color = AppColors.PrimaryRed, thickness = 2.dp)

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
        if(cardState){
            CardConfirmacao(
                pergunta = "Iniciar Busca?",
                onBackClick = {cardState = false},
                onConfirmClick = { navController.navigate(route = "home/cliente/servico_status/1/true")}
            )
        }

    }
}

@Composable
fun CardBuscandoPrestador(cancelarOnClick: () -> Unit, modifier: Modifier = Modifier, isSOS: Boolean) {
    val progresso = remember { Animatable(1f) }
    var resetKey by remember { mutableStateOf(0) }

    LaunchedEffect(resetKey) {
        progresso.snapTo(0f) // Garante que comece do zero imediatamente ao resetar
        progresso.animateTo(
            targetValue = 1f,
            animationSpec = infiniteRepeatable(
                animation = tween(durationMillis = 1 * 60 * 1000, easing = LinearEasing),
                repeatMode = RepeatMode.Restart
            )
        )
    }

    Box(
        modifier = modifier
            .fillMaxSize()
            .navigationBarsPadding()
    ){
        Card(
            modifier = modifier
                .fillMaxWidth()
                .align(Alignment.BottomCenter)
                .clip(RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp)),
            elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White)
        ) {
            Column(
                modifier = Modifier
                    .padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = if(isSOS) "Aguardando Resposta dos Prestadores Próximos" else "Aguardando Resposta do Prestador",
                    fontFamily = fontFamily,
                    fontWeight = FontWeight.Bold,
                    color = AppColors.DarkBlue,
                    fontSize = 18.sp,
                    textAlign = TextAlign.Center
                )
                Spacer(modifier = Modifier.height(25.dp))
                LinearProgressIndicator(
                    progress = {progresso.value},
                    modifier = Modifier
                        .fillMaxWidth(0.8f)
                        .height(13.dp)
                        .align(Alignment.CenterHorizontally)
                        .border(1.dp, AppColors.DarkBlue, RoundedCornerShape(15.dp)),
                    color = Color.Red
                )

                if(isSOS){
                    Spacer(modifier = Modifier.height(40.dp))

                    //Depois tem que fazer essa funcao receber o endereco
                    AddressTimeline(
                        origin = "R. João Blesa - 45. Alphaville",
                        destination = "R. João Blesa - 45. Alphaville"
                    )

                    Spacer(modifier = Modifier.height(50.dp))
                }else{
                    Spacer(modifier = Modifier.height(50.dp))
                }

                Button(
                    onClick = cancelarOnClick,
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentHeight()
                        .padding(horizontal = 40.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = AppColors.SecondaryRed,
                        contentColor = Color.White
                    ),
                    shape = RoundedCornerShape(15.dp)
                ) {
                    Text(
                        text = "Confirmar",
                        color = Color.White,
                        fontFamily = fontFamily,
                        fontWeight = FontWeight.Bold,
                        fontSize = 25.sp
                    )
                }
            }
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ClienteGaragemVirtualScreen(navController: NavController, clienteId: String) {
    val listaVeiculosFake = remember {
        mutableStateListOf(
            Veiculo(1, "Veiculo 1", "ABC-1D23", "12345678910", "25/01/2026", "SEDAN"),
            Veiculo(2, "Veiculo 2", "XYZ-9M87", "98765432100", "15/08/2027", "GUINCHO"),
            Veiculo(3, "Veiculo 3", "ABC-1D23", "12345678910", "25/01/2026", "SEDAN"),
            Veiculo(4, "Veiculo 4", "XYZ-9M87", "98765432100", "15/08/2027", "GUINCHO"),
            Veiculo(5, "Veiculo 5", "ABC-1D23", "12345678910", "25/01/2026", "SEDAN"),
            Veiculo(6, "Veiculo 6", "XYZ-9M87", "98765432100", "15/08/2027", "GUINCHO")
        )
    }

    val listState = rememberLazyListState()
    val coroutineScope = rememberCoroutineScope()

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text("Minha Garagem", color = Color.White, fontWeight = FontWeight.Bold)
                },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            painter = painterResource(R.drawable.baseline_arrow_back_24),
                            contentDescription = "Voltar",
                            tint = Color.White,
                            modifier = Modifier.size(30.dp)
                        )
                    }
                },
                actions = {
                    IconButton(onClick = {
                        val jaTemItemEmEdicao = listaVeiculosFake.any { it.id == null }
                        if (!jaTemItemEmEdicao) {
                            listaVeiculosFake.add(0, Veiculo.criarNovoVazio())
                        }

                        coroutineScope.launch {
                            listState.animateScrollToItem(index = 0)
                        }
                    }) {
                        Icon(
                            painter = painterResource(R.drawable.ic_plus),
                            contentDescription = "Adicionar",
                            tint = Color.White
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = AppColors.PrimaryRed
                )
            )
        },
        bottomBar = {
            BottomPrestadorBar(navController = navController)
        }
    ) { paddingValues ->
        LazyColumn(
            state = listState,
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFFF5F5F5))
                .padding(paddingValues)
                .padding(horizontal = 16.dp),
            contentPadding = PaddingValues(top = 8.dp, bottom = 80.dp)
        ) {
            items(listaVeiculosFake, key = { "${it.id}_${it.placa}" }) { veiculo ->
                CardVeiculo(
                    veiculo = veiculo,
                    onSalvarAlteracoes = { veiculoAtualizado ->
                        val index = if (veiculo.id == null) {
                            listaVeiculosFake.indexOfFirst { it.id == null }
                        } else {
                            listaVeiculosFake.indexOfFirst { it.id == veiculo.id }
                        }

                        if (index != -1) {
                            listaVeiculosFake[index] = veiculoAtualizado
                        }

                        //View Model será chamada aqui
                    },
                    onCancelarEdicao = {
                        if (veiculo.id == null) {
                            listaVeiculosFake.remove(veiculo)
                        }
                    },
                    onRemoverVeiculo = { veiculoParaRemover ->
                        listaVeiculosFake.remove(veiculoParaRemover)

                        //View Model será chamada aqui
                    }
                )
            }
        }
    }
}


//PRESTADOR

@Composable
fun HomePrestadorScreen(navController: NavController) {

    val listaDeClientesTeste = listOf(
        Cliente(
            id = 1,
            nome = "Rogerio Silva",
            telefone = "(11) 98888-7777",
            email = "rogerio.silva@email.com",
            imgPerfil = "https://randomuser.me/api/portraits/men/1.jpg",
            cpf = "123.456.789-00",
            distancia = 0.423
        ),
        Cliente(
            id = 2,
            nome = "Ana Beatriz",
            telefone = "(21) 97777-6666",
            email = "ana.beatriz@email.com",
            imgPerfil = "https://randomuser.me/api/portraits/women/2.jpg",
            cpf = "987.654.321-11",
            distancia = 1.2
        ),
        Cliente(
            id = 3,
            nome = "Oficina do João",
            telefone = "(31) 3444-5555",
            email = "contato@oficinajoao.com",
            imgPerfil = "https://randomuser.me/api/portraits/men/3.jpg",
            cnpj = "12.345.678/0001-99",
            distancia = 2.5
        ),
        Cliente(
            id = 4,
            nome = "Marcos Oliveira",
            telefone = "(41) 96666-5555",
            email = "marcos.o@email.com",
            imgPerfil = "https://randomuser.me/api/portraits/men/4.jpg",
            cpf = "444.555.666-77",
            distancia = 0.850
        ),
        Cliente(
            id = 5,
            nome = "Clínica Saúde Total",
            telefone = "(11) 3222-1111",
            email = "adm@saudetotal.com.br",
            imgPerfil = "https://randomuser.me/api/portraits/women/5.jpg",
            cnpj = "98.765.432/0001-00",
            distancia = 3.1
        ),
        Cliente(
            id = 6,
            nome = "Ricardo Santos",
            telefone = "(71) 95555-4444",
            email = "ricardo.santos@email.com",
            imgPerfil = "https://randomuser.me/api/portraits/men/6.jpg",
            cpf = "222.333.444-55",
            distancia = 0.300
        ),
        Cliente(
            id = 7,
            nome = "Luciana Costa",
            telefone = "(81) 94444-3333",
            email = "lu.costa@email.com",
            imgPerfil = "https://randomuser.me/api/portraits/women/7.jpg",
            cpf = "888.999.000-11",
            distancia = 1.7
        ),
        Cliente(
            id = 8,
            nome = "Padaria Pão Quente",
            telefone = "(19) 3888-9999",
            email = "pedidos@paoquente.com",
            imgPerfil = "https://randomuser.me/api/portraits/women/8.jpg",
            cnpj = "55.444.333/0001-22",
            distancia = 5.4
        ),
        Cliente(
            id = 9,
            nome = "Fernando Souza",
            telefone = "(48) 93333-2222",
            email = "f.souza@email.com",
            imgPerfil = "https://randomuser.me/api/portraits/men/9.jpg",
            cpf = "777.666.555-44",
            distancia = 0.150
        ),
        Cliente(
            id = 10,
            nome = "Beatriz Mendes",
            telefone = "(62) 92222-1111",
            email = "bia.mendes@email.com",
            imgPerfil = "https://randomuser.me/api/portraits/women/10.jpg",
            cpf = "111.000.999-88",
            distancia = 2.9
        )
    )

    var isListVisible by remember { mutableStateOf(false) }

    val progresso = remember { Animatable(1f) }
    var resetKey by remember { mutableStateOf(0) }

    LaunchedEffect(resetKey) {
        progresso.snapTo(0f)
        progresso.animateTo(
            targetValue = 1f,
            animationSpec = infiniteRepeatable(
                animation = tween(durationMillis = 3 * 60 * 1000, easing = LinearEasing),
                repeatMode = RepeatMode.Restart
            )
        )
    }

    Box(modifier = Modifier.fillMaxSize()) {
        Box(modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF0F4F7))) {
            Text("O mapa será implementado aqui", modifier = Modifier.align(Alignment.Center))
        }

        Column(modifier = Modifier.align(Alignment.BottomCenter)) {
            if (!isListVisible) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 8.dp),
                    contentAlignment = Alignment.Center
                ) {
                    FloatingActionButton(
                        onClick = { isListVisible = true },
                        containerColor = Color(0xFFB34B44),
                        contentColor = Color.White,
                        shape = CircleShape,
                        elevation = FloatingActionButtonDefaults.elevation(8.dp)
                    ) {
                        Icon(painterResource(R.drawable.outline_keyboard_double_arrow_up_24), contentDescription = "Subir")
                    }
                }
            }

            if (isListVisible) {
                Surface(
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight(0.6f)
                        .padding(horizontal = 20.dp),
                    shape = RoundedCornerShape(topStart = 32.dp, topEnd = 32.dp),
                    color = Color.White,
                    shadowElevation = 16.dp
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        IconButton(
                            onClick = { isListVisible = false },
                            modifier = Modifier.align(Alignment.CenterHorizontally)
                        ) {
                            Icon(painterResource(R.drawable.outline_keyboard_double_arrow_down_24), contentDescription = "Descer", tint = Color.Gray)
                        }

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text("Mostrando solicitações próximas a você", fontSize = 14.sp, color = Color.Black)
                            IconButton(
                                onClick = {resetKey++}
                            ) {
                                Icon(painterResource(R.drawable.baseline_refresh_24), contentDescription = null, modifier = Modifier.size(30.dp), tint = AppColors.DarkBlue)
                            }
                        }

                        LinearProgressIndicator(
                            progress = {progresso.value},
                            modifier = Modifier
                                .fillMaxWidth(0.8f)
                                .height(4.dp),
                            color = Color.Red
                        )


                        Spacer(modifier = Modifier.height(16.dp))

                        LazyColumn {
                            items(listaDeClientesTeste) {
                                CardCliente(it, navController)
                            }
                        }
                    }
                }
            }

            BottomPrestadorBar(navController = navController)
        }
    }
}

@Composable
fun DetalhesSolicitacaoScreen(navController: NavController, clienteId: String) {
    Scaffold(
        bottomBar = {
            BottomPrestadorBar(navController = navController)
        },
        containerColor = Color.White
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(horizontal = 24.dp)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(modifier = Modifier.fillMaxWidth().padding(top = 16.dp)) {
                IconButton(onClick = { navController.popBackStack() }) {
                    Icon(
                        painter = painterResource(R.drawable.baseline_arrow_back_24),
                        contentDescription = "Voltar",
                        modifier = Modifier.size(32.dp),
                        tint = Color.Black
                    )
                }
            }

            Spacer(modifier = Modifier.height(10.dp))

            Surface(
                modifier = Modifier.size(150.dp),
                shape = CircleShape,
                border = BorderStroke(4.dp, Color(0xFFC0C0C0)),
                color = Color.White
            ) {
                Image(
                    painter = painterResource(id = R.drawable.baseline_person_24),
                    contentDescription = "Foto do Cliente",
                    modifier = Modifier.fillMaxSize()
                )
            }

            Avaliacao(3.0, 20.dp, 3.dp)

            Text(
                text = "Rogerio",
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(vertical = 16.dp),
                color = AppColors.DarkBlue
            )

            Column(modifier = Modifier.fillMaxWidth()) {
                Text(
                    text = "Local de Solicitação",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.SemiBold,
                    modifier = Modifier.padding(bottom = 8.dp),
                    color = AppColors.DarkBlue
                )

                Card(
                    shape = RoundedCornerShape(24.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp),
                    elevation = CardDefaults.cardElevation(4.dp),
                ) {
                    Box(
                        modifier = Modifier.fillMaxSize()
                            .background(AppColors.PlaceholderGray)
                    ) { }
//                    Image(
//                        painter = painterResource(id = R.drawable.mapa_static),
//                        contentDescription = "Mapa Maracanã",
//                        contentScale = ContentScale.Crop,
//                        modifier = Modifier.fillMaxSize()
//                    )
                }
            }

            Spacer(modifier = Modifier.height(30.dp))

            Button(
                onClick = { /* Lógica para aceitar */ },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(80.dp)
                    .padding(bottom = 24.dp, end = 30.dp, start = 30.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFD32F2F)),
                shape = RoundedCornerShape(12.dp)
            ) {
                Text(
                    text = "Aceitar Serviço",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
            }
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ContatosPrestadorScreen(navController: NavController) {

    val listaDeContatosClientes = listOf(
        Contato(
            id = "1",
            name = "Rogerio Silva",
            ultimaMensagem = "Consegue chegar em 15 minutos?",
            perfilImgUrl = "https://i.pravatar.cc/150?u=rog"
        ),
        Contato(
            id = "2",
            name = "Ana Beatriz",
            ultimaMensagem = "Já aceitei o orçamento, pode vir.",
            perfilImgUrl = "https://i.pravatar.cc/150?u=ana"
        ),
        Contato(
            id = "3",
            name = "Marcos Oliveira",
            ultimaMensagem = "Estou parado bem em frente ao posto.",
            perfilImgUrl = "https://i.pravatar.cc/150?u=mar"
        ),
        Contato(
            id = "4",
            name = "Luciana Costa",
            ultimaMensagem = "Você trabalha com cartões de crédito?",
            perfilImgUrl = "https://i.pravatar.cc/150?u=lu"
        ),
        Contato(
            id = "5",
            name = "Ricardo Santos",
            ultimaMensagem = "O pneu reserva também está furado...",
            perfilImgUrl = "https://i.pravatar.cc/150?u=ric"
        ),
        Contato(
            id = "6",
            name = "Beatriz Mendes",
            ultimaMensagem = "Mandei a foto do motor no seu WhatsApp.",
            perfilImgUrl = "https://i.pravatar.cc/150?u=bia"
        ),
        Contato(
            id = "7",
            name = "Fernando Souza",
            ultimaMensagem = "Obrigado pelo suporte hoje cedo!",
            perfilImgUrl = "https://i.pravatar.cc/150?u=fer"
        ),
        Contato(
            id = "8",
            name = "Carlos Eduardo",
            ultimaMensagem = "A bateria descarregou totalmente agora.",
            perfilImgUrl = "https://i.pravatar.cc/150?u=cad"
        ),
        Contato(
            id = "9",
            name = "Juliana Lima",
            ultimaMensagem = "Qual o valor da sua taxa de deslocamento?",
            perfilImgUrl = "https://i.pravatar.cc/150?u=juli"
        ),
        Contato(
            id = "10",
            name = "Roberto Junior",
            ultimaMensagem = "Estou enviando minha localização em tempo real.",
            perfilImgUrl = "https://i.pravatar.cc/150?u=rob"
        )
    )

    Scaffold(
        topBar = {
            AplicationTopBar(navController = navController, titulo = "Contatos", retornavel = false, backgroundColor = AppColors.PrimaryRed,
                textColor = AppColors.TextWhite, iconColor = AppColors.TextWhite)
        },
        bottomBar = {
            BottomPrestadorBar(navController = navController)
        },
        containerColor = Color.White
    ) { paddingValues ->
        Box(
            modifier = Modifier.fillMaxSize()
        ){
            LazyColumn(
                modifier = Modifier
                    .padding(paddingValues)
                    .padding(horizontal = 15.dp, vertical = 20.dp),
                verticalArrangement = Arrangement.spacedBy(20.dp)
            ) {
                items(listaDeContatosClientes){
                    ClienteCardContato(it, navController)
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PrestadorConversaScreen(navController: NavController, contatoId: String)    {

    //Com o id do contato preciso pesquisar as mensagens do prestador e com isso o id dele
    //ira vir e quando solicitar o servico poderá com o id do prestador encontrar a localizacao dele
    //para comparar com quem pediu e dizer a distancia e também exibir a rua que o prestador está
    //e onde o cliente esta

    val contatoTeste = Contato(
        id = "1",
        name = "Rogerio Silva",
        ultimaMensagem = "Consegue chegar em 15 minutos?",
        perfilImgUrl = "https://i.pravatar.cc/150?u=rog"
    )

    //Lista de Mensagens para fazer o design
    val listaDeMensagens = listOf(
        Mensagem(
            id = "1",
            contatoId = "200", // ID do Prestador
            remententeId = "100", // ID do Cliente
            texto = "Olá, meu carro parou na rodovia.",
            horario = "10:00",
            status = StatusMensagem.LIDA,
            remetenteMensagem = RemetenteMensagem.CLIENTE
        ),
        Mensagem(
            id = "2",
            contatoId = "100",
            remententeId = "200",
            texto = "Bom dia! Qual seria o modelo do veículo?",
            horario = "10:02",
            status = StatusMensagem.LIDA,
            remetenteMensagem = RemetenteMensagem.PRESTADOR
        ),
        Mensagem(
            id = "3",
            contatoId = "200",
            remententeId = "100",
            texto = "É um sedan prata. Segue a foto do local.",
            horario = "10:05",
            status = StatusMensagem.LIDA,
            remetenteMensagem = RemetenteMensagem.CLIENTE
        ),
        Mensagem(
            id = "4",
            contatoId = "200",
            remententeId = "100",
            texto = null,
            imgUrl = "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRs17aTBsPEIrihX-smPkNhd9ccDK3O8tj-6Q&s",
            horario = "10:05",
            status = StatusMensagem.ENTREGUE,
            remetenteMensagem = RemetenteMensagem.CLIENTE
        ),
        Mensagem(
            id = "5",
            contatoId = "100",
            remententeId = "200",
            texto = "Recebido. O guincho chega em 10 minutos!",
            horario = "10:07",
            status = StatusMensagem.ENVIADA,
            remetenteMensagem = RemetenteMensagem.PRESTADOR
        )
    )

    var textoState by remember {
        mutableStateOf("")
    }

    val listState = rememberLazyListState()

    LaunchedEffect(listaDeMensagens.size) {
        if (listaDeMensagens.isNotEmpty()) {
            listState.scrollToItem(listaDeMensagens.size - 1)
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                modifier = Modifier.height(90.dp),
                title = {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(15.dp)
                    ) {
                        IconButton(onClick = { navController.popBackStack() }) {
                            Icon(
                                painter = painterResource(R.drawable.baseline_arrow_back_24),
                                contentDescription = "Voltar",
                                tint = Color.White,
                                modifier = Modifier.size(40.dp)
                            )
                        }
                        AsyncImage(
                            model = "${contatoTeste.perfilImgUrl}",
                            placeholder = painterResource(R.drawable.baseline_person_24),
                            error = painterResource(R.drawable.baseline_person_24),
                            contentDescription = null,
                            modifier = Modifier
                                .size(50.dp)
                                .clip(RoundedCornerShape(100))
                                .border(1.dp, Color.White, RoundedCornerShape(100)),
                        )
                        Text(
                            text = contatoTeste.name,
                            fontFamily = fontFamily,
                            fontWeight = FontWeight.SemiBold,
                            color = Color.White,
                            fontSize = 20.sp
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = AppColors.PrimaryRed,
                ),
            )
        },
        bottomBar = {
            BottomAppBar(
                modifier = Modifier
                    .padding(horizontal = 15.dp, vertical = 15.dp)
                    .height(120.dp)
                    .navigationBarsPadding()
                    .imePadding()
                    .clip(RoundedCornerShape(20.dp))
                    .border(1.dp, AppColors.DarkBlue, RoundedCornerShape(20.dp)),
                actions = {
                    Row(
                        modifier = Modifier
                            .padding(horizontal = 8.dp)
                            .fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        IconButton(
                            onClick = {}
                        ) {
                            Image(
                                painter = painterResource(R.drawable.gallery_icon),
                                contentDescription = "Anexar"
                            )
                        }

                        TextField(
                            value = textoState,
                            onValueChange = { textoState = it },
                            modifier = Modifier
                                .weight(1f)
                                .padding(horizontal = 4.dp),
                            placeholder = { Text("Mensagem...") },
                            maxLines = 5,
                            colors = TextFieldDefaults.colors(
                                focusedContainerColor = Color.Transparent,
                                unfocusedContainerColor = Color.Transparent,
                                focusedIndicatorColor = Color.Transparent,
                                unfocusedIndicatorColor = Color.Transparent
                            )
                        )

                        IconButton(
                            onClick = {

                            },
                            enabled = textoState.isNotBlank()
                        ) {
                            Image(
                                painter = painterResource(R.drawable.send_icon),
                                contentDescription = "Enviar",
                            )
                        }
                    }
                },
                containerColor = AppColors.CardBackground
            )
        },
        containerColor = AppColors.BackgroundConversaYellow
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
        ){
            LazyColumn(
                contentPadding = PaddingValues(vertical = 20.dp),
                state = listState
            ) {
                items(listaDeMensagens){ item ->
                    BalaoChat(item, true)
                }
            }
        }
    }

}

@Composable
fun PrestadorPerfilScreen(navController: NavController) {

    var imagemSelecionada by rememberSaveable { mutableStateOf<Uri?>(null) }

    var descricaoState by remember { mutableStateOf("") }

    var tipoVeiculoSelecionado by remember { mutableStateOf<TipoVeiculo?>(TipoVeiculo.CARRO) }

    var servico1 by remember { mutableStateOf<CategoriaServico?>(CategoriaServico.BORRACHARIA) }
    var servico2 by remember { mutableStateOf<CategoriaServico?>(null) } // Opcional

    val photoPickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia(),
        onResult = { uri: Uri? -> imagemSelecionada = uri }
    )

    //Quando ir fazer o viewModel fazer a parte de salvar o usuario logado globalmente e salva-lo para chegar nessa tela aqui
    Scaffold(
        topBar = {
            AplicationTopBar(navController = navController, titulo = "Meu Perfil", retornavel = true)
        },
        containerColor = Color.White
    ) { paddingValues ->
        Card(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
                .padding(bottom = 50.dp, start = 20.dp, end = 20.dp)
                .verticalScroll(rememberScrollState())
                .border(1.dp, AppColors.BorderGray, RoundedCornerShape(15.dp)),
            shape = RoundedCornerShape(15.dp),
            colors = CardDefaults.cardColors(
                containerColor = AppColors.CardBackground,
            ),
            elevation = CardDefaults.cardElevation(
                defaultElevation = 10.dp
            )
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 20.dp, horizontal = 15.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(30.dp)
            ) {
                Box(
                    modifier = Modifier
                        .size(190.dp)
                ) {
                    //Depois pensar na logica para pesquisar o id do usuario Logado e retornar as informações aqui

                    if(imagemSelecionada != null){
                        AsyncImage(
                            model = imagemSelecionada,
                            contentDescription = "Foto de Perfil",
                            modifier = Modifier
                                .fillMaxSize()
                                .clip(CircleShape)
                                .clickable {
                                    photoPickerLauncher.launch(
                                        PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
                                    )
                                },
                            contentScale = ContentScale.Crop
                        )
                    }else{
                        Icon(
                            painter = painterResource(R.drawable.baseline_person_24),
                            contentDescription = null,
                            tint = AppColors.DarkBlue,
                            modifier = Modifier
                                .fillMaxSize()
                                .border(1.dp, AppColors.SecondaryRed, RoundedCornerShape(100))
                                .clickable {
                                    photoPickerLauncher.launch(
                                        PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
                                    )
                                },
                        )
                    }

                    IconButton(
                        onClick = {
                            photoPickerLauncher.launch(
                                PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
                            )
                        },
                        modifier = Modifier
                            .align(Alignment.BottomEnd)
                    ) {
                        Icon(
                            painter = painterResource(R.drawable.baseline_camera_alt_24),
                            contentDescription = "Alterar Foto",
                            tint = AppColors.DarkBlue,
                            modifier = Modifier
                                .size(30.dp)
                                .offset(x = 5.dp, y = 5.dp)
                        )
                    }
                }
                Avaliacao(4.0, 30.dp, 3.dp)
                Text(
                    text = "Nome do Usuario",
                    fontFamily = fontFamily,
                    fontWeight = FontWeight.SemiBold,
                    color = Color.Black,
                    fontSize = 25.sp
                )
                OutlinedTextField(
                    value = descricaoState,
                    onValueChange = {descricaoState = it},
                    placeholder = {
                        Text(
                            text = "Descrição do Serviço",
                            fontFamily = fontFamily,
                            fontWeight = FontWeight.Bold,
                            fontSize = 15.sp,
                        )
                    },
                    modifier = Modifier.fillMaxWidth()
                        .height(150.dp),
                    singleLine = false,
                    shape = RoundedCornerShape(12.dp),
                    colors = TextFieldDefaults.colors(
                        unfocusedContainerColor = AppColors.BackgroundGray,
                        focusedContainerColor = AppColors.BackgroundGray
                    )
                )
                Card(
                    modifier = Modifier
                        .border(1.dp, AppColors.BorderGray, RoundedCornerShape(15.dp)),
                    shape = RoundedCornerShape(15.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = AppColors.CardBackground,
                    ),
                    elevation = CardDefaults.cardElevation(
                        defaultElevation = 10.dp
                    )
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .fillMaxHeight()
                            .padding(vertical = 20.dp, horizontal = 20.dp),
                        verticalArrangement = Arrangement.spacedBy(20.dp)
                    ) {
                        Text(
                            text = "Dados Pessoais",
                            fontFamily = fontFamily,
                            color = Color.Black,
                            fontWeight = FontWeight.Bold,
                            fontSize = 20.sp
                        )

                        CampoDigitar(campoNome = "Nome do Usuario", alteravel = true,
                            painter = painterResource(R.drawable.baseline_person_24),
                            painterTransform = painterResource(R.drawable.baseline_person_24),
                            iconFim = false)


                        CampoDigitar( campoNome = "CPF/CNPJ", alteravel = false)

                        //Depois alterar pelo icone do email
                        CampoDigitar(campoNome = "Email", alteravel = true,
                            painter = painterResource(R.drawable.baseline_person_24),
                            painterTransform = painterResource(R.drawable.baseline_person_24),
                            iconFim = false)

                        //Depois alterar pelo icone do telefone
                        CampoDigitar(campoNome = "Telefone", alteravel = true,
                            painter = painterResource(R.drawable.baseline_person_24),
                            painterTransform = painterResource(R.drawable.baseline_person_24),
                            iconFim = false)

                        Button(
                            onClick = {},
                            modifier = Modifier
                                .height(50.dp)
                                .width(140.dp)
                                .clip(RoundedCornerShape(15.dp))
                                .border(1.dp, Color.Black, RoundedCornerShape(15.dp)),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = Color.Transparent,
                                contentColor = AppColors.SecondaryRed
                            )
                        ) {
                            Text(
                                text = "Alterar Senha",
                                fontFamily = fontFamily,
                                fontWeight = FontWeight.SemiBold,
                                fontSize = 12.sp
                            )
                        }
                    }
                    Text(
                        text = "Excluir Conta",
                        style = TextStyle(
                            textDecoration = TextDecoration.Underline,
                            fontSize = 10.sp
                        ),
                        fontFamily = fontFamily,
                        fontWeight = FontWeight.SemiBold,
                        color = AppColors.PlaceholderGray,
                        modifier = Modifier
                            .align(Alignment.End)
                            .padding(end = 40.dp, bottom = 40.dp)
                    )
                }
//                Card(
//                    modifier = Modifier
//                        .border(1.dp, AppColors.BorderGray, RoundedCornerShape(15.dp)),
//                    shape = RoundedCornerShape(15.dp),
//                    colors = CardDefaults.cardColors(
//                        containerColor = AppColors.CardBackground,
//                    ),
//                    elevation = CardDefaults.cardElevation(
//                        defaultElevation = 10.dp
//                    )
//                ){
//                    Column(
//                        modifier = Modifier
//                            .fillMaxWidth()
//                            .fillMaxHeight()
//                            .padding(vertical = 20.dp, horizontal = 20.dp),
//                        verticalArrangement = Arrangement.spacedBy(20.dp)
//                    ) {
//                        Text(
//                            text = "Dados do Veículo",
//                            fontFamily = fontFamily,
//                            color = Color.Black,
//                            fontWeight = FontWeight.Bold,
//                            fontSize = 20.sp
//                        )
//
//                        CampoDigitar(campoNome = "Data de Validade", alteravel = true)
//
//                        MenuSelecao(
//                            campoNome = "Categoria do Veículo",
//                            itens = TipoVeiculo.values().toList(),
//                            itemPreSelecionado = tipoVeiculoSelecionado,
//                            labelProvider = { it.descricao },
//                            onItemSelecionado = { tipoVeiculoSelecionado = it },
//                            modifier = Modifier.fillMaxWidth()
//                        )
//
//                        CampoDigitar(campoNome = "Código Renavam", alteravel = true)
//
//                        CampoDigitar(campoNome = "Placa do Veículo", alteravel = true)
//                    }
//                }
                Card(
                    modifier = Modifier
                        .border(1.dp, AppColors.BorderGray, RoundedCornerShape(15.dp)),
                    shape = RoundedCornerShape(15.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = AppColors.CardBackground,
                    ),
                    elevation = CardDefaults.cardElevation(
                        defaultElevation = 10.dp
                    )
                ){
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .fillMaxHeight()
                            .padding(vertical = 20.dp, horizontal = 20.dp),
                        verticalArrangement = Arrangement.spacedBy(20.dp)
                    ) {
                        Text(
                            text = "Categorias de Serviço",
                            fontFamily = fontFamily,
                            color = Color.Black,
                            fontWeight = FontWeight.Bold,
                            fontSize = 20.sp
                        )

                        MenuSelecao(
                            campoNome = "Categoria 1",
                            itens = CategoriaServico.values().toList(),
                            itemPreSelecionado = servico1,
                            labelProvider = { it.nome },
                            onItemSelecionado = { servico1 = it },
                            modifier = Modifier.fillMaxWidth()
                        )

                        MenuSelecao(
                            campoNome = "Categoria 2 (Opcional)",
                            itens = CategoriaServico.values().filter { it != servico1 },
                            itemPreSelecionado = servico2,
                            labelProvider = { it.nome },
                            onItemSelecionado = { servico2 = it },
                            modifier = Modifier.fillMaxWidth()
                        )
                    }


                }
//                if(dadosAlterados){
//                    Text(
//                        text = "Salvar Alterações?",
//                        fontFamily = fontFamily,
//                        fontWeight = FontWeight.SemiBold,
//                        color = Color.Black,
//                        fontSize = 25.sp
//                    )
//                    Row(
//                        modifier = Modifier.fillMaxWidth(),
//                        verticalAlignment = Alignment.CenterVertically,
//                        horizontalArrangement = Arrangement.Center
//                    ) {
//                        Button(
//                            onClick = {},
//                            colors = ButtonDefaults.buttonColors(
//                                containerColor = AppColors.SecondaryRed,
//                                contentColor = Color.White
//                            ),
//                            modifier = Modifier
//                                .height(50.dp),
//                            shape = RoundedCornerShape(20.dp)
//                        ) {
//                            Text(
//                                text = "Cancelar",
//                                fontFamily = fontFamily,
//                                fontWeight = FontWeight.SemiBold,
//                                color = Color.White,
//                                fontSize = 16.sp
//                            )
//                        }
//                        Spacer(modifier = Modifier.width(30.dp))
//                        Button(
//                            onClick = {},
//                            colors = ButtonDefaults.buttonColors(
//                                containerColor = AppColors.ConfirmGreen,
//                                contentColor = Color.White
//                            ),
//                            modifier = Modifier
//                                .height(50.dp),
//                            shape = RoundedCornerShape(20.dp)
//                        ) {
//                            Text(
//                                text = "Confirmar",
//                                fontFamily = fontFamily,
//                                fontWeight = FontWeight.SemiBold,
//                                color = Color.White,
//                                fontSize = 16.sp
//                            )
//                        }
//                    }
//                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetalhesSolicitacaoEmergenciaScreen(onCorridaAceita: () -> Unit, clienteId: String, navController: NavController) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Box() {
                        IconButton(
                            onClick = {navController.popBackStack()},
                            modifier = Modifier.align(Alignment.TopStart)
                        ) {
                            Icon(
                                painter = painterResource(R.drawable.baseline_arrow_back_24),
                                contentDescription = "Voltar",
                                tint = AppColors.DarkBlue,
                                modifier = Modifier.size(50.dp)
                            )
                        }

                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.Center,
                            modifier = Modifier.fillMaxWidth()
                                .align(Alignment.Center)
                        ) {
                            Text(
                                text = "🚨",
                                fontSize = 22.sp
                            )

                            Spacer(modifier = Modifier.width(8.dp))

                            Text(
                                text = "Emergência",
                                color = AppColors.PrimaryRed,
                                fontWeight = FontWeight.Bold,
                                fontSize = 24.sp,
                                fontFamily = fontFamily
                            )
                        }
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.White
                )
            )
        },
        bottomBar = {
            BottomPrestadorBar(navController = navController)
        },
        containerColor = Color.White
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(horizontal = 25.dp)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Spacer(modifier = Modifier.height(16.dp))

            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Box(
                    modifier = Modifier
                        .size(150.dp)
                        .border(2.dp, Color(0xFF6D6D6D), CircleShape)
                        .padding(4.dp)
                ) {
                    Image(
                        painter = painterResource(R.drawable.baseline_person_24),
                        contentDescription = "Foto do Cliente",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .fillMaxSize()
                            .clip(CircleShape)
                    )
                }

                Spacer(modifier = Modifier.height(12.dp))

                Avaliacao(3.0, 25.dp, 3.dp)

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = "Rogerio",
                    fontSize = 28.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black
                )
            }

//            Spacer(modifier = Modifier.height(32.dp))

            Column() {
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.Start
                ) {
                    Text(
                        text = "Origem:",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = AppColors.DarkBlue
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = "Rodovia dos Bandeirantes, KM 152",
                        fontSize = 15.sp,
                        color = Color.Black
                    )
                }

//            Spacer(modifier = Modifier.height(24.dp))

                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.Start
                ) {
                    Text(
                        text = "Destino:",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = AppColors.DarkBlue
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = "Centro Automotivo Porto, Rua das Flores, 500.",
                        fontSize = 15.sp,
                        color = Color.Black,
                        lineHeight = 20.sp
                    )
                }

//            Spacer(modifier = Modifier.height(24.dp))

                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.Start
                ) {
                    Text(
                        text = "Descrição:",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = AppColors.DarkBlue
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = "Range Rover Evoque, Perda Total",
                        fontSize = 15.sp,
                        color = Color.Black,
                        lineHeight = 20.sp
                    )
                }
            }

//            Spacer(modifier = Modifier.height(40.dp))

            BotaoAceitarArrastavel(
                modifier = Modifier.padding(bottom = 32.dp),
                onAccept = {
                    onCorridaAceita()
                }
            )
        }
    }
}

//@OptIn(ExperimentalMaterial3Api::class)
//@Composable
//fun ClienteGaragemVirtualScreen(navController: NavController) {
//    val listaVeiculosFake = remember {
//        mutableStateListOf(
//            Veiculo(1, "Veiculo 1", "ABC-1D23", "12345678910", "25/01/2026", "SEDAN"),
//            Veiculo(2, "Veiculo 2", "XYZ-9M87", "98765432100", "15/08/2027", "GUINCHO"),
//            Veiculo(3, "Veiculo 1", "ABC-1D23", "12345678910", "25/01/2026", "SEDAN"),
//            Veiculo(4, "Veiculo 2", "XYZ-9M87", "98765432100", "15/08/2027", "GUINCHO"),
//            Veiculo(5, "Veiculo 1", "ABC-1D23", "12345678910", "25/01/2026", "SEDAN"),
//            Veiculo(6, "Veiculo 2", "XYZ-9M87", "98765432100", "15/08/2027", "GUINCHO")
//        )
//    }
//
//    Scaffold(
//        topBar = {
//            TopAppBar(
//                title = {
//                    Text("Minha Garagem", color = Color.White, fontWeight = FontWeight.Bold)
//                },
//                navigationIcon = {
//                    IconButton(onClick = { /* Navegar para trás */ }) {
//                        Icon(painterResource(R.drawable.baseline_arrow_back_24), contentDescription = "Voltar", tint = Color.White)
//                    }
//                },
//                actions = {
//                    IconButton(onClick = { /* Adicionar novo veículo */ }) {
//                        Icon(painterResource(R.drawable.ic_plus), contentDescription = "Adicionar", tint = Color.White)
//                    }
//                },
//                colors = TopAppBarDefaults.topAppBarColors(
//                    containerColor = Color(0xFFB73A3A) // Mesma cor vermelha do topo
//                )
//            )
//        }
//    ) { paddingValues ->
//        LazyColumn(
//            modifier = Modifier
//                .fillMaxSize()
//                .background(Color(0xFFF5F5F5))
//                .padding(paddingValues)
//                .padding(horizontal = 16.dp),
//            contentPadding = PaddingValues(bottom = 80.dp)
//        ) {
//            items(listaVeiculosFake, key = { it.id }) { veiculo ->
//                CardVeiculo(
//                    veiculo = veiculo,
//                    onSalvarAlteracoes = { veiculoAtualizado ->
//                        val index = listaVeiculosFake.indexOfFirst { it.id == veiculoAtualizado.id }
//                        if (index != -1) {
//                            listaVeiculosFake[index] = veiculoAtualizado
//                        }
//                    }
//                )
//            }
//        }
//    }
//}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PrestadorGaragemVirtualScreen(navController: NavController, prestadorId: String) {
    val listaVeiculosFake = remember {
        mutableStateListOf(
            Veiculo(1, "Veiculo 1", "ABC-1D23", "12345678910", "25/01/2026", "SEDAN"),
            Veiculo(2, "Veiculo 2", "XYZ-9M87", "98765432100", "15/08/2027", "GUINCHO"),
            Veiculo(3, "Veiculo 3", "ABC-1D23", "12345678910", "25/01/2026", "SEDAN"),
            Veiculo(4, "Veiculo 4", "XYZ-9M87", "98765432100", "15/08/2027", "GUINCHO"),
            Veiculo(5, "Veiculo 5", "ABC-1D23", "12345678910", "25/01/2026", "SEDAN"),
            Veiculo(6, "Veiculo 6", "XYZ-9M87", "98765432100", "15/08/2027", "GUINCHO")
        )
    }

    val listState = rememberLazyListState()
    val coroutineScope = rememberCoroutineScope()

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text("Minha Garagem", color = Color.White, fontWeight = FontWeight.Bold)
                },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            painter = painterResource(R.drawable.baseline_arrow_back_24),
                            contentDescription = "Voltar",
                            tint = Color.White,
                            modifier = Modifier.size(30.dp)
                        )
                    }
                },
                actions = {
                    IconButton(onClick = {
                        val jaTemItemEmEdicao = listaVeiculosFake.any { it.id == null }
                        if (!jaTemItemEmEdicao) {
                            listaVeiculosFake.add(0, Veiculo.criarNovoVazio())
                        }

                        coroutineScope.launch {
                            listState.animateScrollToItem(index = 0)
                        }
                    }) {
                        Icon(
                            painter = painterResource(R.drawable.ic_plus),
                            contentDescription = "Adicionar",
                            tint = Color.White
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = AppColors.PrimaryRed
                )
            )
        },
        bottomBar = {
            BottomPrestadorBar(navController = navController)
        }
    ) { paddingValues ->
        LazyColumn(
            state = listState,
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFFF5F5F5))
                .padding(paddingValues)
                .padding(horizontal = 16.dp),
            contentPadding = PaddingValues(top = 8.dp, bottom = 80.dp)
        ) {
            items(listaVeiculosFake, key = { "${it.id}_${it.placa}" }) { veiculo ->
                CardVeiculo(
                    veiculo = veiculo,
                    onSalvarAlteracoes = { veiculoAtualizado ->
                        val index = if (veiculo.id == null) {
                            listaVeiculosFake.indexOfFirst { it.id == null }
                        } else {
                            listaVeiculosFake.indexOfFirst { it.id == veiculo.id }
                        }

                        if (index != -1) {
                            listaVeiculosFake[index] = veiculoAtualizado
                        }

                        //View Model será chamada aqui
                    },
                    onCancelarEdicao = {
                        if (veiculo.id == null) {
                            listaVeiculosFake.remove(veiculo)
                        }
                    },
                    onRemoverVeiculo = { veiculoParaRemover ->
                        listaVeiculosFake.remove(veiculoParaRemover)

                        //View Model será chamada aqui
                    }
                )
            }
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun PrestadorRegistroDePedidosScreen(navController: NavController) {

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
            AplicationTopBar(navController = navController, titulo = "Registro de Pedidos", retornavel = false)
        },
        bottomBar = {
            BottomPrestadorBar(navController = navController)
        },
        containerColor = Color.White
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(15.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            items(listaDePedidos){ pedido ->
                PrestadorCardHistoricoPedido(pedido)
            }
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun PrestadorCardHistoricoPedido(pedido: Pedido, modifier: Modifier = Modifier) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 15.dp, horizontal = 15.dp)
            .clip(RoundedCornerShape(15.dp))
            .border(1.dp, Color.Black, RoundedCornerShape(15.dp)),
        colors = CardDefaults.cardColors(
            containerColor = Color(0xFFF5F5F5),
            contentColor = Color.Black
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 6.dp,
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 30.dp)
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
                    fontWeight = FontWeight.ExtraBold,
                )
            }
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 10.dp, horizontal = 20.dp)
                    .border(1.dp, Color.Black, RoundedCornerShape(15.dp)),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 15.dp, end = 15.dp, top = 20.dp),
                    horizontalArrangement = Arrangement.spacedBy(15.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        painter = painterResource(R.drawable.outline_arrow_downward_24),
                        contentDescription = null,
                        modifier = Modifier
                            .width(30.dp)
                            .height(40.dp),
//                    .align(Alignment.CenterHorizontally)
                        tint = Color.Transparent
                    )
                    Text(
                        text = "Origem",
                        fontFamily = fontFamily,
                        fontSize = 30.sp,
                        fontWeight = FontWeight.Bold,
                    )
                }
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 15.dp),
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
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 15.dp),
                    horizontalArrangement = Arrangement.spacedBy(15.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        painter = painterResource(R.drawable.outline_arrow_downward_24),
                        contentDescription = null,
                        modifier = Modifier
                            .width(30.dp)
                            .height(40.dp)
//                    .align(Alignment.CenterHorizontally)
                    )
                    Text(
                        text = "Destino",
                        fontFamily = fontFamily,
                        fontSize = 30.sp,
                        fontWeight = FontWeight.Bold,
                    )
                }
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 15.dp, end = 15.dp, bottom = 25.dp),
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
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 15.dp, vertical = 15.dp),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    painter = painterResource(R.drawable.baseline_person_24),
                    contentDescription = "Cliente",
                    modifier = Modifier
                        .size(40.dp)
                        .border(1.dp, Color.Black, RoundedCornerShape(100))
                )
                Spacer(modifier = Modifier.width(15.dp))
                Text(
                    text = "Cliente: Carlos Oliveira",
                    fontFamily = fontFamily,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                )
            }
        }

    }
}