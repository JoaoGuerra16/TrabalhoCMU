package com.example.trabalhocmu.ui.activity

import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
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
import androidx.compose.runtime.Composable
import androidx.compose.material3.Text
import androidx.compose.foundation.layout.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

class MainActivity : ComponentActivity() {

    // Sensor-related variables
    private lateinit var sensorManager: SensorManager
    private var lightSensor: Sensor? = null
    private lateinit var lightSensorListener: SensorEventListener
    private val lightValue = mutableStateOf(0f) // Shared state for the light sensor

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

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
                // Não necessário neste exemplo
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


