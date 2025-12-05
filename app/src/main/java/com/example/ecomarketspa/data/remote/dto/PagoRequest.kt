package com.example.ecomarketspa.data.remote.dto

data class PagoRequest(
    val pedido: String,
    val monto: Double,
    val metodoPago: String = "SIMULADO"
)

data class PedidoResponse(
    val _id: String,
    val cliente: String,
    val productos: List<ProductoPedido>,
    val total: Double,
    val estado: String = "PENDIENTE",
    val createdAt: String? = null,
    val updatedAt: String? = null
)

data class PagoResponse(
    val _id: String,
    val pedido: String,
    val monto: Double,
    val metodoPago: String,
    val estado: String = "COMPLETADO",
    val createdAt: String? = null,
    val updatedAt: String? = null
)