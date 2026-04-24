package com.example.drivez.util

import android.os.Build
import androidx.annotation.RequiresApi
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.Locale

@RequiresApi(Build.VERSION_CODES.O)
fun FormatarData(data: String): String {
    try {
        val formatoRecebido = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")

        val formatoDesejado = DateTimeFormatter.ofPattern("dd/MM/yyyy", Locale("pt", "BR"))

        val dataFormatada = LocalDateTime.parse(data, formatoRecebido)

        return dataFormatada.format(formatoDesejado)
    } catch (e: Exception) {
        return data
    }
}