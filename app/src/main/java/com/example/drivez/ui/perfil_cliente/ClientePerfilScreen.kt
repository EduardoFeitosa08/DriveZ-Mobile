package com.example.drivez.ui.perfil_cliente

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil3.compose.AsyncImage
import com.example.drivez.R
import com.example.drivez.core.network.theme.AppColors
import com.example.drivez.core.network.theme.fontFamily
import com.example.drivez.ui.components.AplicationTopBar
import com.example.drivez.ui.components.Avaliacao
import com.example.drivez.ui.components.CampoDigitar

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

                    if (imagemSelecionada != null) {
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
                    } else {
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

                        CampoDigitar(
                            campoNome = "Nome do Usuario", alteravel = true,
                            painter = painterResource(R.drawable.baseline_person_24),
                            painterTransform = painterResource(R.drawable.baseline_person_24),
                            iconFim = false
                        )


                        CampoDigitar(campoNome = "CPF/CNPJ", alteravel = false)

                        //Depois alterar pelo icone do email
                        CampoDigitar(
                            campoNome = "Email", alteravel = true,
                            painter = painterResource(R.drawable.baseline_person_24),
                            painterTransform = painterResource(R.drawable.baseline_person_24),
                            iconFim = false
                        )

                        //Depois alterar pelo icone do telefone
                        CampoDigitar(
                            campoNome = "Telefone", alteravel = true,
                            painter = painterResource(R.drawable.baseline_person_24),
                            painterTransform = painterResource(R.drawable.baseline_person_24),
                            iconFim = false
                        )

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
