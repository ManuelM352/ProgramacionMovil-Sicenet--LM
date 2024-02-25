package com.example.appsicenet.ui.screens

import android.annotation.SuppressLint
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.appsicenet.navegation.NavigationScreens


@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun CalfFinalScreen(navController: NavController, viewModel: ProfileViewModel) {
    val calificaciones = viewModel.calificacionesFinales
    if (calificaciones != null && calificaciones.isNotEmpty()) {
        Column(modifier = Modifier.padding(16.dp)) {
            calificaciones.forEach { calificacion ->
                // Representa cada atributo de la calificación final en un Text
                Text(text = "Calificación: ${calificacion.calif}")
                Text(text = "Acreditación: ${calificacion.acred}")
                Text(text = "Grupo: ${calificacion.grupo}")
                Text(text = "Materia: ${calificacion.materia}")
                Text(text = "Observaciones: ${calificacion.Observaciones}")
                // Agrega un separador entre cada calificación final
                Spacer(modifier = Modifier.height(16.dp))
            }

            Button(onClick = { navController.navigate(NavigationScreens.LoginScreen.route) }) {
                Text(text = "Cerrar sesión")
            }
        }

    } else {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = "No se pudieron obtener las calificaciones")
        }
    }
}

