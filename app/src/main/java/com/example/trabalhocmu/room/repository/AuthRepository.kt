package com.example.trabalhocmu.room.repository

import com.example.trabalhocmu.room.entity.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.firestore.FirebaseFirestore
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import kotlinx.coroutines.tasks.await


class AuthRepository (){
    private val auth = FirebaseAuth.getInstance()
    private val firestore = FirebaseFirestore.getInstance()

    // Função para fazer login com email e senha
    suspend fun loginUser(email: String, password: String): Boolean {
        return try {
            val result = auth.signInWithEmailAndPassword(email, password).await()
            result.user != null
        } catch (e: Exception) {
            false
        }
    }

    suspend fun loginWithGoogle(account: GoogleSignInAccount): Boolean {
        return try {
            val credential = GoogleAuthProvider.getCredential(account.idToken, null)
            val result = auth.signInWithCredential(credential).await()
            result.user != null
        } catch (e: Exception) {
            false
        }
    }

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
            // Criação do usuário no Firebase Authentication
            val result = auth.createUserWithEmailAndPassword(email, password).await()
            val userId = result.user?.uid ?: return false

            // Criar o objeto User com todos os campos e salvar no Firestore
            val user = User(
                name = name,
                username = username,
                email = email,
                age = age,
                gender = gender,
                mobileNumber = mobileNumber,
                password = password // Você pode optar por hash a senha aqui, se necessário
            )

            firestore.collection("users").document(userId).set(user).await()

            return true
        } catch (e: Exception) {
            return false
        }
    }
}