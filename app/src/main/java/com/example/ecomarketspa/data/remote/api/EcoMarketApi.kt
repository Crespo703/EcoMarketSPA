package com.example.ecomarketspa.data.remote.api

import com.example.ecomarketspa.data.remote.dto.*
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*

interface EcoMarketApi {

    @POST("api/auth/register")
    suspend fun register(@Body request: RegisterRequest): AuthResponse

    @POST("api/auth/login")
    suspend fun login(@Body request: LoginRequest): AuthResponse

    @GET("api/cliente-profile/me")
    suspend fun getProfile(@Header("Authorization") token: String): ClienteProfileDto

    @GET("api/producto")
    suspend fun getProducts(): List<ProductDto>

    @GET("api/categoria")
    suspend fun getCategories(): List<CategoryDto>

    @POST("api/pedido")
    suspend fun createPedido(
        @Header("Authorization") token: String,
        @Body request: PedidoRequest
    ): PedidoResponse

    @POST("api/pago")
    suspend fun createPago(
        @Header("Authorization") token: String,
        @Body request: PagoRequest
    ): PagoResponse

    companion object {
        private const val BASE_URL = "https://ecomarket-api-1.onrender.com/"

        fun create(): EcoMarketApi {
            return Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(EcoMarketApi::class.java)
        }
    }
}