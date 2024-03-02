package com.example.appsicenet.ui.screens

import android.annotation.SuppressLint
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.appsicenet.models.CalificacionUnidades
import com.example.appsicenet.models.CargaAcademica
import com.example.appsicenet.navegation.NavigationScreens

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun CalfUniScreen(navController: NavController, viewModel: ProfileViewModel, calificaciones: List<CalificacionUnidades>?) {
    if (!calificaciones.isNullOrEmpty()) {
        LazyColumn(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxSize()
        ) {
            items(calificaciones) { calificacion ->
                Column(
                    modifier = Modifier
                        .padding(8.dp)
                        .background(color = Color.LightGray)
                        .fillMaxWidth()
                ) {
                    // Representa cada atributo de la calificación final en un Text
                    Text(text = "Calificación 1: ${calificacion.c1}")
                    Text(text = "Calificación 2: ${calificacion.c2}")
                    Text(text = "Acreditación: ${calificacion.observaciones}")
                    Text(text = "Grupo: ${calificacion.grupo}")
                    Text(text = "Materia: ${calificacion.materia}")
                    Text(text = "Observaciones: ${calificacion.unidadesActivas}")
                }
                // Agrega un separador entre cada calificación final
                Spacer(modifier = Modifier.height(16.dp))
            }
            item {
                Button(
                    onClick = { navController.navigate(NavigationScreens.LoginScreen.route) },
                    modifier = Modifier
                        .padding(top = 16.dp)
                        .fillMaxWidth()
                ) {
                    Text(text = "Cerrar sesión")
                }
            }
        }

    } else {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = "No se pudieron obtener las calificaciones")
        }
    }
}
