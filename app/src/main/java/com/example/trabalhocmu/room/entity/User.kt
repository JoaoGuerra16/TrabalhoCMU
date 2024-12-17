package com.example.trabalhocmu.room.entity

import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey

@Entity(tableName = "user_table")
data class User(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val name: String,
    val username: String,
    val email: String,
    val age: String,
    val gender: String,
    val mobileNumber: String,
    val password: String
){
    @Ignore // Para o Room, esse construtor não será usado
    constructor() : this(0, "", "", "", "", "", "", "")
}