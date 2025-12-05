package com.example.ecomarketspa.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ecomarketspa.data.local.SessionManager
import com.example.ecomarketspa.data.remote.dto.ClienteProfileDto
import com.example.ecomarketspa.repository.AuthRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

sealed class ProfileState {
    object Idle : ProfileState()
    object Loading : ProfileState()
    data class Success(val profile: ClienteProfileDto, val avatarUri: String?) : ProfileState()
    data class Error(val message: String) : ProfileState()
}

class ProfileViewModel(
    private val authRepository: AuthRepository,
    private val sessionManager: SessionManager
) : ViewModel() {

    private val _state = MutableStateFlow<ProfileState>(ProfileState.Idle)
    val state: StateFlow<ProfileState> = _state

    private val _loggedOut = MutableStateFlow(false)
    val loggedOut: StateFlow<Boolean> = _loggedOut

    init {
        loadProfile()
    }

    fun loadProfile() {
        viewModelScope.launch {
            _state.value = ProfileState.Loading

            try {
                val token = sessionManager.getToken().first()
                val cachedProfile = sessionManager.getProfile().first()
                val avatarUri = sessionManager.getAvatarUri().first()

                if (token.isNullOrEmpty()) {
                    _state.value = ProfileState.Error("Sin sesión")
                    return@launch
                }

                val result = authRepository.getProfile(token)
                if (result.isSuccess) {
                    val updatedProfile = result.getOrNull()!!
                    sessionManager.saveProfile(updatedProfile)
                    _state.value = ProfileState.Success(updatedProfile, avatarUri)
                } else {
                    if (cachedProfile != null) {
                        _state.value = ProfileState.Success(cachedProfile, avatarUri)
                    } else {
                        _state.value = ProfileState.Error("No se pudo cargar el perfil")
                    }
                }

            } catch (e: Exception) {
                _state.value = ProfileState.Error(e.message ?: "Error al cargar perfil")
            }
        }
    }

    fun updateAvatar(uri: String) {
        viewModelScope.launch {
            sessionManager.saveAvatarUri(uri)
            loadProfile()
        }
    }

    fun logout() {
        viewModelScope.launch {
            sessionManager.clearSession()
            _loggedOut.value = true
        }
    }
}