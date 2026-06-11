package com.example.drivez.util

import android.os.Build
import androidx.annotation.RequiresApi
import java.time.LocalDateTime
import java.time.OffsetDateTime
import java.time.format.DateTimeFormatter
import java.util.Locale

@RequiresApi(Build.VERSION_CODES.O)
fun FormatarData(data: String): String {
    try {
        // Tenta parsear formato ISO 8601 (ex: 2026-02-11T03:00:00.000Z)
        val dataFormatada = OffsetDateTime.parse(data)
        val formatoDesejado = DateTimeFormatter.ofPattern("dd/MM/yyyy", Locale("pt", "BR"))
        return dataFormatada.format(formatoDesejado)
    } catch (e: Exception) {
        try {
            // Fallback para o formato antigo caso a string seja diferente
            val formatoRecebido = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")
            val formatoDesejado = DateTimeFormatter.ofPattern("dd/MM/yyyy", Locale("pt", "BR"))
            val dataFormatada = LocalDateTime.parse(data, formatoRecebido)
            return dataFormatada.format(formatoDesejado)
        } catch (e2: Exception) {
            return data
        }
    }
}