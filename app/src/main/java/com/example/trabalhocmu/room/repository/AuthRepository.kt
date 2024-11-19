package com.example.trabalhocmu.room.repository

import android.content.Context
import com.example.trabalhocmu.room.entity.AppDatabase
import com.example.trabalhocmu.room.entity.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.firebase.auth.GoogleAuthProvider
import kotlinx.coroutines.tasks.await

class AuthRepository(private val context: Context) {

    private val auth = FirebaseAuth.getInstance()
    private val firestore = FirebaseFirestore.getInstance()

    // Instância do banco de dados Room
    private val db = AppDatabase.getDatabase(context)

    // Função para fazer login com email e senha
    suspend fun loginUser(email: String, password: String): Boolean {
        return try {
            val result = auth.signInWithEmailAndPassword(email, password).await()
            result.user != null
        } catch (e: Exception) {
            false
        }
    }

    // Função para login com Google
    suspend fun loginWithGoogle(account: GoogleSignInAccount): Boolean {
        return try {
            val credential = GoogleAuthProvider.getCredential(account.idToken, null)
            val result = auth.signInWithCredential(credential).await()
            result.user != null
        } catch (e: Exception) {
            false
        }
    }

    // Função para registrar um usuário
    suspend fun registerUser(
        name: String,
        username: String,
        email: String,
        password: String,
        age: String,
        gender: String,
        mobileNumber: String
    ): Boolean {
        try {
            // Verificar se o usuário já existe no Firebase
            val result = auth.fetchSignInMethodsForEmail(email).await()
            if (result.signInMethods?.isNotEmpty() == true) {
                // Se o e-mail já está registrado no Firebase, retorne falso
                return false
            }

            // Verificar se o usuário já existe no banco local (Room)
            val existingUser = getUserByEmail(email)
            if (existingUser != null) {
                // Se o usuário já existe no Room, retorne falso
                return false
            }

            // Criar o usuário no Firebase Authentication
            val authResult = auth.createUserWithEmailAndPassword(email, password).await()
            val userId = authResult.user?.uid ?: return false

            // Criar o objeto User com todos os campos e salvar no Firestore
            val user = User(
                email = email,
                name = name,
                username = username,
                password = password,
                age = age,
                gender = gender,
                mobileNumber = mobileNumber
            )

            // Salvar o usuário no Firestore
            firestore.collection("users").document(userId).set(user).await()

            // Salvar o usuário também no Room (local)
            insertUser(user)

            return true
        } catch (e: Exception) {
            return false
        }
    }



    // Função para buscar o usuário no banco local (Room)
    suspend fun getUserByEmail(email: String): User? {
        return db.userDao().getUserByEmail(email)
    }

    // Função para inserir o usuário no banco de dados local (Room)
    suspend fun insertUser(user: User) {
        db.userDao().insertUser(user)
    }
}
