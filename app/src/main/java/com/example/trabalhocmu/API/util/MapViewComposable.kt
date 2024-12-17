package com.example.trabalhocmu.API.util


import android.content.Context
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import com.example.trabalhocmu.API.RetroInstance.RetrofitInstance
import com.example.trabalhocmu.R
import com.google.android.gms.maps.MapView
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


@Composable
fun MapViewComposable(
    startingPoint: String,
    finalDestination: String,
    enableSelection: Boolean = false, // Controla se os cliques são permitidos
    onPickupSelected: ((String) -> Unit)? = null,
    onDropoffSelected: ((String) -> Unit)? = null
) {
    val context = LocalContext.current
    val mapView = remember { MapView(context) }
    val lifecycle = LocalLifecycleOwner.current.lifecycle

    var pickupMarker by remember { mutableStateOf<Marker?>(null) }
    var dropoffMarker by remember { mutableStateOf<Marker?>(null) }
    var isPickupSelected by remember { mutableStateOf(false) } // Controla alternância entre Pickup/Dropoff

    DisposableEffect(Unit) {
        mapView.onCreate(null)
        mapView.onResume()

        val observer = LifecycleEventObserver { _, event ->
            when (event) {
                Lifecycle.Event.ON_RESUME -> mapView.onResume()
                Lifecycle.Event.ON_PAUSE -> mapView.onPause()
                Lifecycle.Event.ON_DESTROY -> mapView.onDestroy()
                else -> {}
            }
        }

        lifecycle.addObserver(observer)

        onDispose {
            lifecycle.removeObserver(observer)
            mapView.onDestroy()
        }
    }

    AndroidView(
        factory = { mapView },
        modifier = Modifier
            .fillMaxWidth()
            .height(350.dp)
    ) { map ->
        map.getMapAsync { googleMap ->
            CoroutineScope(Dispatchers.IO).launch {
                // Exibe a rota entre startingPoint e finalDestination
                fetchAndDisplayRoute(googleMap, startingPoint, finalDestination, context)
            }

            // Se a seleção estiver habilitada, permite cliques no mapa
            if (enableSelection) {
                googleMap.setOnMapClickListener { latLng ->
                    CoroutineScope(Dispatchers.IO).launch {
                        val address = getAddressFromLatLng(latLng.latitude, latLng.longitude, context)

                        withContext(Dispatchers.Main) {
                            if (!isPickupSelected) {
                                // Primeiro clique - define o pickup
                                pickupMarker?.remove()
                                pickupMarker = googleMap.addMarker(
                                    MarkerOptions().position(latLng).title("Pickup Location")
                                )
                                onPickupSelected?.invoke(address)
                                isPickupSelected = true
                            } else {
                                // Segundo clique - define o dropoff
                                dropoffMarker?.remove()
                                dropoffMarker = googleMap.addMarker(
                                    MarkerOptions().position(latLng).title("Dropoff Location")
                                )
                                onDropoffSelected?.invoke(address)
                                isPickupSelected = false // Reinicia para o próximo clique
                            }
                        }
                    }
                }
            }
        }
    }
}

suspend fun getAddressFromLatLng(lat: Double, lng: Double, context: Context): String {
    return try {
        val response = RetrofitInstance.api.getCoordinates(
            address = "$lat,$lng",
            apiKey = context.getString(R.string.google_maps_key)
        )
        if (response.results.isNotEmpty()) {
            response.results[0].formatted_address ?: "Unknown Location"
        } else {
            "Unknown Location"
        }
    } catch (e: Exception) {
        e.printStackTrace()
        "Unknown Location"
    }
}