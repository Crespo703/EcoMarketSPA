@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.ecomarketspa.ui.screens.payment

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

@Composable
fun PaymentScreen(navController: NavController) {
    var selectedOption by remember { mutableStateOf("Tarjeta de crédito") }
    val paymentOptions = listOf("Tarjeta de crédito", "Transferencia", "Efectivo")

    Scaffold(
        topBar = {
            TopAppBar(title = { Text("Método de Pago") })
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(24.dp),
            verticalArrangement = Arrangement.Top
        ) {
            Text("Selecciona tu método de pago:", style = MaterialTheme.typography.titleMedium)
            Spacer(Modifier.height(16.dp))

            paymentOptions.forEach { option ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(option)
                    RadioButton(
                        selected = selectedOption == option,
                        onClick = { selectedOption = option }
                    )
                }
            }

            Spacer(Modifier.height(24.dp))
            Button(onClick = { navController.navigate("orders") }) {
                Text("Confirmar pago")
            }
        }
    }
}

