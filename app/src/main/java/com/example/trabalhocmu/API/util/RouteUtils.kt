package com.example.trabalhocmu.API.util

import android.content.Context
import com.example.trabalhocmu.API.RetroInstance.RetrofitInstance
import com.example.trabalhocmu.R
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.LatLngBounds
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.gms.maps.model.PolylineOptions
import com.google.maps.android.PolyUtil
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext


suspend fun fetchAndDisplayRoute(
    googleMap: GoogleMap,
    origin: String,
    destination: String,
    context: Context
) {
    try {
        // Converta os endereços para coordenadas
        val originLatLng = getLatLngFromAddress(origin, context)
        val destinationLatLng = getLatLngFromAddress(destination, context)

        if (originLatLng != null && destinationLatLng != null) {
            // Faça a requisição para a API Directions
            val response = RetrofitInstance.api.getDirections(
                origin = "${originLatLng.first},${originLatLng.second}",
                destination = "${destinationLatLng.first},${destinationLatLng.second}",
                apiKey = context.getString(R.string.google_maps_key)
            )

            if (response.routes.isNotEmpty()) {
                val route = response.routes[0]
                val polylinePoints = PolyUtil.decode(route.overview_polyline.points)

                // Atualize o mapa na thread principal
                withContext(Dispatchers.Main) {
                    googleMap.clear()
                    googleMap.addPolyline(
                        PolylineOptions().addAll(polylinePoints).width(10f).color(android.graphics.Color.BLUE)
                    )

                    googleMap.addMarker(
                        MarkerOptions().position(LatLng(originLatLng.first, originLatLng.second)).title("Start")
                    )
                    googleMap.addMarker(
                        MarkerOptions().position(LatLng(destinationLatLng.first, destinationLatLng.second)).title("End")
                    )

                    // Ajuste a câmera para mostrar toda a rota
                    val boundsBuilder = LatLngBounds.Builder()
                    polylinePoints.forEach { boundsBuilder.include(it) }
                    googleMap.animateCamera(
                        com.google.android.gms.maps.CameraUpdateFactory.newLatLngBounds(boundsBuilder.build(), 100)
                    )
                }
            }
        }
    } catch (e: Exception) {
        e.printStackTrace()
    }
}
