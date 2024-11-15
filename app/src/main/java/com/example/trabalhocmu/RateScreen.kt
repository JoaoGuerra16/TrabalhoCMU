import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Create
import androidx.compose.material.icons.filled.StarRate
import androidx.compose.material.icons.outlined.StarRate
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import androidx.lifecycle.viewmodel.compose.viewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RateScreen(navController: NavController, ratingViewModel: RatingViewModel = viewModel()) {
    var rating by remember { mutableStateOf(0) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Rate User") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Back"
                        )
                    }
                }
            )
        },
        content = { padding ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding),
                contentAlignment = Alignment.Center
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
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
                                tint = Color.Yellow
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(20.dp))

                    Text(
                        text = "Rating: $rating Star${if (rating > 1) "s" else ""}",
                        fontSize = 18.sp
                    )

                    // Botão para adicionar a avaliação
                    Button(onClick = {
                        ratingViewModel.addRating(rating)  // Adiciona a avaliação ao ViewModel
                        navController.navigate("Profile")  // Navega para o Profile para ver o resultado
                    }) {
                        Text("Submit Rating")
                    }
                }
            }
        }
    )
}
