package com.example.ecomarketspa.data.remote.dto

data class CategoryDto(
    val _id: String,
    val nombre: String,
    val descripcion: String? = null,
    val imagen: String? = null,
    val imagenThumbnail: String? = null,
    val isActive: Boolean = true,
    val createdAt: String? = null,
    val updatedAt: String? = null
)