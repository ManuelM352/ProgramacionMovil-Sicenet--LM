package com.example.appsicenet.ui.screens

import android.annotation.SuppressLint
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.appsicenet.models.CalificacionUnidades
import com.example.appsicenet.models.CalificacionesFinales
import com.example.appsicenet.navegation.NavigationScreens
import kotlinx.coroutines.launch


@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun CalfFinalScreen(navController: NavController, viewModel: ProfileViewModel, calificaciones: List<CalificacionesFinales>?) {
    val coroutineScope = rememberCoroutineScope()
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
                    Text(text = "Calificación: ${calificacion.calif}")
                    Text(text = "Acreditación: ${calificacion.acred}")
                    Text(text = "Grupo: ${calificacion.grupo}")
                    Text(text = "Materia: ${calificacion.materia}")
                    Text(text = "Observaciones: ${calificacion.Observaciones}")
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
            item {
                Button(
                    onClick = {
                        coroutineScope.launch {
                            // Guardar la información al hacer clic en el botón "Guardar"
                            viewModel.saveCalificacionesFinales(calificaciones)
                            Log.d("SaveButton", "Save button clicked")
                        }
                    },
                    modifier = Modifier
                        .padding(top = 16.dp)
                        .fillMaxWidth()
                ) {
                    Text(text = "Guardar")
                }

                Spacer(modifier = Modifier.height(16.dp))
            }
        }

    } else {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = "No se pudieron obtener las calificaciones")
        }
    }
}

