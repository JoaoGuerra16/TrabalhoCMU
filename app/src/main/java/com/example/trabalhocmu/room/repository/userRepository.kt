package com.example.trabalhocmu.room.repository

import com.example.trabalhocmu.room.Dao.UserDao
import com.example.trabalhocmu.room.entity.User

class UserRepository(private val userDao: UserDao) {

    suspend fun insertUser(user: User) {
        userDao.insertUser(user)
    }

    suspend fun getUserByEmailAndPassword(email: String, password: String): User? {
        return userDao.getUserByEmailAndPassword(email, password)
    }
}