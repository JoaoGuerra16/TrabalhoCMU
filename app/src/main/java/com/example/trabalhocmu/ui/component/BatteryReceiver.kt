package com.example.trabalhocmu.ui.component

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.BatteryManager

class BatteryReceiver(private val context: Context) : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        val level = intent.getIntExtra(BatteryManager.EXTRA_LEVEL, -1)
        val scale = intent.getIntExtra(BatteryManager.EXTRA_SCALE, -1)
        val batteryPct = level / scale.toFloat() * 100

        if (batteryPct <= 15) {
            // Se a bateria estiver abaixo de 15%, ajusta o brilho para um valor baixo (ex: 50)
            adjustBrightness(context, 50)
        } else {
            // Caso contrário, restaura o brilho normal (ex: 150)
            adjustBrightness(context, 150)
        }
    }
}
