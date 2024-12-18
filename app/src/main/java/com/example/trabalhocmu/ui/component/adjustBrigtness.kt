package com.example.trabalhocmu.ui.component

import android.content.Context
import android.provider.Settings
import android.util.Log

fun adjustBrightness(context: Context, brightness: Int) {
    if (Settings.System.canWrite(context)) {
        try {
            val brightnessValue = brightness.coerceIn(0, 255) // Mantém o valor entre 0 e 255
            Settings.System.putInt(
                context.contentResolver,
                Settings.System.SCREEN_BRIGHTNESS,
                brightnessValue
            )
            Log.d("Brightness", "Brilho ajustado para: $brightnessValue")
        } catch (e: Exception) {
            Log.e("Brightness", "Erro ao ajustar brilho: ${e.message}")
        }
    } else {
        Log.e("Brightness", "Permissão WRITE_SETTINGS não concedida.")
        requestWriteSettingsPermission(context) // Redireciona para solicitar permissão
    }
}
