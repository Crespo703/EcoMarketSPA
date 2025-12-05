package com.example.ecomarketspa.data.remote.dto

data class ProductDto(
    val _id: String,
    val nombre: String,
    val descripcion: String,
    val precio: Double,
    val unidad: String? = "unidad",
    val stock: Int,
    val imagen: String? = null,
    val imagenThumbnail: String? = null,
    val categoria: String? = null,
    val isActive: Boolean = true,
    val createdAt: String? = null,
    val updatedAt: String? = null
)