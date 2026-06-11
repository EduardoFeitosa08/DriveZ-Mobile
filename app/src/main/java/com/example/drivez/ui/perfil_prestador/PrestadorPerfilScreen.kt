package com.example.drivez.ui.perfil_prestador

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
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
import com.example.drivez.data.model.CategoriaServico
import com.example.drivez.ui.components.AplicationTopBar
import com.example.drivez.ui.components.Avaliacao
import com.example.drivez.ui.components.CampoDigitar
import com.example.drivez.ui.components.MenuSelecao

@Composable
fun PrestadorPerfilScreen(navController: NavController) {

    var imagemSelecionada by rememberSaveable { mutableStateOf<Uri?>(null) }

    var descricaoState by remember { mutableStateOf("") }

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
                OutlinedTextField(
                    value = descricaoState,
                    onValueChange = { descricaoState = it },
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
                            text = "Categorias de Serviço",
                            fontFamily = fontFamily,
                            color = Color.Black,
                            fontWeight = FontWeight.Bold,
                            fontSize = 20.sp
                        )

                        MenuSelecao(
                            campoNome = "Categoria 1",
                            itens = CategoriaServico.entries.toList(),
                            itemPreSelecionado = servico1,
                            labelProvider = { categoria -> categoria.nome },
                            onItemSelecionado = { selecionado -> servico1 = selecionado },
                            modifier = Modifier.fillMaxWidth()
                        )

                        MenuSelecao(
                            campoNome = "Categoria 2 (Opcional)",
                            itens = CategoriaServico.entries.filter { it != servico1 },
                            itemPreSelecionado = servico2,
                            labelProvider = { categoria -> categoria.nome },
                            onItemSelecionado = { selecionado -> servico2 = selecionado },
                            modifier = Modifier.fillMaxWidth()
                        )
                    }


                }
            }
        }
    }
}
