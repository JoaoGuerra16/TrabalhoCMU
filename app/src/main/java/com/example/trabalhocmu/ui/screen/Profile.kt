package com.example.trabalhocmu.ui.screen

import com.example.trabalhocmu.viewmodel.RatingViewModel
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ModeEdit
import androidx.compose.material.icons.filled.StarRate
import androidx.compose.material.icons.outlined.StarRate
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.rememberNavController
import com.example.trabalhocmu.R
import com.example.trabalhocmu.room.entity.User
import com.example.trabalhocmu.ui.component.SidebarScaffold
import kotlin.math.roundToInt
import com.example.trabalhocmu.ui.theme.PoppinsFamily
import com.example.trabalhocmu.viewmodel.AuthViewModel

@Composable
fun Profile(
    navController: NavController,
    authViewModel: AuthViewModel = viewModel(),
    ratingViewModel: RatingViewModel = viewModel()
) {
    val user by authViewModel.userData.collectAsState()  // Coleta os dados do usuário
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
                    ProfileTitle(navController)
                    ProfileImage()
                    if (user != null) {
                        ProfileName(user!!.name)
                        RatingStars(rating = roundedRating)
                        ProfileDetails(user!!)
                    } else {
                        // Caso o usuário não esteja disponível, mostrar mensagem
                        Text(stringResource(id = R.string.carregar_dados), fontSize = 18.sp)
                    }
                }
            }
        }
    )
}

@Composable
fun ProfileTitle(navController: NavController) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center,
        modifier = Modifier.padding(start = 35.dp)
    ) {
        Text(
            text = stringResource(id = R.string.profile_title),
            fontSize = 25.sp,
            fontWeight = FontWeight.Bold,
            fontFamily = PoppinsFamily
        )
        IconButton(onClick = {
            navController.navigate("EditProfile")
        }) {
            Icon(
                imageVector = Icons.Filled.ModeEdit,
                contentDescription = stringResource(R.string.edit_profile)
            )
        }
    }
}

@Composable
fun ProfileImage() {
    Image(
        painter = painterResource(id = R.drawable.profile),
        contentDescription = stringResource(R.string.profile_image_desc),
        modifier = Modifier
            .size(100.dp)
            .clip(CircleShape)
    )
}

@Composable
fun ProfileName(fullName: String) {
    Spacer(modifier = Modifier.height(25.dp))
    Text(
        text = fullName,
        fontSize = 22.sp,
        fontWeight = FontWeight.Bold,
        fontFamily = PoppinsFamily,
        modifier = Modifier.fillMaxWidth(),
        textAlign = TextAlign.Center
    )
}

@Composable
fun ProfileDetails(user: User) {
    Spacer(modifier = Modifier.height(45.dp))

    Column(modifier = Modifier.fillMaxWidth()) {
        ProfileDetailRow(label = stringResource(R.string.username), value = user.username)
        Spacer(modifier = Modifier.height(25.dp))

        ProfileDetailRow(label = stringResource(R.string.mobile), value = user.mobileNumber)
        Spacer(modifier = Modifier.height(25.dp))

        ProfileDetailRow(label = stringResource(R.string.email), value = user.email)
        Spacer(modifier = Modifier.height(25.dp))

        ProfileDetailRow(label = stringResource(R.string.age), value = user.age)
        Spacer(modifier = Modifier.height(25.dp))

        ProfileDetailRow(label = stringResource(R.string.gender), value = user.gender)
    }
}

@Composable
fun ProfileDetailRow(label: String, value: String) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = label,
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            fontFamily = PoppinsFamily
        )
        Text(
            text = value,
            fontSize = 18.sp,
            fontFamily = PoppinsFamily
        )
    }
}

@Composable
fun RatingStars(rating: Int) {
    Row {
        for (i in 1..5) {
            Icon(
                imageVector = if (i <= rating) Icons.Filled.StarRate else Icons.Outlined.StarRate,
                contentDescription = null,
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
