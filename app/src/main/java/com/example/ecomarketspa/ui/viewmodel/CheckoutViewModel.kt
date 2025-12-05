package com.example.ecomarketspa.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ecomarketspa.data.local.SessionManager
import com.example.ecomarketspa.data.remote.dto.PagoRequest
import com.example.ecomarketspa.data.remote.dto.PedidoRequest
import com.example.ecomarketspa.data.remote.dto.ProductoPedido
import com.example.ecomarketspa.repository.OrderRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

sealed class CheckoutState {
    object Idle : CheckoutState()
    object Loading : CheckoutState()
    object Success : CheckoutState()
    data class Error(val message: String) : CheckoutState()
}

class CheckoutViewModel(
    private val orderRepository: OrderRepository,
    private val sessionManager: SessionManager
) : ViewModel() {

    private val _state = MutableStateFlow<CheckoutState>(CheckoutState.Idle)
    val state: StateFlow<CheckoutState> = _state

    fun processOrder() {
        viewModelScope.launch {
            _state.value = CheckoutState.Loading

            try {
                val token = sessionManager.getToken().first() ?: throw Exception("Sin token")
                val cart = sessionManager.getCart().first()

                if (cart.isEmpty()) {
                    _state.value = CheckoutState.Error("Carrito vacío")
                    return@launch
                }

                val total = cart.sumOf { it.price * it.quantity }

                val productos = cart.map {
                    ProductoPedido(
                        producto = it.productId,
                        cantidad = it.quantity,
                        precioUnitario = it.price
                    )
                }
                val pedidoRequest = PedidoRequest(productos, total)
                val pedidoResult = orderRepository.createPedido(token, pedidoRequest)

                if (pedidoResult.isFailure) {
                    _state.value = CheckoutState.Error("Error al crear pedido")
                    return@launch
                }

                val pedidoId = pedidoResult.getOrNull()?._id ?: throw Exception("Sin ID de pedido")

                val pagoRequest = PagoRequest(pedido = pedidoId, monto = total)
                val pagoResult = orderRepository.createPago(token, pagoRequest)

                if (pagoResult.isSuccess) {
                    sessionManager.saveCart(emptyList())
                    _state.value = CheckoutState.Success
                } else {
                    _state.value = CheckoutState.Error("Error al procesar pago")
                }

            } catch (e: Exception) {
                _state.value = CheckoutState.Error(e.message ?: "Error desconocido")
            }
        }
    }

    fun resetState() {
        _state.value = CheckoutState.Idle
    }
}