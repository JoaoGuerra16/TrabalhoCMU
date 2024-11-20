package com.example.trabalhocmu.room.Dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.trabalhocmu.room.entity.User

@Dao
interface UserDao {
    @Insert
    suspend fun insertUser(user: User)

    @Query("SELECT * FROM user_table WHERE email = :email")
    suspend fun getUserByEmail(email: String): User?
}
