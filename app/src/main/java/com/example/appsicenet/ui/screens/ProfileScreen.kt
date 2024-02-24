package com.example.appsicenet.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
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
    val attributes = viewModel.attributes
    if (attributes != null) {
        Column(modifier = Modifier.padding(16.dp)) {

            Text(text = "${attributes.nombre}")
            Spacer(modifier = Modifier.height(16.dp))
            Text(text = "Matrícula: ${attributes.matricula}")
            Spacer(modifier = Modifier.height(16.dp))
            Text(text = "Carrera: ${attributes.carrera}")
            Spacer(modifier = Modifier.height(16.dp))
            Text(text = "Especialidad: ${attributes.especialidad}")
            Spacer(modifier = Modifier.height(16.dp))

            Button(onClick = {navController.navigate(NavigationScreens.LoginScreen.route)
            }) {
                Text(text = "Cerrar sesion")
            }
        }

    } else {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = "No se pudo obtener el perfil académico.")
        }
    }
}