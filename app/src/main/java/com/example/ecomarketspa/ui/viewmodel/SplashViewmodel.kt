package com.example.ecomarketspa.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ecomarketspa.data.local.SessionManager
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class SplashViewModel(private val sessionManager: SessionManager) : ViewModel() {

    private val _hasSession = MutableStateFlow<Boolean?>(null)
    val hasSession: StateFlow<Boolean?> = _hasSession

    init {
        checkSession()
    }

    private fun checkSession() {
        viewModelScope.launch {
            delay(1500)
            val token = sessionManager.getToken().first()
            _hasSession.value = !token.isNullOrEmpty()
        }
    }
}