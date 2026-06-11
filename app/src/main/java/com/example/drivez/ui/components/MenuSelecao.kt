//package com.example.drivez.components
//
//import androidx.compose.material3.*
//import androidx.compose.runtime.*
//import androidx.compose.ui.Modifier
//
//@OptIn(ExperimentalMaterial3Api::class)
//@Composable
//fun <T> MenuSelecao(
//    label: String,
//    itens: List<T>,
//    itemPreSelecionado: T?,
//    labelProvider: (T) -> String,
//    onItemSelecionado: (T) -> Unit,
//    modifier: Modifier = Modifier
//) {
//    var expanded by remember { mutableStateOf(false) }
//
//    ExposedDropdownMenuBox(
//        expanded = expanded,
//        onExpandedChange = { expanded = !expanded },
//        modifier = modifier
//    ) {
//        OutlinedTextField(
//            value = itemPreSelecionado?.let { labelProvider(it) } ?: "",
//            onValueChange = {},
//            readOnly = true,
//            label = { Text(label) },
//            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
//            colors = ExposedDropdownMenuDefaults.outlinedTextFieldColors(),
//            modifier = Modifier.menuAnchor()
//        )
//
//        ExposedDropdownMenu(
//            expanded = expanded,
//            onDismissRequest = { expanded = false }
//        ) {
//            itens.forEach { item ->
//                DropdownMenuItem(
//                    text = { Text(labelProvider(item)) },
//                    onClick = {
//                        onItemSelecionado(item)
//                        expanded = false
//                    },
//                    contentPadding = ExposedDropdownMenuDefaults.ItemContentPadding
//                )
//            }
//        }
//    }
//}

package com.example.drivez.ui.components

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.drivez.core.network.theme.fontFamily
import com.example.drivez.core.network.theme.AppColors

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun <T> MenuSelecao(
    campoNome: String,
    itens: List<T>,
    itemPreSelecionado: T?,
    labelProvider: (T) -> String,
    onItemSelecionado: (T) -> Unit,
    modifier: Modifier = Modifier
) {
    var expanded by remember { mutableStateOf(false) }
    val textoExibido = itemPreSelecionado?.let { labelProvider(it) } ?: ""

    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = { expanded = !expanded },
        modifier = modifier
    ) {
        OutlinedTextField(
            value = textoExibido,
            onValueChange = {},
            readOnly = true,
            modifier = Modifier
                .fillMaxWidth()
                .height(60.dp)
                .border(1.dp, Color(0XFF6D6D6D), RoundedCornerShape(15.dp))
                .menuAnchor(),
            placeholder = {
                Text(
                    text = campoNome,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Medium,
                    color = Color(0XFF6D6D6D),
                    fontFamily = fontFamily
                )
            },
            shape = RoundedCornerShape(15.dp),
            trailingIcon = {
                ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded)
            },
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = AppColors.SecondaryRed,
                unfocusedBorderColor = AppColors.BorderGray,
                focusedLabelColor = AppColors.SecondaryRed,
                unfocusedLabelColor = AppColors.PlaceholderGray,
                focusedPlaceholderColor = AppColors.SecondaryRed,
                unfocusedPlaceholderColor = AppColors.PlaceholderGray,
                disabledContainerColor = Color.White,
                focusedTextColor = AppColors.DarkBlue,
                unfocusedTextColor = AppColors.DarkBlue,
                unfocusedContainerColor = if (textoExibido.isNotEmpty()) Color(0xFFE6EEF8) else Color.White,
                focusedContainerColor = Color.White
            )
        )

        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            containerColor = Color.White
        ) {
            itens.forEach { item ->
                DropdownMenuItem(
                    text = {
                        Text(
                            text = labelProvider(item),
                            fontSize = 14.sp,
                            fontFamily = fontFamily,
                            color = AppColors.DarkBlue
                        )
                    },
                    onClick = {
                        onItemSelecionado(item)
                        expanded = false
                    },
                    contentPadding = ExposedDropdownMenuDefaults.ItemContentPadding
                )
            }
        }
    }
}