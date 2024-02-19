package com.example.appsicenet.navegation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.appsicenet.screens.LoginScreen
import com.example.appsicenet.screens.ProfileScreen
import com.example.appsicenet.screens.ProfileViewModel

@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    val viewModel = ProfileViewModel()
    NavHost(navController, startDestination = "login") {
        composable("login") { LoginScreen(navController = navController, viewModel = viewModel) }
        composable("data") { ProfileScreen(navController = navController, viewModel = viewModel) }
    }
}