package com.example.trabalhocmu.ui.screen

import androidx.compose.runtime.Composable

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.trabalhocmu.R
import com.example.trabalhocmu.ui.component.SidebarScaffold
import com.example.trabalhocmu.viewmodel.RideViewModel


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ManageRequestsScreen(navController: NavController, rideViewModel: RideViewModel, rideId: Int) {
    val requests by rideViewModel.getPendingRequestsForRide(rideId).collectAsState(initial = emptyList())

    LaunchedEffect(Unit) {
        rideViewModel.syncAllData()
    }

    Box(
        modifier = Modifier.fillMaxSize()
    ){
        Image(
            painter = painterResource(id = R.drawable.background),
            contentDescription = "Background",
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop,
            alpha = 0.8f)
    }

    SidebarScaffold(navController = navController) {
            paddingValues ->
        Scaffold (
            topBar = @androidx.compose.runtime.Composable {
                CenterAlignedTopAppBar(
                    title = {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth(),
                            contentAlignment = Alignment.Center
                        ) {
                        }
                    },
                    colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                        containerColor = Color.Transparent
                    )
                )
            }, containerColor = Color.Transparent,
            content = { padding ->
                Column(
                    modifier = Modifier
                ) {

                    Column(modifier = Modifier.fillMaxSize().padding(16.dp))
                    {
                        ScreenHeader(title = "Manage Request")

                        LazyColumn {
                            items(requests) { request ->
                                RequestItemCard(
                                    requesterEmail = request.requesterEmail,
                                    status = request.status,
                                    isNormalRoute = request.isNormalRoute,
                                    pickupLocation = request.pickupLocation,
                                    dropoffLocation = request.dropoffLocation,
                                    onAccept = {
                                        rideViewModel.respondToRequest(request.id, "ACCEPTED")
                                    },
                                    onReject = {
                                        rideViewModel.respondToRequest(request.id, "REJECTED")
                                    }
                                )
                            }
                        }
                    }
                }
            }
        )
    }
}


@Composable
fun RequestItemCard(
    requesterEmail: String,
    status: String,
    isNormalRoute: Boolean,
    pickupLocation: String?,
    dropoffLocation: String?,
    onAccept: () -> Unit,
    onReject: () -> Unit
) {
    Card(
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(4.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            // Requester Information
            Text(
                text = "Requester: $requesterEmail",
                fontSize = 16.sp,
                fontWeight = FontWeight.SemiBold,
                color = Color(0xFF454B60)
            )
            Text(
                text = "Status: $status",
                fontSize = 14.sp,
                color = Color.Gray
            )

            // Route Information
            if (isNormalRoute) {
                Text(
                    text = "Route: Normal Route",
                    fontSize = 14.sp,
                    color = Color.Black
                )
            } else {
                Text(
                    text = "Pickup: ${pickupLocation ?: "Not specified"}",
                    fontSize = 14.sp,
                    color = Color.Gray
                )
                Text(
                    text = "Dropoff: ${dropoffLocation ?: "Not specified"}",
                    fontSize = 14.sp,
                    color = Color.Gray
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Action Buttons
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End
            ) {
                Button(
                    onClick = onReject,
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.Red,
                        contentColor = Color.White
                    )
                ) {
                    Text("Reject")
                }

                Spacer(modifier = Modifier.width(8.dp))

                Button(
                    onClick = onAccept,
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(
                            0xFF4CAF50
                        )
                    )
                ) {
                    Text("Accept", color = Color.White)
                }
            }
        }
    }
}

@Composable
fun ScreenHeader(title: String) {
    Text(
        text = title,
        fontSize = 24.sp,
        fontWeight = FontWeight.Bold,
        color = Color(0xFF333333),
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 16.dp),
        textAlign = TextAlign.Center
    )
}