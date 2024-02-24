package com.example.appsicenet.navegation

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.appsicenet.ui.screens.LoginScreen
import com.example.appsicenet.ui.screens.ProfileScreen
import com.example.appsicenet.ui.screens.ProfileViewModel

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    val viewModel : ProfileViewModel =
        viewModel(factory = ProfileViewModel.Factory)
    NavHost(navController, startDestination = "login") {
        composable("login") {
            LoginScreen(navController = navController, viewModel = viewModel) }
        composable("profile") {
            ProfileScreen(navController = navController, viewModel = viewModel)

        }
    }
}
