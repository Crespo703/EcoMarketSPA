package com.example.ecomarketspa.data.remote.dto

data class PedidoRequest(
    val productos: List<ProductoPedido>,
    val total: Double
)

data class ProductoPedido(
    val producto: String,
    val cantidad: Int,
    val precioUnitario: Double
)