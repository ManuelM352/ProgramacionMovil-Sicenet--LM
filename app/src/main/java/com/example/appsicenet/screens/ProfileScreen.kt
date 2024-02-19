package com.example.appsicenet.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.example.appsicenet.navegation.NavigationScreens

@Composable
fun ProfileScreen(navController: NavController, viewModel: ProfileViewModel) {
//    val attributes = viewModel.attributes
//    if (attributes != null) {
//        Column(modifier = Modifier.padding(16.dp)) {
//            Text(text = "Nombre: ${attributes.nombre}")
//            Text(text = "Matrícula: ${attributes.matricula}")
//            Text(text = "Carrera: ${attributes.carrera}")
//
//        }
//    } else {
//        Column(modifier = Modifier.padding(16.dp)) {
//            Text(text = "No se pudo obtener el perfil académico.")
//        }
//    }
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Data Screen")
        Button(onClick = { navController.navigate(NavigationScreens.LoginScreen.route) }) {
            Text(text = "Cerrar sesion")
        }
    }
}