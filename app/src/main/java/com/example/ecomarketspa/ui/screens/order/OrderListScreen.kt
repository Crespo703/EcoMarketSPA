@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.ecomarketspa.ui.screens.order

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

data class Order(
    val id: Int,
    val date: String,
    val total: Double,
    val status: String
)

@Composable
fun OrderListScreen(navController: NavController) {
    val orders = remember {
        listOf(
            Order(1, "2025-10-15", 24.50, "Entregado"),
            Order(2, "2025-10-28", 15.75, "En camino"),
            Order(3, "2025-11-01", 32.10, "Pendiente")
        )
    }

    Scaffold(
        topBar = {
            TopAppBar(title = { Text("Pedidos") })
        }
    ) { padding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp)
        ) {
            items(orders) { order ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp),
                    onClick = {
                        navController.navigate("orderDetail/${order.id}")
                    }
                ) {
                    Column(Modifier.padding(16.dp)) {
                        Text(
                            text = "Pedido #${order.id}",
                            style = MaterialTheme.typography.titleMedium
                        )
                        Text("Fecha: ${order.date}")
                        Text("Estado: ${order.status}")
                        Text("Total: $${order.total}")
                    }
                }
            }
        }
    }
}
