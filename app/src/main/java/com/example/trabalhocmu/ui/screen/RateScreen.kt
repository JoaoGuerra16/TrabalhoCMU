//package com.example.trabalhocmu.ui.screen
//
//import android.widget.Toast
//import androidx.compose.foundation.Image
//import androidx.compose.foundation.clickable
//import androidx.compose.foundation.layout.*
//import androidx.compose.foundation.shape.CircleShape
//import androidx.compose.material.icons.Icons
//import androidx.compose.material.icons.filled.StarRate
//import androidx.compose.material.icons.outlined.StarRate
//import androidx.compose.material3.*
//import androidx.compose.runtime.*
//import androidx.compose.ui.Alignment
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.draw.clip
//import androidx.compose.ui.graphics.Color
//import androidx.compose.ui.platform.LocalContext
//import androidx.compose.ui.res.painterResource
//import androidx.compose.ui.res.stringResource
//import androidx.compose.ui.text.font.FontWeight
//import androidx.compose.ui.unit.dp
//import androidx.compose.ui.unit.sp
//import androidx.navigation.NavController
//import com.example.trabalhocmu.R
//import com.example.trabalhocmu.ui.component.SidebarScaffold
//import com.example.trabalhocmu.viewmodel.RideViewModel
//import kotlinx.coroutines.launch
//
//@Composable
//fun RateScreen(
//    navController: NavController,
//    rideId: Int,
//    rideViewModel: RideViewModel
//) {
//    var rating by remember { mutableStateOf(0) }
//    val coroutineScope = rememberCoroutineScope()
//    val context = LocalContext.current
//
//    SidebarScaffold(
//        navController = navController,
//        content = { paddingValues ->
//            Box(
//                modifier = Modifier
//                    .fillMaxSize()
//                    .padding(paddingValues)
//            ) {
//                Column(
//                    horizontalAlignment = Alignment.CenterHorizontally,
//                    verticalArrangement = Arrangement.Center,
//                    modifier = Modifier
//                        .fillMaxSize()
//                        .padding(16.dp)
//                ) {
//                    // Imagem do perfil do condutor
//                    Image(
//                        painter = painterResource(id = R.drawable.profile),
//                        contentDescription = stringResource(id = R.string.profile_image_desc),
//                        modifier = Modifier
//                            .size(100.dp)
//                            .clip(CircleShape)
//                            .align(Alignment.CenterHorizontally)
//                    )
//
//                    Spacer(modifier = Modifier.height(20.dp))
//
//                    Text(
//                        text = "How would you rate this ride?",
//                        fontSize = 20.sp,
//                        fontWeight = FontWeight.Bold
//                    )
//
//                    Spacer(modifier = Modifier.height(16.dp))
//
//                    // Seleção de estrelas
//                    Row(horizontalArrangement = Arrangement.Center) {
//                        for (i in 1..5) {
//                            Icon(
//                                imageVector = if (i <= rating) Icons.Filled.StarRate else Icons.Outlined.StarRate,
//                                contentDescription = "Star $i",
//                                modifier = Modifier
//                                    .size(50.dp)
//                                    .clickable { rating = i },
//                                tint = Color(0xFFFFC107)
//                            )
//                        }
//                    }
//
//                    Spacer(modifier = Modifier.height(20.dp))
//
//                    Text(
//                        text = if (rating > 0) "You selected $rating star${if (rating > 1) "s" else ""}" else "Select a rating",
//                        fontSize = 16.sp
//                    )
//
//                    Spacer(modifier = Modifier.height(30.dp))
//
//                    // Botão para submeter avaliação
//                    Button(
//                        onClick = {
//                            if (rating > 0) {
//                                coroutineScope.launch {
//                                    rideViewModel.updateParticipantRating(rideId, rating.toFloat())
//                                    navController.popBackStack() // Retorna para tela anterior
//                                    Toast.makeText(context, "Rating submitted successfully!", Toast.LENGTH_SHORT).show()
//                                }
//                            } else {
//                                Toast.makeText(context, "Please select a rating", Toast.LENGTH_SHORT).show()
//                            }
//                        },
//                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF4CAF50)),
//                        modifier = Modifier
//                            .fillMaxWidth()
//                            .height(50.dp)
//                    ) {
//                        Text("Submit Rating", fontSize = 18.sp, color = Color.White)
//                    }
//                }
//            }
//        }
//    )
//}
