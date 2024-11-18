package com.example.trabalhocmu.room.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user_table")
data class User(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val name: String,
    val username: String,
    val email: String,
    val age: Int,
    val gender: String,
    val mobileNumber: String,
    val password: String
)