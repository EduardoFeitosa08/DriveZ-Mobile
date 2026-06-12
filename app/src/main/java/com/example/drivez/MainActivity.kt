package com.example.drivez

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.googlefonts.Font
import androidx.compose.ui.text.googlefonts.GoogleFont
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.drivez.core.network.RetrofitClient
import com.example.drivez.core.network.theme.DriveZTheme
import com.example.drivez.ui.cadastro.CadastroScreen
import com.example.drivez.ui.cadastro.CadastroViewModel
import com.example.drivez.ui.configurar_pedido.ConfigurarPedidoScreen
import com.example.drivez.ui.contatos_cliente.ContatosClienteScreen
import com.example.drivez.ui.contatos_prestador.ContatosPrestadorScreen
import com.example.drivez.ui.conversa_cliente.ClienteConversaScreen
import com.example.drivez.ui.conversa_prestador.PrestadorConversaScreen
import com.example.drivez.ui.detalhes_solicitacao.DetalhesSolicitacaoEmergenciaScreen
import com.example.drivez.ui.detalhes_solicitacao.DetalhesSolicitacaoScreen
import com.example.drivez.ui.detalhes_solicitacao.DetalhesSolicitacaoViewModel
import com.example.drivez.ui.garagem_cliente.ClienteGaragemVirtualScreen
import com.example.drivez.ui.garagem_prestador.PrestadorGaragemVirtualScreen
import com.example.drivez.ui.home_cliente.HomeClienteScreen
import com.example.drivez.ui.home_prestador.HomePrestadorScreen
import com.example.drivez.ui.home_prestador.HomePrestadorViewModel
import com.example.drivez.ui.login.LoginScreen
import com.example.drivez.ui.perfil_cliente.ClientePerfilScreen
import com.example.drivez.ui.perfil_prestador.PrestadorPerfilScreen
import com.example.drivez.ui.registro_pedidos_cliente.ClienteRegistroDePedidosScreen
import com.example.drivez.ui.registro_pedidos_cliente.ClienteRegistroDePedidosViewModel
import com.example.drivez.ui.registro_pedidos_prestador.PrestadorRegistroDePedidosScreen
import com.example.drivez.ui.registro_pedidos_prestador.PrestadorRegistroDePedidosViewModel
import com.example.drivez.ui.service_status.ServiceStatusScreen
import com.example.drivez.ui.servico.ServicoScreen


import androidx.compose.ui.platform.LocalContext
import com.example.drivez.core.session.SessionManager

class MainActivity : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val context = LocalContext.current
            val sessionManager = remember { SessionManager(context) }

            DriveZTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    val navController = rememberNavController()
                    Box(modifier = Modifier.padding(innerPadding)) {
                        NavHost(
                            navController = navController,
                            startDestination = "login"
                        ) {
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

                            composable("completar_cadastro_cliente/{userId}") {
                                // userId is unused
                            }

                            composable("completar_cadastro_prestador/{userId}") {
                                // userId is unused
                            }
                            composable("home/cliente") {
                                HomeClienteScreen(navController = navController)
                            }
                            composable(
                                route = "home/cliente/servico/{prestadorId}",
                                arguments = listOf(navArgument("prestadorId") { type = NavType.StringType })
                            ) {
                                val prestadorId = it.arguments?.getString("prestadorId")
                                ServicoScreen(navController = navController, prestadorId = prestadorId!!)
                            }
                            composable("home/cliente/historico") {
                                val apiService = RetrofitClient.historicoPedidoApiService
                                val factory = object : ViewModelProvider.Factory {
                                    override fun <T : ViewModel> create(modelClass: Class<T>): T {
                                        @Suppress("UNCHECKED_CAST")
                                        return ClienteRegistroDePedidosViewModel(apiService, sessionManager) as T
                                    }
                                }
                                val viewModel: ClienteRegistroDePedidosViewModel = viewModel(factory = factory)
                                ClienteRegistroDePedidosScreen(navController = navController, viewModel = viewModel)
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
                                    navArgument("isSOS") { type = NavType.BoolType; defaultValue = false }
                                )
                            ) {
                                val prestadorId = it.arguments?.getString("prestadorId")
                                val isSOS = it.arguments?.getBoolean("isSOS")
                                ServiceStatusScreen(navController = navController, userId = prestadorId!!, isSOS = isSOS!!)
                            }
                            composable(
                                route = "home/cliente/pedido/{isSOS}",
                                arguments = listOf(
                                    navArgument("isSOS") { type = NavType.BoolType; defaultValue = false }
                                )
                            ) {
                                val isSOS = it.arguments?.getBoolean("isSOS")
                                ConfigurarPedidoScreen(navController = navController, isSOS = isSOS!!)
                            }

                            composable(
                                "home/cliente/perfil/garagem/{clienteId}",
                                arguments = listOf(navArgument("clienteId") { type = NavType.StringType })
                            ) {
                                val clienteId = it.arguments?.getString("clienteId")
                                ClienteGaragemVirtualScreen(navController = navController, clienteId = clienteId!!)
                            }

                            //Prestador

                            composable("home/prestador") {
                                val apiService = RetrofitClient.homePrestadorApiService

                                val factory = object : ViewModelProvider.Factory {
                                    override fun <T : ViewModel> create(modelClass: Class<T>): T {
                                        @Suppress("UNCHECKED_CAST")
                                        return HomePrestadorViewModel(apiService, sessionManager) as T
                                    }
                                }

                                val viewModel: HomePrestadorViewModel = viewModel(factory = factory)

                                HomePrestadorScreen(navController = navController, viewModel = viewModel)
                            }

                            composable(
                                route = "home/prestador/detalhes_solicitacao/{clienteId}",
                                arguments = listOf(navArgument("clienteId") { type = NavType.StringType })
                            ) {
                                val clienteId = it.arguments?.getString("clienteId")
                                val apiService = RetrofitClient.homePrestadorApiService
                                val factory = object : ViewModelProvider.Factory {
                                    override fun <T : ViewModel> create(modelClass: Class<T>): T {
                                        @Suppress("UNCHECKED_CAST")
                                        return DetalhesSolicitacaoViewModel(apiService) as T
                                    }
                                }
                                val viewModel: DetalhesSolicitacaoViewModel = viewModel(factory = factory)
                                DetalhesSolicitacaoScreen(navController = navController, clienteId = clienteId!!, viewModel = viewModel)
                            }
                            composable(
                                route = "home/prestador/detalhes_solicitacao/emergencia/{clienteId}",
                                arguments = listOf(navArgument("clienteId") { type = NavType.StringType })
                            ) {
                                val clienteId = it.arguments?.getString("clienteId")
                                val apiService = RetrofitClient.homePrestadorApiService
                                val factory = object : ViewModelProvider.Factory {
                                    override fun <T : ViewModel> create(modelClass: Class<T>): T {
                                        @Suppress("UNCHECKED_CAST")
                                        return DetalhesSolicitacaoViewModel(apiService) as T
                                    }
                                }
                                val viewModel: DetalhesSolicitacaoViewModel = viewModel(factory = factory)
                                DetalhesSolicitacaoEmergenciaScreen(navController = navController, clienteId = clienteId!!,
                                    onCorridaAceita = {}, viewModel = viewModel)
                            }

                            composable(
                                route = "home/prestador/servico_status/{clienteId}/",
                                arguments = listOf(navArgument("clienteId") { type = NavType.StringType })
                            ) {
                                val clienteId = it.arguments?.getString("clienteId")
                                val apiService = RetrofitClient.homePrestadorApiService
                                val factory = object : ViewModelProvider.Factory {
                                    override fun <T : ViewModel> create(modelClass: Class<T>): T {
                                        @Suppress("UNCHECKED_CAST")
                                        return DetalhesSolicitacaoViewModel(apiService) as T
                                    }
                                }
                                val viewModel: DetalhesSolicitacaoViewModel = viewModel(factory = factory)
                                DetalhesSolicitacaoScreen(navController = navController, clienteId = clienteId!!, viewModel = viewModel)
                            }

                            composable(
                                route = "home/prestador/servico_status/{clienteId}/{isSOS}",
                                arguments = listOf(
                                    navArgument("clienteId") { type = NavType.StringType },
                                    navArgument("isSOS") { type = NavType.BoolType; defaultValue = false }
                                )
                            ) {
                                val clienteId = it.arguments?.getString("clienteId")
                                val isSOS = it.arguments?.getBoolean("isSOS")
                                ServiceStatusScreen(navController = navController, userId = clienteId!!, isSOS = isSOS!!)
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

                            composable("home/prestador/historico") {
                                val apiService = RetrofitClient.homePrestadorApiService
                                val pedidoApiService = RetrofitClient.historicoPedidoApiService
                                val factory = object : ViewModelProvider.Factory {
                                    override fun <T : ViewModel> create(modelClass: Class<T>): T {
                                        @Suppress("UNCHECKED_CAST")
                                        return PrestadorRegistroDePedidosViewModel(apiService, pedidoApiService, sessionManager) as T
                                    }
                                }
                                val viewModel: PrestadorRegistroDePedidosViewModel = viewModel(factory = factory)
                                PrestadorRegistroDePedidosScreen(navController = navController, viewModel = viewModel)
                            }
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