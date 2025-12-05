package com.example.ecomarketspa.data.remote.dto

data class ClienteProfileDto(
    val _id: String? = null,
    val user: String? = null,
    val nombre: String,
    val telefono: String? = null,
    val direccion: String? = null,
    val createdAt: String? = null,
    val updatedAt: String? = null
)