import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.trabalhocmu.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditProfileScreen(
    navController: NavController,
    fullName: MutableState<String>,
    username: MutableState<String>,
    mobile: MutableState<String>,
    email: MutableState<String>,
    age: MutableState<String>,
    gender: MutableState<String>
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Edit Profile",
            fontSize = 25.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.align(Alignment.Start)
        )

        Spacer(modifier = Modifier.height(20.dp))

        // Área da foto de perfil com a opção de "Mudar Foto"
        Box(contentAlignment = Alignment.BottomCenter) {
            Image(
                painter = painterResource(id = R.drawable.profile),
                contentDescription = "Profile Image",
                modifier = Modifier
                    .size(100.dp)
                    .clip(CircleShape)
            )
            Text(
                text = "Mudar Foto",
                color = Color.Blue,
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .clickable {
                        // Aqui você poderá adicionar a lógica para trocar a foto
                    }
            )
        }

        Spacer(modifier = Modifier.height(30.dp))

        // Campos de edição de informações
        OutlinedTextField(
            value = fullName.value,
            onValueChange = { fullName.value = it },
            label = { Text("Full Name") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(20.dp))

        OutlinedTextField(
            value = username.value,
            onValueChange = { username.value = it },
            label = { Text("Username") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(20.dp))

        OutlinedTextField(
            value = mobile.value,
            onValueChange = { mobile.value = it },
            label = { Text("Mobile") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(20.dp))

        OutlinedTextField(
            value = email.value,
            onValueChange = { email.value = it },
            label = { Text("Email") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(20.dp))

        OutlinedTextField(
            value = age.value,
            onValueChange = { age.value = it },
            label = { Text("Age") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(20.dp))

        OutlinedTextField(
            value = gender.value,
            onValueChange = { gender.value = it },
            label = { Text("Gender") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(30.dp))

        Button(
            onClick = {
                // Salvar as alterações e navegar de volta ao perfil
                navController.popBackStack()
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Save Changes")
        }
    }
}
