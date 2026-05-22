package com.example.drivez.core.utils

object SenhaValidator {

    fun avaliarForca(senha: String): SenhaForca {
        if (senha.isEmpty()) return SenhaForca.VAZIA

        var pontos = 0

        if (senha.length >= 8) pontos++
        if (senha.any { it.isUpperCase() }) pontos++
        if (senha.any { it.isLowerCase() }) pontos++
        if (senha.any { it.isDigit() }) pontos++
        val especiais = "!@#$%^&*()_+-=[]{}|;':\",./<>?"
        if (senha.any { it in especiais }) pontos++

        return when (pontos) {
            in 0..2 -> SenhaForca.FRACA
            in 3..4 -> SenhaForca.MEDIA
            else -> SenhaForca.FORTE
        }
    }
}

enum class SenhaForca(val texto: String, val cor: androidx.compose.ui.graphics.Color) {
    VAZIA("", androidx.compose.ui.graphics.Color.Transparent),
    FRACA("Senha fraca (Use letras maiúsculas, números e caracteres especiais)", androidx.compose.ui.graphics.Color(0xFFD32F2F)),
    MEDIA("Senha média (Quase lá, adicione caracteres especiais)", androidx.compose.ui.graphics.Color(0xFFF57C00)),
    FORTE("Senha forte!", androidx.compose.ui.graphics.Color(0xFF388E3C))
}