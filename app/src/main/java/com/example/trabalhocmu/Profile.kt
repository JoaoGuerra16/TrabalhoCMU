package com.example.trabalhocmu

import RatingViewModel
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Create
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.ModeEdit
import androidx.compose.material.icons.filled.StarRate
import androidx.compose.material.icons.outlined.StarRate
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.draw.clip
import androidx.navigation.compose.rememberNavController
import kotlin.math.roundToInt
import com.example.trabalhocmu.ui.theme.PoppinsFamily

@Composable
fun Profile(
    navController: NavController,
    ratingViewModel: RatingViewModel = viewModel()
) {
    val scrollState = rememberScrollState()
    val currentLanguage = remember { mutableStateOf("PT") }
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
                        .padding(top = 10.dp),  // Ajuste do padding superior
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {

                    // Reduzir o espaçamento entre a top bar e o título
                    Spacer(modifier = Modifier.height(16.dp))

                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center,
                        modifier = Modifier.padding(start = 35.dp)
                    ) {
                        Text(
                            text = "Profile",
                            fontSize = 25.sp,
                            fontWeight = FontWeight.Bold,
                            fontFamily = PoppinsFamily  // Aplica a fonte Poppins
                        )
                        IconButton(onClick = {
                            navController.navigate("EditProfile")
                        }) {
                            Icon(
                                imageVector = Icons.Filled.ModeEdit,
                                contentDescription = "Edit Profile"
                            )
                        }
                    }
                    Spacer(modifier = Modifier.height(16.dp))  // Ajuste do espaço após o título

                    // Foto de perfil
                    Image(
                        painter = painterResource(id = R.drawable.profile),
                        contentDescription = "Profile Image",
                        modifier = Modifier
                            .size(100.dp)
                            .clip(CircleShape)
                    )
                    Spacer(modifier = Modifier.height(25.dp))

                    // Nome completo centralizado
                    Text(
                        text = fullName.value,
                        fontSize = 22.sp,
                        fontWeight = FontWeight.Bold,
                        fontFamily = PoppinsFamily,  // Aplica a fonte Poppins
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.Center
                    )
                    Spacer(modifier = Modifier.height(10.dp))

                    // Estrelas de Avaliação
                    RatingStars(rating = roundedRating)   // Começa com zero estrelas preenchidas
                    Spacer(modifier = Modifier.height(45.dp))

                    Column(modifier = Modifier.fillMaxWidth()) {
                        // Campos de perfil, com layout de espaçamento existente
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text("Username:", fontSize = 18.sp, fontWeight = FontWeight.Bold, fontFamily = PoppinsFamily)  // Aplica a fonte Poppins
                            Text(username.value, fontSize = 18.sp, fontFamily = PoppinsFamily)  // Aplica a fonte Poppins
                        }
                        Spacer(modifier = Modifier.height(25.dp))

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text("Mobile:", fontSize = 18.sp, fontWeight = FontWeight.Bold, fontFamily = PoppinsFamily)  // Aplica a fonte Poppins
                            Text(mobile.value, fontSize = 18.sp, fontFamily = PoppinsFamily)  // Aplica a fonte Poppins
                        }
                        Spacer(modifier = Modifier.height(25.dp))

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text("Email:", fontSize = 18.sp, fontWeight = FontWeight.Bold, fontFamily = PoppinsFamily)  // Aplica a fonte Poppins
                            Text(email.value, fontSize = 18.sp, fontFamily = PoppinsFamily)  // Aplica a fonte Poppins
                        }
                        Spacer(modifier = Modifier.height(25.dp))

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text("Age:", fontSize = 18.sp, fontWeight = FontWeight.Bold, fontFamily = PoppinsFamily)  // Aplica a fonte Poppins
                            Text(age.value, fontSize = 18.sp, fontFamily = PoppinsFamily)  // Aplica a fonte Poppins
                        }
                        Spacer(modifier = Modifier.height(25.dp))

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text("Gender:", fontSize = 18.sp, fontWeight = FontWeight.Bold, fontFamily = PoppinsFamily)  // Aplica a fonte Poppins
                            Text(gender.value, fontSize = 18.sp, fontFamily = PoppinsFamily)  // Aplica a fonte Poppins
                        }
                    }
                }

                Row(
                    modifier = Modifier
                        .align(Alignment.BottomEnd)
                        .padding(16.dp)
                        .clickable {
                            currentLanguage.value =
                                if (currentLanguage.value == "PT") "ENG" else "PT"
                        },
                    verticalAlignment = Alignment.CenterVertically
                ) {

                }

            }
        })
}

@Composable
fun RatingStars(rating: Int) {
    Row {
        for (i in 1..5) {
            Icon(
                imageVector = if (i <= rating) Icons.Filled.StarRate else Icons.Outlined.StarRate,
                contentDescription = "Star $i",
                modifier = Modifier.size(35.dp),
                tint = Color(0xFFFFC107)
            )
        }
    }
}




@Preview(showBackground = true)
@Composable
fun PreviewProfile() {
    Profile(navController = rememberNavController())
}
