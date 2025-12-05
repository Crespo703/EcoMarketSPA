package com.example.ecomarketspa.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ecomarketspa.data.local.SessionManager
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class CartViewModel(private val sessionManager: SessionManager) : ViewModel() {

    private val _cartItems = MutableStateFlow<List<SessionManager.CartItem>>(emptyList())
    val cartItems: StateFlow<List<SessionManager.CartItem>> = _cartItems

    private val _total = MutableStateFlow(0.0)
    val total: StateFlow<Double> = _total

    init {
        loadCart()
    }

    fun loadCart() {
        viewModelScope.launch {
            sessionManager.getCart().collect { items ->
                _cartItems.value = items
                _total.value = items.sumOf { it.price * it.quantity }
            }
        }
    }

    fun updateQuantity(productId: String, newQuantity: Int) {
        viewModelScope.launch {
            val currentCart = sessionManager.getCart().first().toMutableList()
            val item = currentCart.find { it.productId == productId }

            if (item != null) {
                if (newQuantity > 0) {
                    currentCart.remove(item)
                    currentCart.add(item.copy(quantity = newQuantity))
                } else {
                    currentCart.remove(item)
                }
                sessionManager.saveCart(currentCart)
            }
        }
    }

    fun removeItem(productId: String) {
        viewModelScope.launch {
            val currentCart = sessionManager.getCart().first().toMutableList()
            currentCart.removeAll { it.productId == productId }
            sessionManager.saveCart(currentCart)
        }
    }

    fun clearCart() {
        viewModelScope.launch {
            sessionManager.saveCart(emptyList())
        }
    }
}