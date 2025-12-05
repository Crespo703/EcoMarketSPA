package com.example.ecomarketspa.ui.screens.category

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CategoryScreen(navController: NavController) {
    val categories = listOf("Frutas", "Verduras", "Lácteos", "Cereales", "Bebidas")

    Scaffold(
        topBar = {
            TopAppBar(title = { Text("Categorías") })
        }
    ) { padding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp)
        ) {
            items(categories) { category ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp)
                        .clickable {
                            // Podrías filtrar productos por categoría
                        }
                ) {
                    Column(Modifier.padding(16.dp)) {
                        Text(category, style = MaterialTheme.typography.titleMedium)
                    }
                }
            }
        }
    }
}
