package com.example.drivez.util

import android.content.Context
import android.location.Address
import android.location.Geocoder
import android.os.Build
import com.mapbox.geojson.Point
import kotlinx.coroutines.suspendCancellableCoroutine
import java.io.IOException
import java.util.Locale
import kotlin.coroutines.resume

suspend fun obterCoordenadasDoEndereco(context: Context, enderecoTexto: String): Point? {
    val geocoder = Geocoder(context, Locale.getDefault())

    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
        return suspendCancellableCoroutine { continuation ->
            geocoder.getFromLocationName(enderecoTexto, 1, object : Geocoder.GeocodeListener {
                override fun onGeocode(addresses: MutableList<Address>) {
                    if (addresses.isNotEmpty()) {
                        val localizacao = addresses[0]
                        continuation.resume(Point.fromLngLat(localizacao.longitude, localizacao.latitude))
                    } else {
                        continuation.resume(null)
                    }
                }

                override fun onError(errorMessage: String?) {
                    continuation.resume(null)
                }
            })
        }
    } else {
        return try {
            @Suppress("DEPRECATION")
            val addresses = geocoder.getFromLocationName(enderecoTexto, 1)
            if (!addresses.isNullOrEmpty()) {
                val localizacao = addresses[0]
                Point.fromLngLat(localizacao.longitude, localizacao.latitude)
            } else {
                null
            }
        } catch (e: IOException) {
            null
        }
    }
}