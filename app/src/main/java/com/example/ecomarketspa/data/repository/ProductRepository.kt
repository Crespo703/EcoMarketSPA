package com.example.ecomarketspa.repository

import com.example.ecomarketspa.data.remote.api.EcoMarketApi
import com.example.ecomarketspa.data.remote.dto.CategoryDto
import com.example.ecomarketspa.data.remote.dto.ProductDto

class ProductRepository(private val api: EcoMarketApi) {

    suspend fun getProducts(): Result<List<ProductDto>> {
        return try {
            val products = api.getProducts()
            Result.success(products)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun getCategories(): Result<List<CategoryDto>> {
        return try {
            val categories = api.getCategories()
            Result.success(categories)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}