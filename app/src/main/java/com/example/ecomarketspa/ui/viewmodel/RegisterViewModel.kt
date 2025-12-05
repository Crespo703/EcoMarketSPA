package com.example.ecomarketspa.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ecomarketspa.data.local.SessionManager
import com.example.ecomarketspa.repository.AuthRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

sealed class RegisterState {
    object Idle : RegisterState()
    object Loading : RegisterState()
    object Success : RegisterState()
    data class Error(val message: String) : RegisterState()
}

class RegisterViewModel(
    private val authRepository: AuthRepository,
    private val sessionManager: SessionManager
) : ViewModel() {

    private val _state = MutableStateFlow<RegisterState>(RegisterState.Idle)
    val state: StateFlow<RegisterState> = _state

    fun register(email: String, password: String, nombre: String) {
        if (email.isBlank() || !email.contains("@")) {
            _state.value = RegisterState.Error("Email inválido")
            return
        }
        if (password.length < 6) {
            _state.value = RegisterState.Error("Password debe tener al menos 6 caracteres")
            return
        }
        if (nombre.isBlank()) {
            _state.value = RegisterState.Error("Nombre requerido")
            return
        }

        viewModelScope.launch {
            _state.value = RegisterState.Loading
            val result = authRepository.register(email, password, nombre)

            result.onSuccess { authResponse ->
                sessionManager.saveToken(authResponse.access_token)
                _state.value = RegisterState.Success
            }.onFailure { e ->
                _state.value = RegisterState.Error(e.message ?: "Error al registrar")
            }
        }
    }

    fun resetState() {
        _state.value = RegisterState.Idle
    }
}