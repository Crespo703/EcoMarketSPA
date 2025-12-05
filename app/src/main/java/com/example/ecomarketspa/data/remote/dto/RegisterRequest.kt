package com.example.ecomarketspa.data.remote.dto

data class RegisterRequest(
    val email: String,
    val password: String,
    val role: String = "CLIENTE",
    val nombre: String
)