package com.example.trabalhocmu.ui.screen

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.StarRate
import androidx.compose.material.icons.outlined.StarRate
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.trabalhocmu.R
import com.example.trabalhocmu.ui.component.SidebarScaffold
import com.example.trabalhocmu.viewmodel.RideViewModel
import kotlinx.coroutines.launch

@Composable
fun RateScreen(
    navController: NavController,
    rideId: Int,
    driverEmail: String, viewModel: RideViewModel

) {
    var rating by remember { mutableStateOf(0) }

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text("Rate your Driver", fontSize = 22.sp, fontWeight = FontWeight.Bold)

        Row {
            for (i in 1..5) {
                Icon(
                    imageVector = if (i <= rating) Icons.Filled.StarRate else Icons.Outlined.StarRate,
                    contentDescription = "Star $i",
                    modifier = Modifier
                        .size(50.dp)
                        .clickable { rating = i },
                    tint = Color(0xFFFFC107)
                )
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        Button(onClick = {
            viewModel.submitRating(rideId, driverEmail, rating)
            navController.popBackStack() // Retorna após avaliar
        }) {
            Text("Submit Rating")
        }
    }
}
