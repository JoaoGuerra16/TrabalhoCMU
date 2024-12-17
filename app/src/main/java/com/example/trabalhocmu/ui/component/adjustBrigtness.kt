package com.example.trabalhocmu.ui.component

import android.content.Context
import android.provider.Settings
import android.util.Log

fun adjustBrightness(context: Context, brightness: Int) {
    try {
        // Verifica se a permissão WRITE_SETTINGS foi concedida
        if (Settings.System.canWrite(context)) {
            val brightnessValue = brightness.coerceIn(0, 255) // Garante que está entre 0 e 255
            Settings.System.putInt(
                context.contentResolver,
                Settings.System.SCREEN_BRIGHTNESS,
                brightnessValue
            )
            Log.d("Brightness", "Brilho ajustado para: $brightnessValue")
        } else {
            Log.e("Brightness", "Permissão WRITE_SETTINGS não concedida.")
        }
    } catch (e: Exception) {
        Log.e("Brightness", "Erro ao ajustar brilho: ${e.message}")
    }
}
