package com.example.drivez.ui.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.drivez.R
import com.example.drivez.data.model.Veiculo
import com.example.drivez.fontFamily
import com.example.drivez.core.network.theme.AppColors

@Composable
fun CardVeiculo(veiculo: Veiculo, onSalvarAlteracoes: (Veiculo) -> Unit, onCancelarEdicao: () -> Unit,
                onRemoverVeiculo: (Veiculo) -> Unit) {
    var expandido by remember { mutableStateOf(veiculo.id === null) }

    var emEdicao by remember { mutableStateOf(veiculo.id == null) }

    var placaInput by remember { mutableStateOf(veiculo.placa) }
    var renavamInput by remember { mutableStateOf(veiculo.renavam) }
    var validadeInput by remember { mutableStateOf(veiculo.validadeDocumento) }
    var categoriaInput by remember { mutableStateOf(veiculo.categoria) }
    var mensagemErro by remember { mutableStateOf<String?>(null) }

    val exibirBotoesAction = emEdicao

    LaunchedEffect(exibirBotoesAction) {
        if (!exibirBotoesAction) mensagemErro = null
    }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .shadow(4.dp, RoundedCornerShape(8.dp))
            .animateContentSize(),
        shape = RoundedCornerShape(8.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Column {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(AppColors.PrimaryRed)
                    .padding(horizontal = 16.dp, vertical = 12.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = veiculo.titulo.uppercase(),
                    color = Color.White,
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp
                )
                Row() {
                    if (emEdicao && veiculo.id != null) {
                        Icon(
                            painter = painterResource(R.drawable.ic_remove),
                            contentDescription = "Remover Veículo",
                            tint = Color.White,
                            modifier = Modifier
                                .size(30.dp)
                                .clickable {
                                    onRemoverVeiculo(veiculo)
                                }
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                    }
                    Icon(
                        painter = painterResource(R.drawable.ic_edit),
                        contentDescription = "Editar",
                        tint = Color.White,
                        modifier = Modifier
                            .size(30.dp)
                            .clickable {
                                emEdicao = true
                                expandido = true
                            }
                    )
                }
            }

            Column(modifier = Modifier.padding(16.dp)) {
                ItemLinhaEditar(label = "Placa", valor = placaInput, enabled = emEdicao, onValorAlterado = { placaInput = it })

                if (expandido) {
                    Spacer(modifier = Modifier.height(8.dp))
                    ItemLinhaEditar(label = "Renavam", valor = renavamInput, enabled = emEdicao, onValorAlterado = { renavamInput = it })
                    Spacer(modifier = Modifier.height(8.dp))
                    ItemLinhaEditar(label = "Validade Doc.", valor = validadeInput, enabled = emEdicao, onValorAlterado = { validadeInput = it })
                    Spacer(modifier = Modifier.height(8.dp))
                    ItemLinhaEditar(label = "Categoria", valor = categoriaInput, enabled = emEdicao, onValorAlterado = { categoriaInput = it })
                }

                Spacer(modifier = Modifier.height(16.dp))

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable {
                            expandido = !expandido
                            if (!expandido) emEdicao = false
                        },
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = if (expandido) "View Less" else "View More",
                        color = AppColors.LinkBlue,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Medium
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Icon(
                        painter = if (expandido) painterResource(R.drawable.ic_view_less) else painterResource(R.drawable.ic_view_more),
                        contentDescription = null,
                    )
                }

                mensagemErro?.let { erro ->
                    Text(
                        text = erro,
                        color = AppColors.PrimaryRed,
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Medium,
                        fontFamily = fontFamily,
                        modifier = Modifier.padding(top = 12.dp, start = 4.dp)
                    )
                }

                AnimatedVisibility(visible = exibirBotoesAction) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 16.dp),
                        horizontalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        Button(
                            onClick = {
                                placaInput = veiculo.placa
                                renavamInput = veiculo.renavam
                                validadeInput = veiculo.validadeDocumento
                                categoriaInput = veiculo.categoria
                                mensagemErro = null
                                emEdicao = false
                                onCancelarEdicao()
                            },
                            colors = ButtonDefaults.buttonColors(containerColor = AppColors.PrimaryRed),
                            modifier = Modifier.weight(1f),
                            shape = RoundedCornerShape(16.dp)
                        ) {
                            Text("Cancelar", color = Color.White, fontWeight = FontWeight.Bold)
                        }

                        Button(
                            onClick = {
                                val erroResultado: String? = validarCamposVeiculo(
                                    placa = placaInput,
                                    renavam = renavamInput,
                                    validadeDoc = validadeInput,
                                    categoria = categoriaInput
                                )

                                if (erroResultado != null) {
                                    mensagemErro = erroResultado
                                } else {
                                    mensagemErro = null
                                    emEdicao = false

                                    val veiculoAtualizado = veiculo.copy(
                                        placa = placaInput.uppercase().trim(),
                                        renavam = renavamInput.trim(),
                                        validadeDocumento = validadeInput.trim(),
                                        categoria = categoriaInput.trim()
                                    )
                                    onSalvarAlteracoes(veiculoAtualizado)
                                }
                            },
                            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF4CAF50)),
                            modifier = Modifier.weight(1f),
                            shape = RoundedCornerShape(16.dp)
                        ) {
                            Text("Salvar", color = Color.White, fontWeight = FontWeight.Bold)
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun ItemLinhaEditar(label: String, valor: String, enabled: Boolean, onValorAlterado: (String) -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = label,
            color = Color(0xFF757575),
            fontWeight = FontWeight.Medium,
            fontFamily = fontFamily,
            modifier = Modifier.weight(1f),
            fontSize = 15.sp
        )
        TextField(
            value = valor,
            onValueChange = onValorAlterado,
            enabled = enabled,
            colors = TextFieldDefaults.colors(
                focusedContainerColor = Color.Transparent,
                unfocusedContainerColor = Color.Transparent,
                disabledContainerColor = Color.Transparent,
                focusedIndicatorColor = Color(0xFFB0B0B0),
                unfocusedIndicatorColor = Color(0xFFE0E0E0),
                disabledIndicatorColor = Color.Transparent,
                focusedTextColor = Color.Black,
                unfocusedTextColor = Color.Black,
                disabledTextColor = Color.Black
            ),
            modifier = Modifier.weight(1.5f),
        )
    }
}

fun validarCamposVeiculo(placa: String, renavam: String, validadeDoc: String, categoria: String): String? {
    val placaLimpa = placa.replace("-", "").trim()
    val regexPlaca = """^[A-Z]{3}[0-9]{1}[A-Z0-9]{1}[0-9]{2}$""".toRegex(RegexOption.IGNORE_CASE)

    if (placaLimpa.isEmpty()) {
        return "A placa não pode estar vazia."
    }
    if (!placaLimpa.matches(regexPlaca)) {
        return "Formato de placa inválido (ex: ABC-1234 ou ABC1D23)."
    }

    val renavamLimpo = renavam.filter { it.isDigit() }
    if (renavamLimpo.isEmpty()) {
        return "O Renavam não pode estar vazio."
    }
    if (renavamLimpo.length != 11) {
        return "O Renavam deve conter exatamente 11 dígitos."
    }

    val regexData = """^\d{2}/\d{2}/\d{4}$""".toRegex()
    if (validadeDoc.trim().isEmpty()) {
        return "A validade do documento é obrigatória."
    }
    if (!validadeDoc.matches(regexData)) {
        return "Use o formato DD/MM/AAAA para a validade."
    }

    if (categoria.trim().isEmpty()) {
        return "A categoria não pode estar vazia."
    }

    return null
}