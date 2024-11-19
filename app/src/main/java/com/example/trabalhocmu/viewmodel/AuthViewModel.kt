import android.widget.Toast
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.trabalhocmu.room.entity.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.launch
import androidx.compose.ui.platform.LocalContext
import org.mindrot.jbcrypt.BCrypt

class AuthViewModel : ViewModel() {
    var name = mutableStateOf("")
    var username = mutableStateOf("")
    var email = mutableStateOf("")
    var age = mutableStateOf(0)
    var gender = mutableStateOf("")
    var mobileNumber = mutableStateOf("")
    var password = mutableStateOf("")
    var confirmPassword = mutableStateOf("")

    var passwordVisible = mutableStateOf(false)
    var confirmPasswordVisible = mutableStateOf(false)

    private val auth = FirebaseAuth.getInstance()
    private val firestore = FirebaseFirestore.getInstance()

    // Função para registrar o usuário
    fun registerUser(context: android.content.Context, navController: androidx.navigation.NavController) {
        if (name.value.isBlank() || username.value.isBlank() || email.value.isBlank() || password.value.isBlank() || confirmPassword.value.isBlank()) {
            Toast.makeText(context, "Todos os campos devem ser preenchidos!", Toast.LENGTH_SHORT).show()
            return
        }

        if (password.value != confirmPassword.value) {
            Toast.makeText(context, "As senhas não coincidem.", Toast.LENGTH_SHORT).show()
            return
        }

        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email.value).matches()) {
            Toast.makeText(context, "Por favor, insira um email válido.", Toast.LENGTH_SHORT).show()
            return
        }

        // Gerar o hash da senha antes de salvar
        val hashedPassword = hashPassword(password.value)

        // Criar usuário no Firebase Authentication
        auth.createUserWithEmailAndPassword(email.value, password.value)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val user = User(
                        name = name.value,
                        username = username.value,
                        email = email.value,
                        age = age.value,
                        gender = gender.value,
                        mobileNumber = mobileNumber.value,
                        password = hashedPassword // Salvando a senha com hash
                    )

                    // Salvar no Firestore
                    saveUserToFirestore(user, context)

                    // Navegar para a tela de Login após o sucesso
                    navController.navigate("Login")
                } else {
                    // Exibir erro caso o registro falhe
                    Toast.makeText(context, "Erro ao criar conta: ${task.exception?.message}", Toast.LENGTH_SHORT).show()
                }
            }
    }

    // Função para gerar o hash da senha usando bcrypt
    private fun hashPassword(password: String): String {
        return BCrypt.hashpw(password, BCrypt.gensalt())  // Gera o hash da senha com o salt
    }

    private fun saveUserToFirestore(user: User, context: android.content.Context) {
        val userRef = firestore.collection("users").document(auth.currentUser?.uid ?: "")
        userRef.set(user)
            .addOnSuccessListener {
                // Sucesso ao salvar no Firestore
            }
            .addOnFailureListener { e ->
                // Lidar com falha ao salvar no Firestore
                Toast.makeText(context, "Erro ao salvar no Firestore: ${e.message}", Toast.LENGTH_SHORT).show()
            }
    }
}
