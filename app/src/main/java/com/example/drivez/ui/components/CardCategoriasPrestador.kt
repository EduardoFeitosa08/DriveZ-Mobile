package com.example.drivez.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.drivez.R
import com.example.drivez.core.network.theme.AppColors
import com.example.drivez.data.model.CategoriaServico
import androidx.compose.foundation.lazy.grid.GridItemSpan

//@OptIn(ExperimentalMaterial3Api::class)
//@Composable
//fun CardCategorias(
//    categoriaAtual: String?,
//    onDismissRequest: () -> Unit,
//    onCategoriaSelecionada: (String?) -> Unit
//) {
//    val categorias = remember { CategoriaServico.entries.map { it.nome } }
//
//    val temFiltroAtivo = categoriaAtual != null
//
//    ModalBottomSheet(
//        onDismissRequest = onDismissRequest,
//        containerColor = Color.White,
//        shape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp)
//    ) {
//        Column(
//            modifier = Modifier
//                .fillMaxWidth()
//                .padding(16.dp)
//        ) {
//            Row(
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .padding(bottom = 16.dp),
//                horizontalArrangement = Arrangement.SpaceBetween,
//                verticalAlignment = Alignment.CenterVertically
//            ) {
//                Text(
//                    text = "Selecione uma categoria de serviço",
//                    fontSize = 17.sp,
//                    fontWeight = FontWeight.Bold,
//                    color = Color.Black,
//                    modifier = Modifier.weight(1f)
//                )
//
//                IconButton(
//                    onClick = {
//                        onCategoriaSelecionada(null)
//                        onDismissRequest()
//                    },
//                    enabled = temFiltroAtivo
//                ) {
//                    Icon(
//                        painter = painterResource(R.drawable.ic_remove),
//                        contentDescription = "Limpar Filtro",
//                        tint = if (temFiltroAtivo) AppColors.SecondaryRed else Color(0xFFB0B0B0),
//                        modifier = Modifier.size(24.dp)
//                    )
//                }
//            }
//
//            LazyVerticalGrid(
//                columns = GridCells.Fixed(2),
//                horizontalArrangement = Arrangement.spacedBy(10.dp),
//                verticalArrangement = Arrangement.spacedBy(10.dp),
//                modifier = Modifier.padding(bottom = 32.dp)
//            ) {
//                items(categorias) { categoria ->
//                    val estaSelecionada = categoria == categoriaAtual
//
//                    Card(
//                        modifier = Modifier
//                            .fillMaxWidth()
//                            .clickable {
//                                onCategoriaSelecionada(categoria)
//                                onDismissRequest()
//                            },
//                        colors = CardDefaults.cardColors(
//                            containerColor = if (estaSelecionada) AppColors.SecondaryRed.copy(alpha = 0.15f) else Color(0xFFF5F5F5)
//                        ),
//                        border = if (estaSelecionada) androidx.compose.foundation.BorderStroke(1.5.dp, AppColors.SecondaryRed) else null,
//                        shape = RoundedCornerShape(10.dp)
//                    ) {
//                        Text(
//                            text = categoria,
//                            modifier = Modifier.padding(16.dp),
//                            fontWeight = if (estaSelecionada) FontWeight.Bold else FontWeight.Medium,
//                            fontSize = 14.sp,
//                            color = if (estaSelecionada) AppColors.SecondaryRed else Color.Black
//                        )
//                    }
//                }
//            }
//        }
//    }
//}

//@OptIn(ExperimentalMaterial3Api::class)
//@Composable
//fun CardCategorias(
//    categoriaAtual: String?,
//    onDismissRequest: () -> Unit,
//    onCategoriaSelecionada: (String?) -> Unit
//) {
//    val todasCategorias = remember { CategoriaServico.entries.map { it.nome } }
//
//    val nomeGuincho = CategoriaServico.GUINCHO.nome
//    val outrasCategorias = remember { todasCategorias.filter { it != nomeGuincho } }
//
//    val temFiltroAtivo = categoriaAtual != null
//
//    ModalBottomSheet(
//        onDismissRequest = onDismissRequest,
//        containerColor = Color.White,
//        shape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp)
//    ) {
//        Column(
//            modifier = Modifier
//                .fillMaxWidth()
//                .padding(16.dp)
//        ) {
//            Row(
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .padding(bottom = 16.dp),
//                horizontalArrangement = Arrangement.SpaceBetween,
//                verticalAlignment = Alignment.CenterVertically
//            ) {
//                Text(
//                    text = "Selecione uma categoria de serviço",
//                    fontSize = 17.sp,
//                    fontWeight = FontWeight.Bold,
//                    color = Color.Black,
//                    modifier = Modifier.weight(1f)
//                )
//
//                IconButton(
//                    onClick = {
//                        onCategoriaSelecionada(null)
//                        onDismissRequest()
//                    },
//                    enabled = temFiltroAtivo
//                ) {
//                    Icon(
//                        painter = painterResource(R.drawable.ic_remove),
//                        contentDescription = "Limpar Filtro",
//                        tint = if (temFiltroAtivo) AppColors.SecondaryRed else Color(0xFFB0B0B0),
//                        modifier = Modifier.size(24.dp)
//                    )
//                }
//            }
//
//            LazyVerticalGrid(
//                columns = GridCells.Fixed(2),
//                horizontalArrangement = Arrangement.spacedBy(10.dp),
//                verticalArrangement = Arrangement.spacedBy(10.dp),
//                modifier = Modifier.padding(bottom = 32.dp)
//            ) {
//                item(span = { GridItemSpan(maxLineSpan) }) {
//                    val estaSelecionado = categoriaAtual == nomeGuincho
//
//                    Card(
//                        modifier = Modifier
//                            .fillMaxWidth()
//                            .clickable {
//                                onCategoriaSelecionada(nomeGuincho)
//                                onDismissRequest()
//                            },
//                        colors = CardDefaults.cardColors(
//                            containerColor = if (estaSelecionado) AppColors.SecondaryRed.copy(alpha = 0.15f) else Color(0xFFF5F5F5)
//                        ),
//                        border = if (estaSelecionado) androidx.compose.foundation.BorderStroke(1.5.dp, AppColors.SecondaryRed) else null,
//                        shape = RoundedCornerShape(10.dp)
//                    ) {
//                        Box(
//                            modifier = Modifier
//                                .fillMaxWidth()
//                                .padding(horizontal = 16.dp, vertical = 20.dp),
//                            contentAlignment = Alignment.Center
//                        ) {
//                            Text(
//                                text = nomeGuincho,
//                                fontWeight = if (estaSelecionado) FontWeight.Bold else FontWeight.SemiBold,
//                                fontSize = 15.sp,
//                                color = if (estaSelecionado) AppColors.SecondaryRed else Color.Black
//                            )
//                        }
//                    }
//                }
//
//                items(outrasCategorias) { categoria ->
//                    val estaSelecionada = categoria == categoriaAtual
//
//                    Card(
//                        modifier = Modifier
//                            .fillMaxWidth()
//                            .clickable {
//                                onCategoriaSelecionada(categoria)
//                                onDismissRequest()
//                            },
//                        colors = CardDefaults.cardColors(
//                            containerColor = if (estaSelecionada) AppColors.SecondaryRed.copy(alpha = 0.15f) else Color(0xFFF5F5F5)
//                        ),
//                        border = if (estaSelecionada) androidx.compose.foundation.BorderStroke(1.5.dp, AppColors.SecondaryRed) else null,
//                        shape = RoundedCornerShape(10.dp)
//                    ) {
//                        Text(
//                            text = categoria,
//                            modifier = Modifier.padding(16.dp),
//                            fontWeight = if (estaSelecionada) FontWeight.Bold else FontWeight.Medium,
//                            fontSize = 14.sp,
//                            color = if (estaSelecionada) AppColors.SecondaryRed else Color.Black
//                        )
//                    }
//                }
//            }
//        }
//    }
//}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CardCategorias(
    categoriaAtual: String?,
    onDismissRequest: () -> Unit,
    onCategoriaSelecionada: (String?) -> Unit
) {
    val todasCategorias = remember { CategoriaServico.entries }

    val categoriaGuincho = CategoriaServico.GUINCHO
    val outrasCategorias = remember { todasCategorias.filter { it != categoriaGuincho } }

    val temFiltroAtivo = categoriaAtual != null

    ModalBottomSheet(
        onDismissRequest = onDismissRequest,
        containerColor = Color.White,
        shape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Selecione uma categoria de serviço",
                    fontSize = 17.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black,
                    modifier = Modifier.weight(1f)
                )

                IconButton(
                    onClick = {
                        onCategoriaSelecionada(null)
                        onDismissRequest()
                    },
                    enabled = temFiltroAtivo
                ) {
                    Icon(
                        painter = painterResource(R.drawable.ic_remove),
                        contentDescription = "Limpar Filtro",
                        tint = if (temFiltroAtivo) AppColors.SecondaryRed else Color(0xFFB0B0B0),
                        modifier = Modifier.size(24.dp)
                    )
                }
            }

            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                horizontalArrangement = Arrangement.spacedBy(10.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp),
                modifier = Modifier.padding(bottom = 32.dp)
            ) {
                item(span = { GridItemSpan(maxLineSpan) }) {
                    val estaSelecionado = categoriaAtual == categoriaGuincho.nome

                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable {
                                onCategoriaSelecionada(categoriaGuincho.nome)
                                onDismissRequest()
                            },
                        colors = CardDefaults.cardColors(
                            containerColor = if (estaSelecionado) AppColors.SecondaryRed.copy(alpha = 0.15f) else Color(0xFFF5F5F5)
                        ),
                        border = if (estaSelecionado) androidx.compose.foundation.BorderStroke(1.5.dp, AppColors.SecondaryRed) else null,
                        shape = RoundedCornerShape(10.dp)
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp),
                            horizontalArrangement = Arrangement.Center,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                painter = painterResource(obterIconeParaCategoria(categoriaGuincho)),
                                contentDescription = null,
                                tint = if (estaSelecionado) AppColors.SecondaryRed else Color.Black,
                                modifier = Modifier.size(22.dp)
                            )
                            Spacer(modifier = Modifier.width(10.dp))
                            Text(
                                text = categoriaGuincho.nome,
                                fontWeight = if (estaSelecionado) FontWeight.Bold else FontWeight.SemiBold,
                                fontSize = 15.sp,
                                color = if (estaSelecionado) AppColors.SecondaryRed else Color.Black
                            )
                        }
                    }
                }

                items(outrasCategorias) { enumCategoria ->
                    val estaSelecionada = enumCategoria.nome == categoriaAtual

                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable {
                                onCategoriaSelecionada(enumCategoria.nome)
                                onDismissRequest()
                            },
                        colors = CardDefaults.cardColors(
                            containerColor = if (estaSelecionada) AppColors.SecondaryRed.copy(alpha = 0.15f) else Color(0xFFF5F5F5)
                        ),
                        border = if (estaSelecionada) androidx.compose.foundation.BorderStroke(1.5.dp, AppColors.SecondaryRed) else null,
                        shape = RoundedCornerShape(10.dp)
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(14.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                painter = painterResource(obterIconeParaCategoria(enumCategoria)),
                                contentDescription = null,
                                tint = if (estaSelecionada) AppColors.SecondaryRed else Color(0xFF616161),
                                modifier = Modifier.size(18.dp)
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = enumCategoria.nome,
                                fontWeight = if (estaSelecionada) FontWeight.Bold else FontWeight.Medium,
                                fontSize = 13.sp,
                                color = if (estaSelecionada) AppColors.SecondaryRed else Color.Black,
                                maxLines = 1
                            )
                        }
                    }
                }
            }
        }
    }
}

fun obterIconeParaCategoria(categoria: CategoriaServico): Int {
    return when (categoria) {
        CategoriaServico.GUINCHO -> R.drawable.ic_guincho_filled
        CategoriaServico.BORRACHARIA -> R.drawable.ic_borracharia_filled
        CategoriaServico.AUTO_ELETRICA -> R.drawable.ic_auto_eletrica_filled
        CategoriaServico.MECANICA_MOVEL -> R.drawable.ic_mecanica_filled
        CategoriaServico.CHAVEIRO -> R.drawable.ic_chaveiro_filled
        CategoriaServico.PANE_SECA -> R.drawable.ic_posto_de_gasolina_filled
        CategoriaServico.CARGA_BATERIA -> R.drawable.ic_carga_bateria_filled
        CategoriaServico.INJECAO_ELETRONICA -> R.drawable.ic_injecao_eletronica_filled
        CategoriaServico.LEVA_E_TRAZ -> R.drawable.ic_leva_e_traz_filled
    }
}