package com.example.ecomarketspa.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ecomarketspa.data.local.SessionManager
import com.example.ecomarketspa.repository.AuthRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

sealed class LoginState {
    object Idle : LoginState()
    object Loading : LoginState()
    object Success : LoginState()
    data class Error(val message: String) : LoginState()
}

class LoginViewModel(
    private val authRepository: AuthRepository,
    private val sessionManager: SessionManager
) : ViewModel() {

    private val _state = MutableStateFlow<LoginState>(LoginState.Idle)
    val state: StateFlow<LoginState> = _state

    fun login(email: String, password: String) {
        if (!isValidEmail(email)) {
            _state.value = LoginState.Error("Email inválido")
            return
        }
        if (!isValidPassword(password)) {
            _state.value = LoginState.Error("Password debe tener al menos 6 caracteres")
            return
        }

        viewModelScope.launch {
            _state.value = LoginState.Loading
            val result = authRepository.login(email, password)

            result.onSuccess { authResponse ->
                sessionManager.saveToken(authResponse.access_token)
                _state.value = LoginState.Success
            }.onFailure { e ->
                _state.value = LoginState.Error(e.message ?: "Error al iniciar sesión")
            }
        }
    }

    fun resetState() {
        _state.value = LoginState.Idle
    }

    companion object {
        fun isValidEmail(email: String): Boolean {
            return email.contains("@") && email.contains(".")
        }

        fun isValidPassword(password: String): Boolean {
            return password.length >= 6
        }
    }
}