package com.example.appsicenet.navegation

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.appsicenet.ui.screens.CalfFinalScreen
import com.example.appsicenet.ui.screens.CalfUniScreen
import com.example.appsicenet.ui.screens.CargaAcademica
import com.example.appsicenet.ui.screens.CargaAcademicaViewModel
import com.example.appsicenet.ui.screens.KardexScreen
import com.example.appsicenet.ui.screens.LoginScreen
import com.example.appsicenet.ui.screens.ProfileScreen
import com.example.appsicenet.ui.screens.ProfileViewModel

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    val viewModel : ProfileViewModel =
        viewModel(factory = ProfileViewModel.Factory)
//    val viewModeld : CargaAcademicaViewModel =
//        viewModel(factory = CargaAcademicaViewModel.Factory)
    NavHost(navController, startDestination = "login") {
        composable("login") {
            LoginScreen(navController = navController, viewModel = viewModel) }
        composable("profile") {
            ProfileScreen(navController = navController, viewModel = viewModel)}
        composable("calfFinal") {
            CalfFinalScreen(navController = navController, viewModel = viewModel,calificaciones = viewModel.calificacionesFinales)}
        composable("calfUnidades") {
            CalfUniScreen(navController = navController, viewModel = viewModel, calificaciones = viewModel.calificacionesUnidades)}
        composable("kardex") {
            KardexScreen(navController = navController, viewModel = viewModel)}
        composable("cargaAcademica") {
            CargaAcademica(navController = navController, viewModel = viewModel, calificaciones = viewModel.cargaAcademica)}
    }
}
