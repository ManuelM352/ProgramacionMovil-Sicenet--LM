package com.example.appsicenet.ui.screens

import android.annotation.SuppressLint
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.appsicenet.navegation.NavigationScreens

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun CargaAcademica(navController: NavController, viewModel: ProfileViewModel) {
    val calificaciones = viewModel.cargaAcademica
    if (calificaciones != null && calificaciones.isNotEmpty()) {
        Column(modifier = Modifier.padding(16.dp)) {
            calificaciones.forEach { calificacion ->
                // Representa cada atributo de la calificación final en un Text
                Text(text = "Calificación: ${calificacion.semipresencial}")
                Text(text = "Acreditación: ${calificacion.observaciones}")
                Text(text = "Grupo: ${calificacion.grupo}")
                Text(text = "Materia: ${calificacion.materia}")
                Text(text = "Observaciones: ${calificacion.docente}")
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

