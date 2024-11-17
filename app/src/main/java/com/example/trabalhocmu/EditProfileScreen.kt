package com.example.trabalhocmu

import LanguageViewModel
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CameraAlt
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.trabalhocmu.ui.theme.PoppinsFamily

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditProfileScreen(
    navController: NavController
) {
    val fullName = remember { mutableStateOf("John Doe") }
    val username = remember { mutableStateOf("JohnDoe") }
    val mobile = remember { mutableStateOf("+1234567890") }
    val email = remember { mutableStateOf("john.doe@example.com") }
    val age = remember { mutableStateOf("30") }
    val gender = remember { mutableStateOf("Male") }

    SidebarScaffold(
        navController = navController,

        content = { padding ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(15.dp)
                    .padding(top = 25.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // Título Edit Profile
                Text(
                    text = stringResource(R.string.edit_profile_title),
                    fontSize = 25.sp,
                    fontWeight = FontWeight.Bold,
                    fontFamily = PoppinsFamily,
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                )

                Spacer(modifier = Modifier.height(16.dp))

                // Área da foto de perfil com a opção de "Mudar Foto"
                Box(contentAlignment = Alignment.BottomCenter) {
                    Image(
                        painter = painterResource(id = R.drawable.profile),
                        contentDescription = stringResource(R.string.profile_image_desc),
                        modifier = Modifier
                            .size(100.dp)
                            .clip(CircleShape)
                    )

                    // Ícone de câmera com fundo semitransparente
                    Box(
                        modifier = Modifier
                            .align(Alignment.BottomCenter)
                            .padding(bottom = 10.dp)
                            .size(50.dp)
                            .clip(CircleShape)
                            .background(Color(0x80000000))
                            .clickable {
                                // Lógica para trocar a foto
                            },
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Filled.CameraAlt,
                            contentDescription = stringResource(R.string.change_photo),
                            tint = Color.White
                        )
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Campos de edição de informações
                ProfileTextField(
                    value = fullName.value,
                    onValueChange = { fullName.value = it },
                    label = stringResource(R.string.full_name)
                )
                Spacer(modifier = Modifier.height(12.dp))

                ProfileTextField(
                    value = username.value,
                    onValueChange = { username.value = it },
                    label = stringResource(R.string.username)
                )
                Spacer(modifier = Modifier.height(12.dp))

                ProfileTextField(
                    value = mobile.value,
                    onValueChange = { mobile.value = it },
                    label = stringResource(R.string.mobile)
                )
                Spacer(modifier = Modifier.height(12.dp))

                ProfileTextField(
                    value = email.value,
                    onValueChange = { email.value = it },
                    label = stringResource(R.string.email)
                )
                Spacer(modifier = Modifier.height(12.dp))

                ProfileTextField(
                    value = age.value,
                    onValueChange = { age.value = it },
                    label = stringResource(R.string.age)
                )
                Spacer(modifier = Modifier.height(12.dp))

                ProfileTextField(
                    value = gender.value,
                    onValueChange = { gender.value = it },
                    label = stringResource(R.string.gender)
                )
                Spacer(modifier = Modifier.height(20.dp))

                // Botão para salvar as alterações
                Button(
                    onClick = {
                        // Salvar as alterações e navegar de volta ao perfil
                        navController.popBackStack()
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFF4CAF50)
                    )
                ) {
                    Text(stringResource(R.string.save_changes), fontFamily = PoppinsFamily)
                }
            }
        })
}

@Composable
fun ProfileTextField(
    value: String,
    onValueChange: (String) -> Unit,
    label: String
) {
    OutlinedTextField(
        value = value,
        shape = RoundedCornerShape(12.dp),
        onValueChange = onValueChange,
        label = { Text(label, fontFamily = PoppinsFamily) },
        textStyle = androidx.compose.ui.text.TextStyle(fontFamily = PoppinsFamily),
        modifier = Modifier.fillMaxWidth()
    )
}

@Preview(showBackground = true)
@Composable
fun PreviewEditProfileScreen() {
    EditProfileScreen(navController = rememberNavController())
}
