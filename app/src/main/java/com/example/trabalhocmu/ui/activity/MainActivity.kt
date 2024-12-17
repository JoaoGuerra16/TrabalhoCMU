package com.example.trabalhocmu.ui.activity

import android.Manifest
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Build
import android.os.Bundle
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
    private val lightValue = mutableStateOf(0f) // Shared state for the light sensor
    private val requestPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted: Boolean ->
        if (isGranted) {
            sendNotification(this, "Permissão Concedida", "Agora pode receber notificações.")
        } else {
            // Ver esta parte, faz alguma coisa quando a autorização é negada
        }
    }



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            requestPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
        }

        setAppLanguage()
        initializeLightSensor()

        setContent {
            val navController = rememberNavController()
            val drawerState = rememberDrawerState(DrawerValue.Closed)

            // ViewModels compartilhados
            val ratingViewModel: RatingViewModel = viewModel()
            val languageViewModel: LanguageViewModel =
                viewModel(factory = LanguageViewModelFactory(this))
            val rideViewModel: RideViewModel = viewModel(factory = RideViewModelFactory(this)) // Usando o factory

            // Navegação principal com o sensor de luz integrado
            MainNavGraph(
                navController = navController,
                drawerState = drawerState,
                ratingViewModel = ratingViewModel,
                languageViewModel = languageViewModel,
                rideViewModel = rideViewModel,
                lightValue = lightValue.value // Passa o valor do sensor como parâmetro
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

            override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {

            }
        }
    }

    override fun onResume() {
        super.onResume()
        lightSensor?.let {
            sensorManager.registerListener(lightSensorListener, it, SensorManager.SENSOR_DELAY_NORMAL)
        }
    }

    override fun onPause() {
        super.onPause()
        sensorManager.unregisterListener(lightSensorListener)
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

