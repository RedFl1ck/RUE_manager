package com.example.reumanager.data.model

data class RegisterApi(
    val name: String,
    val surname: String,
    val middleName: String,
    val birthday: String,
    val email: String,
    val password: String,
    val educationInfo: String,
    val experience: String,
    val careerObjective: String
)