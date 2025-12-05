package com.example.ecomarketspa.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ecomarketspa.data.local.SessionManager
import com.example.ecomarketspa.data.remote.dto.ProductDto
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class ProductDetailViewModel(private val sessionManager: SessionManager) : ViewModel() {

    private val _addedToCart = MutableStateFlow(false)
    val addedToCart: StateFlow<Boolean> = _addedToCart

    fun addToCart(product: ProductDto, quantity: Int = 1) {
        viewModelScope.launch {
            val currentCart = sessionManager.getCart().first().toMutableList()

            val existingItem = currentCart.find { it.productId == product._id }
            if (existingItem != null) {
                currentCart.remove(existingItem)
                currentCart.add(existingItem.copy(quantity = existingItem.quantity + quantity))
            } else {
                currentCart.add(
                    SessionManager.CartItem(
                        productId = product._id,
                        productName = product.nombre,
                        price = product.precio,
                        quantity = quantity,
                        imageUrl = product.imagen
                    )
                )
            }

            sessionManager.saveCart(currentCart)
            _addedToCart.value = true
        }
    }

    fun resetCartState() {
        _addedToCart.value = false
    }
}