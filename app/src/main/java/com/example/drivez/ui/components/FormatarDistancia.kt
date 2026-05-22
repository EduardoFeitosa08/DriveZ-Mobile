package com.example.drivez.ui.components

fun formatarDistancia(distancia: Double): String {
    return if (distancia < 1.0) {
        "${(distancia * 1000).toInt()}m de distancia"
    } else {
        "%.1fkm de distancia".format(distancia)
    }
}