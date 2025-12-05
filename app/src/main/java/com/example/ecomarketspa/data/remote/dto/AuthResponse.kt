package com.example.ecomarketspa.data.remote.dto

data class AuthResponse(
    val user: UserDto,
    val access_token: String
)