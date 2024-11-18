package com.example.trabalhocmu.ui.screen

import com.example.trabalhocmu.viewmodel.RatingViewModel
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.navigation.compose.rememberNavController
import com.example.trabalhocmu.R
import com.example.trabalhocmu.ui.component.SidebarScaffold
import kotlin.math.roundToInt
import com.example.trabalhocmu.ui.theme.PoppinsFamily

@Composable
fun RiderProfile(
    navController: NavController,
    ratingViewModel: RatingViewModel = viewModel()
) {
    val scrollState = rememberScrollState()
    val fullName = remember { mutableStateOf("John Doe") }
    val username = remember { mutableStateOf("JohnDoe") }
    val mobile = remember { mutableStateOf("+1234567890") }
    val email = remember { mutableStateOf("john.doe@example.com") }
    val age = remember { mutableStateOf("30") }
    val gender = remember { mutableStateOf("Male") }
    val averageRating by ratingViewModel.averageRating.collectAsState()
    val roundedRating = averageRating.roundToInt()

    SidebarScaffold(
        navController = navController,
        content = { padding ->

            Box(modifier = Modifier.fillMaxSize()) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(15.dp)
                        .padding(top = 10.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Spacer(modifier = Modifier.height(16.dp))

                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center,
                        modifier = Modifier.padding(start = 35.dp)
                    ) {
                        Text(
                            text = stringResource(R.string.Rider_profile),
                            fontSize = 25.sp,
                            fontWeight = FontWeight.Bold,
                            fontFamily = PoppinsFamily
                        )
                        IconButton(onClick = {
                            navController.navigate("EditProfile")
                        }) {}
                    }
                    Spacer(modifier = Modifier.height(16.dp))

                    Image(
                        painter = painterResource(id = R.drawable.profile),
                        contentDescription = stringResource(R.string.profile_image_description),
                        modifier = Modifier
                            .size(100.dp)
                            .clip(CircleShape)
                    )
                    Spacer(modifier = Modifier.height(25.dp))

                    Text(
                        text = fullName.value,
                        fontSize = 22.sp,
                        fontWeight = FontWeight.Bold,
                        fontFamily = PoppinsFamily,
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.Center
                    )
                    Spacer(modifier = Modifier.height(10.dp))

                    RatingStars(rating = roundedRating)
                    Spacer(modifier = Modifier.height(45.dp))

                    Column(modifier = Modifier.fillMaxWidth()) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(
                                stringResource(R.string.username),
                                fontSize = 18.sp,
                                fontWeight = FontWeight.Bold,
                                fontFamily = PoppinsFamily
                            )
                            Text(username.value, fontSize = 18.sp, fontFamily = PoppinsFamily)
                        }
                        Spacer(modifier = Modifier.height(25.dp))

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(
                                stringResource(R.string.mobile),
                                fontSize = 18.sp,
                                fontWeight = FontWeight.Bold,
                                fontFamily = PoppinsFamily
                            )
                            Text(mobile.value, fontSize = 18.sp, fontFamily = PoppinsFamily)
                        }
                        Spacer(modifier = Modifier.height(25.dp))

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(
                                stringResource(R.string.email),
                                fontSize = 18.sp,
                                fontWeight = FontWeight.Bold,
                                fontFamily = PoppinsFamily
                            )
                            Text(email.value, fontSize = 18.sp, fontFamily = PoppinsFamily)
                        }
                        Spacer(modifier = Modifier.height(25.dp))

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(
                                stringResource(R.string.age),
                                fontSize = 18.sp,
                                fontWeight = FontWeight.Bold,
                                fontFamily = PoppinsFamily
                            )
                            Text(age.value, fontSize = 18.sp, fontFamily = PoppinsFamily)
                        }
                        Spacer(modifier = Modifier.height(25.dp))

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(
                                stringResource(R.string.gender),
                                fontSize = 18.sp,
                                fontWeight = FontWeight.Bold,
                                fontFamily = PoppinsFamily
                            )
                            Text(gender.value, fontSize = 18.sp, fontFamily = PoppinsFamily)
                        }
                    }
                }
            }
        })
}





@Preview(showBackground = true)
@Composable
fun PreviewRiderProfile() {
    RiderProfile(navController = rememberNavController())
}