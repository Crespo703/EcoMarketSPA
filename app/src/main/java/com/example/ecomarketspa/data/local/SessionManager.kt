package com.example.ecomarketspa.data.local

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.example.ecomarketspa.data.remote.dto.ClienteProfileDto
import com.google.gson.Gson
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "session")

class SessionManager(private val context: Context) {

    companion object {
        private val TOKEN_KEY = stringPreferencesKey("token")
        private val PROFILE_KEY = stringPreferencesKey("profile")
        private val AVATAR_URI_KEY = stringPreferencesKey("avatar_uri")
        private val CART_KEY = stringPreferencesKey("cart")
    }

    private val gson = Gson()

    // Token
    suspend fun saveToken(token: String) {
        context.dataStore.edit { preferences ->
            preferences[TOKEN_KEY] = token
        }
    }

    fun getToken(): Flow<String?> {
        return context.dataStore.data.map { preferences ->
            preferences[TOKEN_KEY]
        }
    }

    // Profile
    suspend fun saveProfile(profile: ClienteProfileDto) {
        context.dataStore.edit { preferences ->
            preferences[PROFILE_KEY] = gson.toJson(profile)
        }
    }

    fun getProfile(): Flow<ClienteProfileDto?> {
        return context.dataStore.data.map { preferences ->
            preferences[PROFILE_KEY]?.let {
                gson.fromJson(it, ClienteProfileDto::class.java)
            }
        }
    }

    // Avatar URI
    suspend fun saveAvatarUri(uri: String) {
        context.dataStore.edit { preferences ->
            preferences[AVATAR_URI_KEY] = uri
        }
    }

    fun getAvatarUri(): Flow<String?> {
        return context.dataStore.data.map { preferences ->
            preferences[AVATAR_URI_KEY]
        }
    }

    // Cart
    data class CartItem(
        val productId: String,
        val productName: String,
        val price: Double,
        val quantity: Int,
        val imageUrl: String?
    )

    suspend fun saveCart(cart: List<CartItem>) {
        context.dataStore.edit { preferences ->
            preferences[CART_KEY] = gson.toJson(cart)
        }
    }

    fun getCart(): Flow<List<CartItem>> {
        return context.dataStore.data.map { preferences ->
            preferences[CART_KEY]?.let {
                gson.fromJson(it, Array<CartItem>::class.java).toList()
            } ?: emptyList()
        }
    }

    // Logout
    suspend fun clearSession() {
        context.dataStore.edit { preferences ->
            preferences.remove(TOKEN_KEY)
            preferences.remove(PROFILE_KEY)
            preferences.remove(AVATAR_URI_KEY)
            preferences.remove(CART_KEY)
        }
    }
}