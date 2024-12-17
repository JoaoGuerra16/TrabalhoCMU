package com.example.trabalhocmu.ui.activity

import android.Manifest
import android.content.*
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.BatteryManager
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.material3.rememberDrawerState
import androidx.compose.material3.DrawerValue
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.rememberNavController
import com.example.trabalhocmu.viewmodel.LanguageViewModel
import com.example.trabalhocmu.viewmodel.LanguageViewModelFactory
import com.example.trabalhocmu.viewmodel.RatingViewModel
import com.example.trabalhocmu.viewmodel.RideViewModel
import com.example.trabalhocmu.viewmodel.RideViewModelFactory
import java.util.Locale
import androidx.compose.runtime.mutableStateOf
import com.example.trabalhocmu.notificacoes.createNotificationChannel
import com.example.trabalhocmu.notificacoes.sendNotification

class MainActivity : ComponentActivity() {

    // Sensor-related variables
    private lateinit var sensorManager: SensorManager
    private var lightSensor: Sensor? = null
    private lateinit var lightSensorListener: SensorEventListener
    private val lightValue = mutableStateOf(0f)

    // BroadcastReceiver para monitorar a bateria
    private lateinit var batteryReceiver: BroadcastReceiver

    private val requestPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted: Boolean ->
        if (isGranted) {
            sendNotification(this, "Permissão Concedida", "Agora pode receber notificações.")
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Solicitar permissão para notificações no Android 13+
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            requestPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
        }

        setAppLanguage()
        initializeLightSensor()
        initializeBatteryReceiver()

        setContent {
            val navController = rememberNavController()
            val drawerState = rememberDrawerState(DrawerValue.Closed)

            // ViewModels compartilhados
            val ratingViewModel: RatingViewModel = viewModel()
            val languageViewModel: LanguageViewModel =
                viewModel(factory = LanguageViewModelFactory(this))
            val rideViewModel: RideViewModel = viewModel(factory = RideViewModelFactory(this))

            // Navegação principal com o sensor de luz integrado
            MainNavGraph(
                navController = navController,
                drawerState = drawerState,
                ratingViewModel = ratingViewModel,
                languageViewModel = languageViewModel,
                rideViewModel = rideViewModel,
                lightValue = lightValue.value
            )
        }
    }

    private fun initializeLightSensor() {
        sensorManager = getSystemService(SENSOR_SERVICE) as SensorManager
        lightSensor = sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT)

        lightSensorListener = object : SensorEventListener {
            override fun onSensorChanged(event: SensorEvent?) {
                lightValue.value = event?.values?.get(0) ?: 0f
            }

            override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {}
        }
    }

    private fun initializeBatteryReceiver() {
        batteryReceiver = object : BroadcastReceiver() {
            override fun onReceive(context: Context?, intent: Intent?) {
                val level = intent?.getIntExtra(BatteryManager.EXTRA_LEVEL, -1) ?: -1
                val scale = intent?.getIntExtra(BatteryManager.EXTRA_SCALE, -1) ?: -1
                val batteryPct = level / scale.toFloat() * 100

                if (batteryPct <= 15) {
                    adjustBrightness(this@MainActivity, 50) // Reduz o brilho
                    sendNotification(
                        this@MainActivity,
                        "Bateria Baixa",
                        "O brilho foi reduzido para economizar bateria."
                    )
                }
            }
        }
    }

    private fun adjustBrightness(context: Context, brightness: Int) {
        try {
            if (Settings.System.canWrite(context)) {
                val brightnessValue = brightness.coerceIn(0, 255)
                Settings.System.putInt(
                    context.contentResolver,
                    Settings.System.SCREEN_BRIGHTNESS,
                    brightnessValue
                )
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override fun onResume() {
        super.onResume()
        lightSensor?.let {
            sensorManager.registerListener(lightSensorListener, it, SensorManager.SENSOR_DELAY_NORMAL)
        }

        // Registrar o BroadcastReceiver da bateria
        val filter = IntentFilter(Intent.ACTION_BATTERY_CHANGED)
        registerReceiver(batteryReceiver, filter)
    }

    override fun onPause() {
        super.onPause()
        sensorManager.unregisterListener(lightSensorListener)

        // Desregistrar o BroadcastReceiver
        unregisterReceiver(batteryReceiver)
    }

    private fun setAppLanguage() {
        val sharedPreferences = getSharedPreferences("app_prefs", MODE_PRIVATE)
        val savedLanguage = sharedPreferences.getString("selected_language", "English") ?: "English"
        val locale = when (savedLanguage) {
            "English" -> Locale("en")
            "Português" -> Locale("pt")
            else -> Locale("en")
        }

        Locale.setDefault(locale)
        val config = resources.configuration
        config.setLocale(locale)
        resources.updateConfiguration(config, resources.displayMetrics)
    }
}
