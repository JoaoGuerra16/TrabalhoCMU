package com.example.trabalhocmu.ui.component
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.provider.Settings
import android.widget.Toast


fun requestWriteSettingsPermission(context: Context) {
    if (!Settings.System.canWrite(context)) {
        // Redireciona para as configurações para conceder a permissão
        val intent = Intent(Settings.ACTION_MANAGE_WRITE_SETTINGS)
        intent.data = Uri.parse("package:${context.packageName}")
        context.startActivity(intent)
        Toast.makeText(context, "Conceda permissão para alterar configurações", Toast.LENGTH_LONG).show()
    }
}
