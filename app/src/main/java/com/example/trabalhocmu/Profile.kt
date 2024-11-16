package com.example.trabalhocmu

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Create
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.compose.material.icons.filled.Menu
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.material.icons.filled.StarRate
import androidx.compose.material.icons.outlined.StarRate
import androidx.lifecycle.viewmodel.compose.viewModel
import kotlin.math.roundToInt


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Profile(
    navController: NavController) {
    val fullName = remember { mutableStateOf("John Doe") }
    val username = remember { mutableStateOf("JohnDoe") }
    val mobile = remember { mutableStateOf("+1234567890") }
    val email = remember { mutableStateOf("john.doe@example.com") }
    val age = remember { mutableStateOf("30") }
    val gender = remember { mutableStateOf("Male") }
    val currentLanguage = remember { mutableStateOf("PT") }
    val scrollState = rememberScrollState()


    TopAppBar(
        title = { },
        navigationIcon = {
            IconButton(onClick = { /* Ação para o botão de menu */ }) {
                Icon(
                    imageVector = Icons.Filled.Menu,
                    contentDescription = "Open Menu"
                )
            }
        }
    )

    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(scrollState)
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(id = R.drawable.logo),
                contentDescription = "Logo",
                modifier = Modifier.size(65.dp)
            )
            Spacer(modifier = Modifier.height(10.dp))
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier.padding(start = 35.dp)
            ) {
                Text(
                    text = "Ride info",
                    fontSize = 25.sp,
                    fontWeight = FontWeight.Bold,
                )
                IconButton(onClick = {
                    navController.navigate("edit_profile")
                }) {
                    Icon(
                        imageVector = Icons.Filled.Create,
                        contentDescription = "Edit Profile"
                    )
                }
            }
            Spacer(modifier = Modifier.height(20.dp))
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
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.height(10.dp))

            // Estrelas de Avaliação
            RatingStars(rating = 0)   // Começa com zero estrelas preenchidas
            Spacer(modifier = Modifier.height(45.dp))

            Column(modifier = Modifier.fillMaxWidth()) {
                // Campos de perfil, com layout de espaçamento existente
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text("Username:", fontSize = 18.sp, fontWeight = FontWeight.Bold)
                    Text(username.value, fontSize = 18.sp)
                }
                Spacer(modifier = Modifier.height(25.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text("Mobile:", fontSize = 18.sp, fontWeight = FontWeight.Bold)
                    Text(mobile.value, fontSize = 18.sp)
                }
                Spacer(modifier = Modifier.height(25.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text("Email:", fontSize = 18.sp, fontWeight = FontWeight.Bold)
                    Text(email.value, fontSize = 18.sp)
                }
                Spacer(modifier = Modifier.height(25.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text("Age:", fontSize = 18.sp, fontWeight = FontWeight.Bold)
                    Text(age.value, fontSize = 18.sp)
                }
                Spacer(modifier = Modifier.height(25.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text("Gender:", fontSize = 18.sp, fontWeight = FontWeight.Bold)
                    Text(gender.value, fontSize = 18.sp)
                }
            }
        }

        Row(
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(16.dp)
                .clickable {
                    currentLanguage.value = if (currentLanguage.value == "PT") "ENG" else "PT"
                },
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = if (currentLanguage.value == "ENG") "ENG | PT" else "ENG | PT ",
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold
            )
        }
    }
}

// Composable para exibir as estrelas de avaliação
@Composable
fun RatingStars(rating: Int) {
    Row {
        for (i in 1..5) {
            Icon(
                imageVector = if (i <= rating) Icons.Filled.StarRate else Icons.Outlined.StarRate,
                contentDescription = "Star $i",
                tint = Color(0xFFFFEB3B),
                modifier = Modifier.size(40.dp)
            )
        }
    }
}
