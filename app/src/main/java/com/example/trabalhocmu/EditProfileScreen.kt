package com.example.trabalhocmu

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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.trabalhocmu.ui.theme.PoppinsFamily
import androidx.compose.material3.Icon


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
                    .padding(top = 25.dp), // Usa o padding fornecido pela SidebarScaffold

                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                // Título Edit Profile
                Text(
                    text = "Edit Profile",
                    fontSize = 25.sp,
                    fontWeight = FontWeight.Bold,
                    fontFamily = PoppinsFamily, // Definindo a fonte personalizada
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                )

                Spacer(modifier = Modifier.height(16.dp)) // Ajuste o espaço após o título

                // Área da foto de perfil com a opção de "Mudar Foto"
                Box(contentAlignment = Alignment.BottomCenter) {
                    Image(
                        painter = painterResource(id = R.drawable.profile),
                        contentDescription = "Profile Image",
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
                            .background(Color(0x80000000)) // Cor semitransparente preta
                            .clickable {
                                // Lógica para trocar a foto
                            },
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Filled.CameraAlt,
                            contentDescription = "Change Photo",
                            tint = Color.White // Ícone branco para contraste
                        )
                    }
                }

                Spacer(modifier = Modifier.height(16.dp)) // Ajuste do espaço após a foto

                // Campos de edição de informações
                OutlinedTextField(
                    value = fullName.value,
                    shape = RoundedCornerShape(12.dp),
                    onValueChange = { fullName.value = it },
                    label = {
                        Text(
                            "Full Name",
                            fontFamily = PoppinsFamily
                        )
                    }, // Fonte aplicada ao rótulo
                    textStyle = androidx.compose.ui.text.TextStyle(fontFamily = PoppinsFamily), // Fonte aplicada ao texto digitado
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(12.dp)) // Ajuste o espaço entre os campos

                OutlinedTextField(
                    value = username.value,
                    shape = RoundedCornerShape(12.dp),
                    onValueChange = { username.value = it },
                    label = { Text("Username", fontFamily = PoppinsFamily) },
                    textStyle = androidx.compose.ui.text.TextStyle(fontFamily = PoppinsFamily),
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(12.dp))

                OutlinedTextField(
                    value = mobile.value,
                    shape = RoundedCornerShape(12.dp),
                    onValueChange = { mobile.value = it },
                    label = { Text("Mobile", fontFamily = PoppinsFamily) },
                    textStyle = androidx.compose.ui.text.TextStyle(fontFamily = PoppinsFamily),
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(12.dp))

                OutlinedTextField(
                    value = email.value,
                    shape = RoundedCornerShape(12.dp),
                    onValueChange = { email.value = it },
                    label = { Text("Email", fontFamily = PoppinsFamily) },
                    textStyle = androidx.compose.ui.text.TextStyle(fontFamily = PoppinsFamily),
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(12.dp))

                OutlinedTextField(
                    value = age.value,
                    shape = RoundedCornerShape(12.dp),
                    onValueChange = { age.value = it },
                    label = { Text("Age", fontFamily = PoppinsFamily) },
                    textStyle = androidx.compose.ui.text.TextStyle(fontFamily = PoppinsFamily),
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(12.dp))

                OutlinedTextField(
                    value = gender.value,
                    shape = RoundedCornerShape(12.dp),
                    onValueChange = { gender.value = it },
                    label = { Text("Gender", fontFamily = PoppinsFamily) },
                    textStyle = androidx.compose.ui.text.TextStyle(fontFamily = PoppinsFamily),
                    modifier = Modifier.fillMaxWidth()
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
                    Text("Save Changes", fontFamily = PoppinsFamily) // Fonte personalizada no botão
                }
            }
        })
}

@Preview(showBackground = true)
@Composable
fun PreviewEditProfileScreen() {
    EditProfileScreen(navController = rememberNavController())
}
