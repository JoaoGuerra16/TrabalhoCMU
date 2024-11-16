package com.example.trabalhocmu

import RatingViewModel
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.rememberNavController
import com.example.trabalhocmu.R

@Composable
fun RateScreen(navController: NavController, ratingViewModel: RatingViewModel = viewModel()) {
    var rating by remember { mutableStateOf(0) }

    SidebarScaffold(
        navController = navController,
        content = { padding ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(top = 10.dp)
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(top = 80.dp) // Ajuste o valor para aproximar a imagem do topo
                ) {
                    // Imagem do perfil
                    Image(
                        painter = painterResource(id = R.drawable.profile),
                        contentDescription = "Profile Image",
                        modifier = Modifier
                            .size(100.dp)
                            .clip(CircleShape)
                            .align(Alignment.CenterHorizontally) // Centraliza horizontalmente
                    )

                    Spacer(modifier = Modifier.height(65.dp))

                    // Centraliza o restante do conteúdo
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier.fillMaxHeight()
                    ) {
                        Text(
                            text = "How would you rate this user?",
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(bottom = 16.dp)
                        )

                        // Estrelas interativas
                        Row {
                            for (i in 1..5) {
                                Icon(
                                    imageVector = if (i <= rating) Icons.Filled.StarRate else Icons.Outlined.StarRate,
                                    contentDescription = "Star $i",
                                    modifier = Modifier
                                        .size(40.dp)
                                        .clickable {
                                            rating = i
                                        },
                                    tint = Color(0xFFFFC107)
                                )
                            }
                        }

                        Spacer(modifier = Modifier.height(20.dp))

                        Text(
                            text = "Rating: $rating Star${if (rating > 1) "s" else ""}",
                            fontSize = 18.sp
                        )
                        Spacer(modifier = Modifier.height(20.dp))

                        // Botão para adicionar a avaliação
                        Button(
                            onClick = {
                                ratingViewModel.addRating(rating)  // Adiciona a avaliação ao ViewModel
                                navController.navigate("Profile")  // Navega para o Profile para ver o resultado
                            },
                            colors = ButtonDefaults.buttonColors(
                                containerColor = Color(0xFF4CAF50) // Define a cor do botão
                            )
                        ) {
                            Text("Submit Rating")
                        }
                    }
                }
            }
        }
    )
}


@Preview(showBackground = true)
@Composable
fun PreviewRateScreen() {
    RateScreen(navController = rememberNavController())
}
