package com.example.ecomarketspa.repository

import com.example.ecomarketspa.data.remote.api.EcoMarketApi
import com.example.ecomarketspa.data.remote.dto.AuthResponse
import com.example.ecomarketspa.data.remote.dto.ClienteProfileDto
import com.example.ecomarketspa.data.remote.dto.LoginRequest
import com.example.ecomarketspa.data.remote.dto.RegisterRequest

class AuthRepository(private val api: EcoMarketApi) {

    suspend fun login(email: String, password: String): Result<AuthResponse> {
        return try {
            val response = api.login(LoginRequest(email, password))
            Result.success(response)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun register(
        email: String,
        password: String,
        nombre: String
    ): Result<AuthResponse> {
        return try {
            val response = api.register(
                RegisterRequest(
                    email = email,
                    password = password,
                    role = "CLIENTE",
                    nombre = nombre
                )
            )
            Result.success(response)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun getProfile(token: String): Result<ClienteProfileDto> {
        return try {
            val response = api.getProfile("Bearer $token")
            Result.success(response)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}