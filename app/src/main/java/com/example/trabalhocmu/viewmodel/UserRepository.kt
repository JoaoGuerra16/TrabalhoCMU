package com.example.trabalhocmu.room.entity

class UserRepository(private val userDao: UserDao) {

    // Função para inserir um usuário
    suspend fun insertUser(user: User) {
        userDao.insertUser(user)
    }

    // Função para obter todos os usuários
    suspend fun getAllUsers(): List<User> {
        return userDao.getAllUsers()
    }

    // Função para login
    suspend fun login(email: String, password: String): User? {
        return userDao.getUserByEmailAndPassword(email, password)
    }
}
