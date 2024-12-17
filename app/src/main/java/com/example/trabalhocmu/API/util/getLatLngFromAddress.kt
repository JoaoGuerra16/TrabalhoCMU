package com.example.trabalhocmu.API.util

import android.content.Context
import com.example.trabalhocmu.API.RetroInstance.RetrofitInstance
import com.example.trabalhocmu.R


suspend fun getLatLngFromAddress(address: String, context: Context): Pair<Double, Double>? {
    return try {
        val response = RetrofitInstance.api.getCoordinates(
            address = address,
            apiKey = context.getString(R.string.google_maps_key)
        )
        if (response.results.isNotEmpty()) {
            val location = response.results[0].geometry.location
            Pair(location.lat, location.lng)
        } else {
            null
        }
    } catch (e: Exception) {
        e.printStackTrace()
        null
    }
}
