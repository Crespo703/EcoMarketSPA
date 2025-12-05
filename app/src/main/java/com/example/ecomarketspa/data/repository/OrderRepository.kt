package com.example.ecomarketspa.repository

import com.example.ecomarketspa.data.remote.api.EcoMarketApi
import com.example.ecomarketspa.data.remote.dto.PagoRequest
import com.example.ecomarketspa.data.remote.dto.PagoResponse
import com.example.ecomarketspa.data.remote.dto.PedidoRequest
import com.example.ecomarketspa.data.remote.dto.PedidoResponse

class OrderRepository(private val api: EcoMarketApi) {

    suspend fun createPedido(token: String, request: PedidoRequest): Result<PedidoResponse> {
        return try {
            val response = api.createPedido("Bearer $token", request)
            Result.success(response)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun createPago(token: String, request: PagoRequest): Result<PagoResponse> {
        return try {
            val response = api.createPago("Bearer $token", request)
            Result.success(response)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}