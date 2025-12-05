package com.example.ecomarketspa.data.remote.dto

data class UserDto(
    val _id: String? = null,
    val email: String,
    val role: String,
    val isActive: Boolean = true,
    val emailVerified: Boolean = false,
    val createdAt: String? = null,
    val updatedAt: String? = null
)