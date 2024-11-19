import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.compose.runtime.mutableStateOf
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.firestore.FirebaseFirestore
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignIn
import kotlinx.coroutines.launch
import org.mindrot.jbcrypt.BCrypt

class LoginViewModel : ViewModel() {

    val email = mutableStateOf("")
    val password = mutableStateOf("")
    val passwordVisible = mutableStateOf(false)
    val loginError = mutableStateOf(false)

    private val auth = FirebaseAuth.getInstance()
    private val firestore = FirebaseFirestore.getInstance()

    // Função para verificar se a senha fornecida corresponde ao hash
    fun checkPassword(inputPassword: String, storedHashedPassword: String): Boolean {
        return BCrypt.checkpw(inputPassword, storedHashedPassword)  // Verifica se a senha corresponde ao hash
    }

    // Função para login com email e senha
    fun loginUser(email: String, password: String, onSuccess: () -> Unit, onFailure: (String) -> Unit) {
        firestore.collection("users")
            .whereEqualTo("email", email)
            .get()
            .addOnSuccessListener { result ->
                if (result.isEmpty) {
                    onFailure("Email ou senha inválidos")
                    return@addOnSuccessListener
                }

                val userDocument = result.documents.first()
                val storedHashedPassword = userDocument.getString("password") ?: ""

                if (checkPassword(password, storedHashedPassword)) {
                    // Senha correta, login bem-sucedido
                    onSuccess()
                } else {
                    onFailure("Email ou senha inválidos")
                }
            }
            .addOnFailureListener {
                onFailure("Erro ao buscar usuário")
            }
    }

    // Função para login com Google
    fun firebaseAuthWithGoogle(account: GoogleSignInAccount, onSuccess: () -> Unit, onFailure: (String) -> Unit) {
        val credential = GoogleAuthProvider.getCredential(account.idToken, null)
        auth.signInWithCredential(credential)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    onSuccess()
                } else {
                    onFailure("Erro ao autenticar com Google")
                }
            }
    }
}
