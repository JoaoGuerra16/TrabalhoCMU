package com.example.trabalhocmu.sensors

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp



class LightSensorActivity : ComponentActivity() {
    private lateinit var sensorManager: SensorManager
    private var lightSensor: Sensor? = null
    private lateinit var lightSensorListener: SensorEventListener

    private var lightValue by mutableStateOf(0f)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        sensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager
        lightSensor = sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT)

        lightSensorListener = object : SensorEventListener {
            override fun onSensorChanged(event: SensorEvent?) {
                lightValue = event?.values?.get(0) ?: 0f // Update the shared state
            }

            override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {}
        }

        setContent {
            LightSensorScreen(lightValue)
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
}

@Composable
fun LightSensorScreen(lightValue: Float) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Light Sensor",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = "Current Light Level: ${lightValue.toInt()} lux",
            fontSize = 18.sp
        )
        Spacer(modifier = Modifier.height(16.dp))
        when {
            lightValue < 10 -> Text("Very Dark", fontSize = 16.sp, color = MaterialTheme.colorScheme.primary)
            lightValue < 500 -> Text("Normal Ambient Light", fontSize = 16.sp, color = MaterialTheme.colorScheme.secondary)
            lightValue < 10000 -> Text("Bright", fontSize = 16.sp, color = MaterialTheme.colorScheme.tertiary)
            else -> Text("Very Bright (Sunlight)", fontSize = 16.sp, color = MaterialTheme.colorScheme.error)
        }
    }
}