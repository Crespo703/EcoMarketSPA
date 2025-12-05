package com.example.ecomarketspa.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ecomarketspa.data.remote.dto.ProductDto
import com.example.ecomarketspa.repository.ProductRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

sealed class HomeState {
    object Idle : HomeState()
    object Loading : HomeState()
    data class Success(val products: List<ProductDto>) : HomeState()
    data class Error(val message: String) : HomeState()
}

class HomeViewModel(private val productRepository: ProductRepository) : ViewModel() {

    private val _state = MutableStateFlow<HomeState>(HomeState.Idle)
    val state: StateFlow<HomeState> = _state

    init {
        loadProducts()
    }

    fun loadProducts() {
        viewModelScope.launch {
            _state.value = HomeState.Loading
            val result = productRepository.getProducts()

            result.onSuccess { products ->
                _state.value = HomeState.Success(products)
            }.onFailure { e ->
                _state.value = HomeState.Error(e.message ?: "Error al cargar productos")
            }
        }
    }
}