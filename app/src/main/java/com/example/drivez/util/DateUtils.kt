package com.example.drivez.util

import android.os.Build
import androidx.annotation.RequiresApi
import java.time.LocalDateTime
import java.time.OffsetDateTime
import java.time.format.DateTimeFormatter
import java.util.Locale

@RequiresApi(Build.VERSION_CODES.O)
fun FormatarData(data: String): String {
    if (data.isBlank()) return "Data indisponível"
    try {
        // Tenta parsear formato ISO 8601 (ex: 2026-02-11T03:00:00.000Z)
        val dataFormatada = OffsetDateTime.parse(data)
        val formatoDesejado = DateTimeFormatter.ofPattern("dd/MM/yyyy", Locale("pt", "BR"))
        return dataFormatada.format(formatoDesejado)
    } catch (e: Exception) {
        try {
            // Fallback para o formato antigo caso a string seja diferente (yyyy-MM-dd HH:mm)
            val cleanData = data.split(".")[0] // Remove milissegundos se houver
            val formatoRecebido = if (cleanData.contains("T")) {
                DateTimeFormatter.ISO_LOCAL_DATE_TIME
            } else {
                DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")
            }
            val formatoDesejado = DateTimeFormatter.ofPattern("dd/MM/yyyy", Locale("pt", "BR"))
            val dataFormatada = LocalDateTime.parse(cleanData, formatoRecebido)
            return dataFormatada.format(formatoDesejado)
        } catch (e2: Exception) {
            // Se falhar tudo, tenta extrair apenas os primeiros 10 caracteres (YYYY-MM-DD)
            return if (data.length >= 10 && data[4] == '-' && data[7] == '-') {
                val parts = data.substring(0, 10).split("-")
                "${parts[2]}/${parts[1]}/${parts[0]}"
            } else {
                data
            }
        }
    }
}
