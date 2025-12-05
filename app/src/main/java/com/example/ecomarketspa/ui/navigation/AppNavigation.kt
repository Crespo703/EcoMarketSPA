package com.example.ecomarketspa.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.ecomarketspa.data.local.SessionManager
import com.example.ecomarketspa.data.remote.api.EcoMarketApi
import com.example.ecomarketspa.data.remote.dto.ProductDto
import com.example.ecomarketspa.repository.AuthRepository
import com.example.ecomarketspa.repository.OrderRepository
import com.example.ecomarketspa.repository.ProductRepository
import com.example.ecomarketspa.ui.screen.auth.LoginScreen
import com.example.ecomarketspa.ui.screen.auth.RegisterScreen
import com.example.ecomarketspa.ui.screen.cart.CartScreen
import com.example.ecomarketspa.ui.screen.checkout.CheckoutScreen
import com.example.ecomarketspa.ui.screen.home.HomeScreen
import com.example.ecomarketspa.ui.screen.product.ProductDetailScreen
import com.example.ecomarketspa.ui.screen.profile.ProfileScreen
import com.example.ecomarketspa.ui.screen.splash.SplashScreen
import com.example.ecomarketspa.viewmodel.*
import com.google.gson.Gson

sealed class Screen(val route: String) {
    object Splash : Screen("splash")
    object Login : Screen("login")
    object Register : Screen("register")
    object Home : Screen("home")
    object ProductDetail : Screen("product_detail/{productJson}")
    object Cart : Screen("cart")
    object Checkout : Screen("checkout/{total}")
    object Profile : Screen("profile")
}

@Composable
fun AppNavigation(sessionManager: SessionManager) {
    val navController = rememberNavController()
    val api = remember { EcoMarketApi.create() }
    val authRepository = remember { AuthRepository(api) }
    val productRepository = remember { ProductRepository(api) }
    val orderRepository = remember { OrderRepository(api) }
    val gson = remember { Gson() }

    NavHost(
        navController = navController,
        startDestination = Screen.Splash.route
    ) {
        composable(Screen.Splash.route) {
            val viewModel = remember { SplashViewModel(sessionManager) }
            SplashScreen(
                viewModel = viewModel,
                onNavigateToLogin = {
                    navController.navigate(Screen.Login.route) {
                        popUpTo(Screen.Splash.route) { inclusive = true }
                    }
                },
                onNavigateToHome = {
                    navController.navigate(Screen.Home.route) {
                        popUpTo(Screen.Splash.route) { inclusive = true }
                    }
                }
            )
        }

        composable(Screen.Login.route) {
            val viewModel = remember { LoginViewModel(authRepository, sessionManager) }
            LoginScreen(
                viewModel = viewModel,
                onNavigateToRegister = {
                    navController.navigate(Screen.Register.route)
                },
                onLoginSuccess = {
                    navController.navigate(Screen.Home.route) {
                        popUpTo(Screen.Login.route) { inclusive = true }
                    }
                }
            )
        }

        composable(Screen.Register.route) {
            val viewModel = remember { RegisterViewModel(authRepository, sessionManager) }
            RegisterScreen(
                viewModel = viewModel,
                onNavigateToLogin = {
                    navController.popBackStack()
                },
                onRegisterSuccess = {
                    navController.navigate(Screen.Home.route) {
                        popUpTo(Screen.Register.route) { inclusive = true }
                    }
                }
            )
        }

        composable(Screen.Home.route) {
            val viewModel = remember { HomeViewModel(productRepository) }
            HomeScreen(
                viewModel = viewModel,
                onNavigateToProduct = { product ->
                    val productJson = gson.toJson(product)
                    navController.navigate("product_detail/$productJson")
                },
                onNavigateToCart = {
                    navController.navigate(Screen.Cart.route)
                },
                onNavigateToProfile = {
                    navController.navigate(Screen.Profile.route)
                }
            )
        }

        composable(
            route = "product_detail/{productJson}",
            arguments = listOf(navArgument("productJson") { type = NavType.StringType })
        ) { backStackEntry ->
            val productJson = backStackEntry.arguments?.getString("productJson")
            val product = gson.fromJson(productJson, ProductDto::class.java)
            val viewModel = remember { ProductDetailViewModel(sessionManager) }
            ProductDetailScreen(
                product = product,
                viewModel = viewModel,
                onNavigateBack = { navController.popBackStack() }
            )
        }

        composable(Screen.Cart.route) {
            val viewModel = remember { CartViewModel(sessionManager) }
            CartScreen(
                viewModel = viewModel,
                onNavigateBack = { navController.popBackStack() },
                onNavigateToCheckout = {
                    val total = viewModel.total.value
                    navController.navigate("checkout/$total")
                }
            )
        }

        composable(
            route = "checkout/{total}",
            arguments = listOf(navArgument("total") { type = NavType.StringType })
        ) { backStackEntry ->
            val total = backStackEntry.arguments?.getString("total")?.toDoubleOrNull() ?: 0.0
            val viewModel = remember { CheckoutViewModel(orderRepository, sessionManager) }
            CheckoutScreen(
                viewModel = viewModel,
                cartTotal = total,
                onNavigateBack = { navController.popBackStack() },
                onOrderSuccess = {
                    navController.navigate(Screen.Home.route) {
                        popUpTo(Screen.Home.route) { inclusive = true }
                    }
                }
            )
        }

        composable(Screen.Profile.route) {
            val viewModel = remember { ProfileViewModel(authRepository, sessionManager) }
            ProfileScreen(
                viewModel = viewModel,
                onNavigateBack = { navController.popBackStack() },
                onLogoutSuccess = {
                    navController.navigate(Screen.Login.route) {
                        popUpTo(0) { inclusive = true }
                    }
                }
            )
        }
    }
}