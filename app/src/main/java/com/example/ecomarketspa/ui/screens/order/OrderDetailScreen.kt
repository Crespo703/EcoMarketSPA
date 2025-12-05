package com.example.ecomarketspa.ui.screens.order

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OrderDetailScreen(navController: NavController, orderId: Int) {
    val order = remember(orderId) {
        Order(
            id = orderId,
            date = "2025-11-02",
            total = 24.50,
            status = when (orderId) {
                1 -> "Entregado"
                2 -> "En camino"
                3 -> "Pendiente"
                else -> "Desconocido"
            }
        )
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Detalle del Pedido") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Filled.ArrowBack, contentDescription = "Volver")
                    }
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(24.dp),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.Start
        ) {
            Text(
                text = "Pedido #${order.id}",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold
            )
            Spacer(Modifier.height(8.dp))
            Text("Fecha: ${order.date}")
            Text("Estado: ${order.status}")
            Text("Total: $${order.total}")

            Spacer(Modifier.height(32.dp))
            Text(
                "Productos incluidos:",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.SemiBold
            )
            Spacer(Modifier.height(8.dp))
            Text("• Manzanas Orgánicas — $2.50")
            Text("• Leche Natural — $1.80")
            Text("• Zanahorias Frescas — $1.20")

            Spacer(Modifier.height(24.dp))
            Button(onClick = { navController.navigate("payment") }) {
                Text("Ir a pago")
            }
        }
    }
}
