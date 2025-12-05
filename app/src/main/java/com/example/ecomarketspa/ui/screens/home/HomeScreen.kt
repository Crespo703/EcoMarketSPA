package com.example.ecomarketspa.ui.screen.home

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.ecomarketspa.data.remote.dto.ProductDto
import com.example.ecomarketspa.ui.component.ErrorView
import com.example.ecomarketspa.ui.component.LoadingView
import com.example.ecomarketspa.ui.component.ProductItemCard
import com.example.ecomarketspa.viewmodel.HomeState
import com.example.ecomarketspa.viewmodel.HomeViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    viewModel: HomeViewModel,
    onNavigateToProduct: (ProductDto) -> Unit,
    onNavigateToCart: () -> Unit,
    onNavigateToProfile: () -> Unit
) {
    val state by viewModel.state.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("EcoMarket") },
                actions = {
                    IconButton(onClick = onNavigateToCart) {
                        Icon(Icons.Default.ShoppingCart, "Carrito")
                    }
                    IconButton(onClick = onNavigateToProfile) {
                        Icon(Icons.Default.Person, "Perfil")
                    }
                }
            )
        }
    ) { padding ->
        when (val currentState = state) {
            is HomeState.Idle, is HomeState.Loading -> {
                LoadingView()
            }
            is HomeState.Success -> {
                if (currentState.products.isEmpty()) {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(padding),
                        contentAlignment = androidx.compose.ui.Alignment.Center
                    ) {
                        Text("No hay productos disponibles")
                    }
                } else {
                    LazyColumn(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(padding),
                        contentPadding = PaddingValues(8.dp)
                    ) {
                        items(currentState.products) { product ->
                            ProductItemCard(
                                product = product,
                                onClick = { onNavigateToProduct(product) }
                            )
                        }
                    }
                }
            }
            is HomeState.Error -> {
                ErrorView(
                    message = currentState.message,
                    onRetry = { viewModel.loadProducts() }
                )
            }
        }
    }
}